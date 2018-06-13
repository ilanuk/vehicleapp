package com.company.repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.company.model.Vehicle;

@RepositoryRestController
public class VehicleController {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VehicleJpaRepository vehicleJpaRepository;
	
	
    @RequestMapping(value = "/vehicles/last", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteLastVehicle() {
        List<Vehicle> vehicles = vehicleJpaRepository.findAll();
        if(!vehicles.isEmpty()) {
        	OptionalInt lastId = vehicles.stream().mapToInt(veh->veh.getId().intValue())
        			.max();
        	vehicleJpaRepository.deleteById((long) lastId.getAsInt());
        }
        return ResponseEntity.ok().build(); 
    }
}
