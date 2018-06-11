package com.company.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="VEHICLETYPE")
public class VehicleType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6797910556821821932L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;	

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="VEHICLETYPE_ID")
	private List<Vehicle> Vehicles = new ArrayList<Vehicle>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Vehicle> getVehicles() {
		return Vehicles;
	}

	public void setVehicles(List<Vehicle> Vehicles) {
		this.Vehicles = Vehicles;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id 
				+ "\", \"name\":\"" + name 
				+ "\", \"Vehicles\":\"" + Vehicles + "\"}";
	}
	
}
