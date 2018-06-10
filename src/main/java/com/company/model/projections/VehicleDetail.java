package com.company.model.projections;

import java.math.BigDecimal;

import org.springframework.data.rest.core.config.Projection;

import com.company.model.Manufacturer;
import com.company.model.Vehicle;
import com.company.model.VehicleType;

@Projection(name="vehicleDetail", types= {Vehicle.class})
public interface VehicleDetail {
	
	String getName();
	BigDecimal getPrice();
	Manufacturer getManufacturer();
	VehicleType getVehicleType();
	int getBatchNo();
}
