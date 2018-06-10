package com.company.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehiclePersistenceTests {
	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private VehicleJpaRepository vehicleJpaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		Vehicle v = new Vehicle();
		v.setBatchNo(10);
		v.setName("Test Vehicle");
		v.setPrice(BigDecimal.valueOf(55L));
		v.setYearFirstMade(new Date());
		v = vehicleRepository.create(v);
		
		// clear the persistence context so we don't return the previously cached location object
		// this is a test only thing and normally doesn't need to be done in prod code
		entityManager.clear();

		Optional<Vehicle> ov = vehicleRepository.find(v.getId());
		Vehicle otherVehicle = ov.get();
		assertEquals("Test Vehicle", otherVehicle.getName());
		assertEquals(10, otherVehicle.getBatchNo());
		
		//delete BC location now
		vehicleRepository.delete(otherVehicle);
		
		vehicleJpaRepository.aCustomMethod();
	}

	@Test
	public void testGetVehiclesInPriceRange() throws Exception {
		List<Vehicle> vehicles = vehicleRepository
				.getVehiclesInPriceRange(BigDecimal.valueOf(10000L), BigDecimal.valueOf(20000L));
		assertEquals(5, vehicles.size());
	}

	@Test
	public void testGetVehiclesByPriceRangeAndWoodType() throws Exception {
		Page<Vehicle> vehicles = vehicleRepository.getVehiclesByPriceRange(BigDecimal.valueOf(1000L), BigDecimal.valueOf(2000L));
		assertEquals(2, vehicles.getSize());
	}

	@Test
	public void testGetVehiclesByType() throws Exception {
		List<Vehicle> vehicles = vehicleRepository.getVehiclesByType("Car");
		assertEquals(6, vehicles.size());
	}

	@Test
	public void testGetVehiclesByTypes() throws Exception {
		List<String> types = new ArrayList<String>();
		types.add("Car");
		types.add("Truck");
		List<Vehicle> vehicles = vehicleJpaRepository.findByVehicleTypeNameIn(types);
		
		vehicles.forEach((vehicle) -> {
			assertTrue(vehicle.getVehicleType().getName().equals("Car") || 
					vehicle.getVehicleType().getName().equals("Truck"));
		});
	}
}
