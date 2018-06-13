package com.company.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;

public class VehicleJpaRepositoryImpl implements VehicleJpaRepositoryCustom {

	@Override
	public void aCustomMethod() {
		System.out.println("I'm a custom method");
	}

	@Override
	@DeleteMapping(path="/")
	@Query("delete v from Vehicle v where v.id = ( select max(id) from Vehicle)")
	public void deleteLastVehicle() {
	}

}