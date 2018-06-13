package com.company.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.RequestEntity;
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
        	Long lastId = vehicles.get(vehicles.size()-1).getId();
        	vehicleJpaRepository.deleteById(lastId);
        }
        return ResponseEntity.ok().build(); 
    }
}
