package com.company.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.company.model.VehicleType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleTypePersistenceTests {
	@Autowired
	private VehicleTypeJpaRepository vehicleTypeJpaRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testSaveAndGetAndDelete() throws Exception {
		VehicleType vt = new VehicleType();
		vt.setName("Test Vehicle Type");
		vt = vehicleTypeJpaRepository.save(vt);
		
		// clear the persistence context so we don't return the previously cached location object
		// this is a test only thing and normally doesn't need to be done in prod code
		entityManager.clear();

		Optional<VehicleType> ov = vehicleTypeJpaRepository.findById(vt.getId());
		VehicleType otherVehicleType = ov.get();
		assertEquals("Test Vehicle Type", otherVehicleType.getName());
		
		vehicleTypeJpaRepository.delete(otherVehicleType);
	}

	@Test
	public void testFind() throws Exception {
		Optional<VehicleType> vt = vehicleTypeJpaRepository.findById(1L);
		VehicleType vtype = vt.get();
		assertEquals("Car", vtype.getName());
	}

	@Test
	public void testForNull() throws Exception {
		List<VehicleType> vts = vehicleTypeJpaRepository.findByNameIsNull();
		assertNull(vts.get(0).getName());
	}
}
