package com.company.model.projections;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.company.model.Manufacturer;
import com.company.model.Vehicle;
import com.company.model.VehicleType;

@Projection(name="vehicleDetailView", types= {Vehicle.class})
public interface VehicleDetailView {

	@Value("#{target.name}")  ///The method name can be anything
	String getVehicleName();
	BigDecimal getPrice();
	@Value("#{target.manufacturer.name}")
	String getManufacturerName();
	VehicleType getVehicleType();
	int getBatchNo();
	
	@Value("#{target.manufacturer.name} #{target.name}")
	String getFullName();

}
