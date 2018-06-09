package com.company.repository;


import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.company.MainApplication;
import com.company.model.Manufacturer;
import com.company.repository.ManufacturerJpaRepository;
import com.company.repository.ManufacturerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManufacturerPersistenceTests {
	@Autowired
	private ManufacturerRepository manufacturerRepository;

	@Autowired
	private ManufacturerJpaRepository manufacturerJpaRepository;

	@Test
	public void testGetManufacturersFoundedBeforeDate() throws Exception {
		List<Manufacturer> mans = manufacturerRepository
				.getManufacturersFoundedBeforeDate(new Date());
		assertEquals(4, mans.size());
	}

	@Test
	public void testTrueFalse() throws Exception {
		List<Manufacturer> mans = manufacturerJpaRepository.findByActiveTrue();
		assertEquals("General Motors Corporation", mans.get(0).getName());

		mans = manufacturerJpaRepository.findByActiveFalse();
		assertEquals("AlumaCraft Corporation", mans.get(0).getName());
}

	@Test
	public void testGetManufactureByName() throws Exception {
		Manufacturer m = manufacturerRepository.getManufacturerByName("Ford Corporation");
		assertEquals("Ford Corporation", m.getName());
	}

	@Test
	public void testGetManufacturersThatSellModelsOfType() throws Exception {
		List<Manufacturer> mans = manufacturerRepository
				.getManufacturersThatSellVehiclesOfType("Car");
		assertEquals(6, mans.size());
	}
}
