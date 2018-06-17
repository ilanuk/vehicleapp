package com.company.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@NamedNativeQuery(name = "Manufacturer.getAllThatSellVehicles", 
		query = "SELECT m.id, m.name, m.foundedDate, m.averageYearlySales, m.location_id as headquarters_id, m.active "
	    + "FROM Manufacturer m "
		+ "LEFT JOIN Vehicle mod ON (m.id = mod.manufacturer_id) "
		+ "LEFT JOIN VehicleType mt ON (mt.id = mod.vehicletype_id) "
	    + "WHERE (mt.name = ?)", resultClass = Manufacturer.class)
public class Manufacturer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8947917447657177988L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Column(name="FOUNDEDDATE")
	private Date foundedDate;

	@Column(name="AVERAGEYEARLYSALES")
	private BigDecimal averageYearlySales;
	private Boolean active;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="manufacturer_id")
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();

	@ManyToOne
	@JoinColumn(name="LOCATION_ID", nullable=false, updatable=false)
	private Location headquarters;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFoundedDate() {
		return foundedDate;
	}

	public void setFoundedDate(Date foundedDate) {
		this.foundedDate = foundedDate;
	}

	public BigDecimal getAverageYearlySales() {
		return averageYearlySales;
	}

	public void setAverageYearlySales(BigDecimal averageYearlySales) {
		this.averageYearlySales = averageYearlySales;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public Location getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(Location headquarters) {
		this.headquarters = headquarters;
	}

	public Long getId() {
		return id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id 
				+ "\", \"name\":\"" + name 
				+ "\", \"foundedDate\":\"" + foundedDate 
				+ "\", \"averageYearlySales\":\"" + averageYearlySales 
				+ "\", \"active\":\"" + active 
				+ "\", \"vehicles\":\"" + vehicles 
				+ "\", \"headquarters\":\"" + headquarters + "\"}";
	}
	
	
}

