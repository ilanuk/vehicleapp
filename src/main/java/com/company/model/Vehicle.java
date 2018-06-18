package com.company.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="Vehicle.findAllVehiclesByType", query="select m from Vehicle m where m.vehicleType.name = :name")
public class Vehicle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8699778740434948317L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;	

	private BigDecimal price;

	@Column(name="YEARFIRSTMADE")
	private Date yearFirstMade;
	
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	private Manufacturer manufacturer;

	@ManyToOne
	@JoinColumn(name="VEHICLETYPE_ID")
	private VehicleType vehicleType;

	@Column(name="BATCHNO")
	private int batchNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}

	public Date getYearFirstMade() {
		return yearFirstMade;
	}

	public void setYearFirstMade(Date yearFirstMade) {
		this.yearFirstMade = yearFirstMade;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\", \"name\":\"" + name + "\",\"price\":\"" + price +
				"\",\"yearFirstMade\":\"" + yearFirstMade
				+ "\",\"manufacturer\":\"" + manufacturer +
				"\",\"vehicleType\":\"" + vehicleType +
				"\",\"batchNo\":\"" + batchNo + "\"}";
	}

	
	
}
