package com.company.repository;

import com.company.repository.VehicleJpaRepositoryCustom;

public class VehicleJpaRepositoryImpl implements VehicleJpaRepositoryCustom {

	@Override
	public void aCustomMethod() {
		System.out.println("I'm a custom method");
	}

}