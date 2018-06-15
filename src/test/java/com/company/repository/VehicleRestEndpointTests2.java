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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.company.model.Location;
import com.company.model.Manufacturer;
import com.company.model.Vehicle;
import com.company.model.VehicleType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleRestEndpointTests2 {
	//check the RepositoryConfig settings to change the baseURI
	private static final String BASE_URI = "http://localhost:8080/api";
	private static final int UNKNOWN_ID = Integer.MAX_VALUE;

	@Test
	public void listVehicleWorksOK() {
		WebClient webclient = WebClient.create(BASE_URI);
		long id = 1;
		Mono<Vehicle> result =	webclient.get()
				.uri("/vehicles/{id}",id).accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Vehicle.class);
		assertNotNull(result);
		Vehicle vehicle = result.blockOptional().get();
		System.out.println(vehicle);
		assertThat(vehicle.getId(),is(id));
	}

	@Test
	public void test_get_one_success() {
//		RestTemplate template = new RestTemplate();
//		ResponseEntity<Vehicle> response = template.getForEntity(BASE_URI + "/vehicles/1", Vehicle.class);
//		Vehicle vehicle = response.getBody();
//		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		WebClient webclient = WebClient.create(BASE_URI);
		long id = 1;
		Mono<ClientResponse> response =	webclient.get()
				.uri("/vehicles/{id}",id).accept(MediaType.APPLICATION_JSON)
				.exchange();
		assertNotNull(response);
		assertThat(response.blockOptional().get().statusCode(),is(HttpStatus.OK));
		Mono<Vehicle> monoBody =
				response.flatMap(clientResponse -> clientResponse.bodyToMono(Vehicle.class));
		Vehicle vehicle = monoBody.blockOptional().get();
		System.out.println(vehicle);
		assertThat(vehicle.getName(), is("Chevrolet Bolt EV"));
	}

	@Test
	public void test_get_all_success() {
//		RestTemplate template = new RestTemplate();
//		ResponseEntity<Vehicle> response = template.getForEntity(BASE_URI + "/vehicles/1", Vehicle.class);
//		Vehicle vehicle = response.getBody();
//		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		ParameterizedTypeReference<Resources<Vehicle>> resourceParameterizedTypeReference =
		        new ParameterizedTypeReference<Resources<Vehicle>>() {};
		WebClient webclient = WebClient.create(BASE_URI);
		Mono<ClientResponse> response =	webclient.get()
				.uri("/vehicles").accept(MediaTypes.HAL_JSON)
				.exchange();
		assertNotNull(response);
		Object obj = response.blockOptional().get().toEntity(resourceParameterizedTypeReference);
		assertThat(response.blockOptional().get().statusCode(),is(HttpStatus.OK));
		//Mono<List<Vehicle> monoBody =
				//response.flatMap(clientResponse -> clientResponse.bodyToMono(Vehicle.class));
		Mono<ResponseEntity<Resources<Vehicle>>> vehicleResourceMono = 
				response.blockOptional().get().toEntity(resourceParameterizedTypeReference);
		 Mono<Resources<Vehicle>> mono 
		 = response.blockOptional().get().bodyToMono(resourceParameterizedTypeReference);
		 
		 System.out.println("mono="+ mono);
		Resources<Vehicle> vehicles = mono.blockOptional().get();
		System.out.println("vehicleResourceMono="+ vehicleResourceMono);
		
		Resources<Vehicle> vehicleResource = vehicleResourceMono
				.blockOptional().get().getBody();
		System.out.println("vehicleResource="+ vehicleResource);
		System.out.println("vehicles="+ vehicles);
		for (Vehicle vehicle : vehicleResource) {
			System.out.println(vehicle);
		}

		//		Vehicle vehicle = monoBody.blockOptional().get();
//		System.out.println(vehicle);
//		assertThat(vehicle.getName(), is("Chevrolet Bolt EV"));
	}
	@Test
	public void test_get_by_id_failure_not_found() {
		try {
//			RestTemplate template = new RestTemplate();
//			ResponseEntity<Vehicle> response = template.getForEntity(BASE_URI + "/vehicles/" + UNKNOWN_ID,
//					Vehicle.class);
			WebClient webclient = WebClient.create(BASE_URI);
			long id = UNKNOWN_ID;
			Mono<ClientResponse> response =	webclient.get()
					.uri("/vehicles/{id}",id).accept(MediaType.APPLICATION_JSON)
					.exchange();
			assertNotNull(response);
			assertThat(response.blockOptional().get().statusCode(),is(HttpStatus.NOT_FOUND));
		} catch (HttpClientErrorException e) {
			assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
		}
	}

	@Test
	public void test_create_new_vehicle_success() {
//		RestTemplate template = new RestTemplate();
//		URI location = template.postForLocation(BASE_URI + "/vehicles/", v, Vehicle.class);
		Vehicle v = new Vehicle();
		v.setBatchNo(10);
		v.setName("Test Vehicle");
		v.setPrice(BigDecimal.valueOf(55L));
		v.setYearFirstMade(new Date());
		WebClient webclient = WebClient.create(BASE_URI);
		Mono<ClientResponse> response =	webclient.post()
				.uri("/vehicles")
				.body(Mono.just(v), Vehicle.class)
				.accept(MediaType.APPLICATION_JSON)
				.exchange();
		ClientResponse cresponse = response.blockOptional().get();
		assertThat(cresponse.statusCode(),
				is(HttpStatus.CREATED));
		System.out.println(cresponse.statusCode());

		URI location = cresponse.headers().asHttpHeaders().getLocation();
		System.out.println(location.getPath());
		assertThat(location, notNullValue());
		Mono<ClientResponse> nresponse =	webclient.get()
				.uri(location).accept(MediaType.APPLICATION_JSON)
				.exchange();
		assertNotNull(nresponse);
		assertThat(nresponse.blockOptional().get().statusCode(),
				is(HttpStatus.OK));
		Mono<Vehicle> monoBody =
				nresponse.flatMap(clientResponse -> clientResponse.bodyToMono(Vehicle.class));
		Vehicle vehicle = monoBody.blockOptional().get();
		System.out.println(vehicle);

		System.out.println(vehicle.getName());
		System.out.println(vehicle.getId());
		//delete the newly created entity
		Mono<ClientResponse> dresponse =	webclient.delete()
				.uri(location).accept(MediaType.APPLICATION_JSON)
				.exchange();
		assertNotNull(dresponse);
		assertThat(dresponse.blockOptional().get().statusCode(),
				is(HttpStatus.NO_CONTENT));

		//		template.delete(location);
		try {
//			RestTemplate template = new RestTemplate();
//			ResponseEntity<Vehicle> response1 = template.getForEntity(location, Vehicle.class);
			Mono<ClientResponse> fresponse =	webclient.get()
					.uri(location).accept(MediaType.APPLICATION_JSON)
					.exchange();
			assertNotNull(fresponse);
			assertThat(fresponse.blockOptional().get().statusCode(),
					is(HttpStatus.NOT_FOUND));
			//fail("should return 404 not found");
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

}
