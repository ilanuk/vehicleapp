package com.company.repository;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.company.MainApplication;
import com.company.model.Vehicle;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
@WebAppConfiguration
public class VehicleRestEndpointTests1 {

//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Test
	public void listVehiclesWorksOK() {
		RestTemplate template = new RestTemplate();
		ResponseEntity<Vehicle> result =
				template.getForEntity("http://localhost:8080/api/vehicles/1", 
						Vehicle.class);
		assertNotNull(result);
		assertNotNull(result.getBody());
		System.out.println(result.getBody().toString());
		Vehicle veh = result.getBody();
		System.out.println(veh.getName());
	}
	
}
