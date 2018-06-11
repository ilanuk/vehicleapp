package com.company.repository;

import static junit.framework.TestCase.fail;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.company.model.Location;
import com.company.model.Manufacturer;
import com.company.model.Vehicle;
import com.company.model.VehicleType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleRestEndpointTests1 {
	//check the RepositoryConfig settings to change the baseURI
	private static final String BASE_URI = "http://localhost:8080/api";
	private static final int UNKNOWN_ID = Integer.MAX_VALUE;

	@Test
	public void listVehicleWorksOK() {
		RestTemplate template = new RestTemplate();
		ResponseEntity<Vehicle> result = template.getForEntity(BASE_URI + "/vehicles/1", Vehicle.class);
		System.out.println(result);
		assertNotNull(result);
		assertNotNull(result.getBody());
		System.out.println(result.getBody().toString());
		Vehicle veh = result.getBody();
		System.out.println(veh.getName());
		System.out.println(veh.getId());
		result.getHeaders().forEach((k,list)->{
			System.out.println("K="+k+":list="+list);
		});
	}

	@Test
	public void test_get_one_success() {
		RestTemplate template = new RestTemplate();
		ResponseEntity<Vehicle> response = template.getForEntity(BASE_URI + "/vehicles/1", Vehicle.class);
		Vehicle vehicle = response.getBody();
		System.out.println(vehicle);
		assertThat(vehicle.getName(), is("Chevrolet Bolt EV"));
		assertThat(response.getStatusCode(), is(HttpStatus.OK));

	}

	@Test
	public void test_get_by_id_failure_not_found() {
		try {
			RestTemplate template = new RestTemplate();
			ResponseEntity<Vehicle> response = template.getForEntity(BASE_URI + "/vehicles/" + UNKNOWN_ID,
					Vehicle.class);
			fail("should return 404 not found");
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}
	}

	@Test
	public void test_create_new_vehicle_success() {
		Vehicle v = new Vehicle();
		v.setBatchNo(10);
		v.setName("Test Vehicle");
		v.setPrice(BigDecimal.valueOf(55L));
		v.setYearFirstMade(new Date());
		RestTemplate template = new RestTemplate();
		URI location = template.postForLocation(BASE_URI + "/vehicles/", v, Vehicle.class);
		
		System.out.println(location.getPath());
		assertThat(location, notNullValue());
		ResponseEntity<Vehicle> result = template.getForEntity(location, Vehicle.class);
		System.out.println(result);
		assertNotNull(result);
		assertNotNull(result.getBody());
		System.out.println(result.getBody().toString());
		Vehicle veh = result.getBody();
		System.out.println(veh.getName());
		System.out.println(veh.getId());
		//delete the newly created entity
		template.delete(location);
		try {
			ResponseEntity<Vehicle> response = template.getForEntity(location, Vehicle.class);
			fail("should return 404 not found");
		}
		catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}

	}
	@Test
	public void test_create_new_vehicle_success2() {
		Vehicle v = new Vehicle();
		v.setBatchNo(10);
		v.setName("Test Vehicle");
		v.setPrice(BigDecimal.valueOf(55L));
		v.setYearFirstMade(new Date());
		RestTemplate template = new RestTemplate();
		//URI location = template.postForLocation(BASE_URI + "/vehicles/", v, Vehicle.class);
		ResponseEntity<Vehicle> response 
		= template.postForEntity(BASE_URI+"/vehicles", v, Vehicle.class);
		assertEquals(response.getStatusCode(),HttpStatus.CREATED);
		List<String> values = response.getHeaders().get("Location");
		System.out.println(values.get(0));
		assertThat(values.get(0), notNullValue());
		ResponseEntity<Vehicle> result = template.getForEntity(values.get(0), Vehicle.class);
		System.out.println(result);
		assertNotNull(result);
		assertNotNull(result.getBody());
		System.out.println(result.getBody().toString());
		Vehicle veh = result.getBody();
		System.out.println(veh.getName());
		System.out.println(veh.getId());
		//delete the newly created entity
		template.delete(values.get(0));
		try {
			ResponseEntity<Vehicle> res = template.getForEntity(values.get(0), Vehicle.class);
			fail("should return 404 not found");
		}
		catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}

	}

	@Test
	public void test_create_new_vehicle_doesNotfail_autoId() {
		try {
			Vehicle v = new Vehicle();
			v.setBatchNo(10);
			v.setName("Test Vehicle");
			v.setPrice(BigDecimal.valueOf(55L));
			v.setYearFirstMade(new Date());
			RestTemplate template = new RestTemplate();
			URI location = template.postForLocation(BASE_URI + "/vehicles/", v, Vehicle.class);
			location = template.postForLocation(BASE_URI + "/vehicles/", v, Vehicle.class);
			assertThat(location, notNullValue());
		} catch (HttpClientErrorException e) {
			// as the id is autogenerated
			assertThat(e.getStatusCode(), is(HttpStatus.OK));
		}
	}

	@Test
	public void test_update_vehicle_success() {
		Vehicle existingVehicle = new Vehicle();
		existingVehicle.setName("TestUpdate");
		RestTemplate template = new RestTemplate();
		template.put(BASE_URI + "/vehicles/" + 2, existingVehicle);
	}

	@Test
	public void test_update_vehicle_fail() {
		Vehicle existingVehicle = new Vehicle();
		long myId = UNKNOWN_ID;
		existingVehicle.setId(myId);
		try {
			RestTemplate template = new RestTemplate();
			
			template.put(BASE_URI + "/vehicles/" + UNKNOWN_ID, existingVehicle);
			
			fail("should return 404 not found");
		} catch (HttpClientErrorException e) {
			System.out.println(e.getStatusCode() + ":" + e.getStatusText());
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}
	}

	// =========================================== Delete Vehicle
	// ============================================

	@Test
	public void test_delete_vehicle_success() {
		RestTemplate template = new RestTemplate();
		template.delete(BASE_URI + "/vehicles/" + 11);
	}

	@Test
	public void test_delete_vehicle_fail() {
		try {
			RestTemplate template = new RestTemplate();
			template.delete(BASE_URI + "/vehicles/" + UNKNOWN_ID);
			fail("should return 404 not found");
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}
	}
	
	@Test 
	public void testTraversion() throws URISyntaxException {
		
		Traverson traverson = new Traverson(new URI(BASE_URI), MediaTypes.HAL_JSON);
		ParameterizedTypeReference<Resources<Vehicle>> resourceParameterizedTypeReference =
		        new ParameterizedTypeReference<Resources<Vehicle>>() {};
		
		Resources<Vehicle> vehicleResource = traverson.follow("vehicles").
		        toObject(resourceParameterizedTypeReference);

		for (Vehicle vehicle : vehicleResource) {
			System.out.println(vehicle);
		}
		ParameterizedTypeReference<Resources<Manufacturer>> resParameterizedTypeReference =
		        new ParameterizedTypeReference<Resources<Manufacturer>>() {};
		Resources<Manufacturer> manufacturerResource = traverson.follow("mfgs").
		        toObject(resParameterizedTypeReference);

		for (Manufacturer mfg : manufacturerResource) {
			System.out.println(mfg);
		}

		ParameterizedTypeReference<Resources<Location>> resLocParameterizedTypeReference =
		        new ParameterizedTypeReference<Resources<Location>>() {};
		Resources<Location> locResource = traverson.follow("locations").
		        toObject(resLocParameterizedTypeReference);
		for (Location loc : locResource) {
			System.out.println(loc);
		}
		ParameterizedTypeReference<Resources<VehicleType>> resTypParameterizedTypeReference =
		        new ParameterizedTypeReference<Resources<VehicleType>>() {};
		Resources<VehicleType> typResource = traverson.follow("vehicleTypes").
		        toObject(resTypParameterizedTypeReference);
		for (VehicleType typ : typResource) {
			System.out.println(typ);
		}
	}

	@Test
	public void getLastVehicle() throws JsonParseException, JsonMappingException, IOException {
		RestTemplate template = new RestTemplate();
		ResponseEntity<Object> response = template.getForEntity(BASE_URI + "/vehicles", Object.class);
		
		System.out.println(response.getBody().toString());
		System.out.println(response.getStatusCodeValue());
		assertNotNull(response);
	}

	// =========================================== CORS Headers
	// ===========================================

}
