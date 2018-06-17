package com.company.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.MainApplication;
import com.company.model.Manufacturer;
import com.company.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, 
  webEnvironment = WebEnvironment.DEFINED_PORT)
public class VehicleRestEndpointTests3 {
 
    @Autowired
    private TestRestTemplate template;
 
    private static String VEHICLE_ENDPOINT = "http://localhost:8080/api/vehicles/";
    private static String VEHICLETYPE_ENDPOINT = "http://localhost:8080/api/vehicleTypes/";
    private static String MANUFACTURER_ENDPOINT = "http://localhost:8080/api/mfgs/";
    private static String LOCATION_ENDPOINT = "http://localhost:8080/api/locations/";
 
    @Test
    public void whenSaveOneToOneRelationship_thenCorrect() throws JSONException {
        Vehicle v = new Vehicle();
		v.setBatchNo(20);
		v.setName("Test New Vehicle");
		v.setPrice(BigDecimal.valueOf(20000L));
		v.setYearFirstMade(new Date());

		ResponseEntity<Vehicle> vehiclep = template.postForEntity(VEHICLE_ENDPOINT, v, Vehicle.class);
		assertEquals(HttpStatus.CREATED, vehiclep.getStatusCode());
		URI vUri = vehiclep.getHeaders().getLocation();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> httpEntity = new HttpEntity<>(VEHICLETYPE_ENDPOINT + "/1", requestHeaders);
        ResponseEntity<String> res1 = template.exchange(vUri + "/vehicleType", HttpMethod.PUT, httpEntity, String.class);
		assertEquals(res1.getStatusCode(),HttpStatus.NO_CONTENT);

        HttpEntity<String> httpEntity1 = new HttpEntity<>(MANUFACTURER_ENDPOINT + "/1", requestHeaders);
        ResponseEntity<String> res2 = template.exchange(vUri + "/manufacturer", HttpMethod.PUT, httpEntity1, String.class);
		assertEquals(res2.getStatusCode(),HttpStatus.NO_CONTENT);

        ResponseEntity<Vehicle> vehicleGetResponse = template.getForEntity(vUri, Vehicle.class);
        assertEquals("vehicle is incorrect", vehicleGetResponse.getBody()
            .getName(), v.getName());
    }
    
    @Test
    public void whenSaveManyToManyRelationship_thenCorrect() throws JSONException{

        String jsonResponse = template.getForObject(MANUFACTURER_ENDPOINT , String.class);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray("mfgs");
        assertEquals("manufacturer is incorrect", jsonArray.getJSONObject(0)
            .getString("name"), "General Motors Corporation");
    }

    @Test
    public void whenSaveOneToManyRelationship_thenCorrect() throws JSONException{
        Manufacturer mfg = new Manufacturer();
        mfg.setName("Honda Motor Company");
        mfg.setActive(true);
        mfg.setAverageYearlySales(new BigDecimal(2000000L));
        mfg.setFoundedDate(new Date());
        
        ResponseEntity<Manufacturer> mfg1 = template.postForEntity(MANUFACTURER_ENDPOINT, mfg, Manufacturer.class);
		assertEquals(HttpStatus.CREATED, mfg1.getStatusCode());
		URI vUri0 = mfg1.getHeaders().getLocation();
		String myurl = vUri0.toString();
		System.out.println(myurl);
		int i = myurl.lastIndexOf("/");
		int ind1 = Integer.parseInt(myurl.substring(i+1));
		System.out.println(myurl.substring(i+1));

		
        String jsonResponse = template.getForObject(MANUFACTURER_ENDPOINT , String.class);
        JSONObject jsonObj = new JSONObject(jsonResponse).getJSONObject("_embedded");
        JSONArray jsonArray = jsonObj.getJSONArray("mfgs");
        assertEquals("manufacturer is incorrect", jsonArray.getJSONObject(ind1-1)
            .getString("name"), mfg.getName());

// 
//        ResponseEntity<Manufacturer> manufacturerGetResponse = template.getForEntity(vUri0.toString(), 
//        		Manufacturer.class);
//        assertEquals(HttpStatus.OK,manufacturerGetResponse.getStatusCode());

//        assertEquals("manufacturer is incorrect", 
//        		mfg.getName(),
//        		manufacturerGetResponse.getBody().getName());
// 
        Vehicle v1 = new Vehicle();
		v1.setBatchNo(21);
		v1.setName("Test Honda Accord Vehicle");
		v1.setPrice(BigDecimal.valueOf(20000L));
		v1.setYearFirstMade(new Date());

		ResponseEntity<Vehicle> vehiclep1 = template.postForEntity(VEHICLE_ENDPOINT, v1, Vehicle.class);
		URI vUri1 = vehiclep1.getHeaders().getLocation();
		assertEquals(HttpStatus.CREATED,vehiclep1.getStatusCode());
        
        Vehicle v2 = new Vehicle();
		v2.setBatchNo(21);
		v2.setName("Test Honda CRV Vehicle");
		v2.setPrice(BigDecimal.valueOf(25000L));
		v2.setYearFirstMade(new Date());

		ResponseEntity<Vehicle> vehiclep2 = template.postForEntity(VEHICLE_ENDPOINT, v2, Vehicle.class);
		URI vUri2 = vehiclep2.getHeaders().getLocation();
		assertEquals(HttpStatus.CREATED,vehiclep2.getStatusCode());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", "text/uri-list");
        HttpEntity<String> httpEntity1 = new HttpEntity<>(vUri0.toString(), requestHeaders);
        ResponseEntity<String> res1 = template.exchange(vUri1+"/manufacturer", HttpMethod.PUT, httpEntity1, String.class);
		assertEquals(HttpStatus.NO_CONTENT,res1.getStatusCode());
        ResponseEntity<String> res2 = template.exchange(vUri2+"/manufacturer", HttpMethod.PUT, httpEntity1, String.class);
		assertEquals(HttpStatus.NO_CONTENT,res2.getStatusCode());

   }
}