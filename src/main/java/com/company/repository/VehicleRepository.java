package com.company.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.company.model.Vehicle;

@Repository
public class VehicleRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private VehicleJpaRepository vehicleJpaRepository;
	
	/**
	 * Create
	 */
	public Vehicle create(Vehicle vehicle) {
		return vehicleJpaRepository.saveAndFlush(vehicle);
	}

	/**
	 * Update
	 */
	public Vehicle update(Vehicle vehicle) {
		return vehicleJpaRepository.saveAndFlush(vehicle);
	}

	/**
	 * Delete
	 */
	public void delete(Vehicle vehicle) {
		vehicleJpaRepository.delete(vehicle);
	}

	/**
	 * Find
	 */
	public Vehicle find(Long id) {
		return vehicleJpaRepository.findOne(id);
	}

	/**
	 * Custom finder
	 */
	public List<Vehicle> getVehiclesInPriceRange(BigDecimal lowest, BigDecimal highest) {
		return vehicleJpaRepository.findByPriceGreaterThanEqualAndPriceLessThanEqual(lowest, highest);
	}

	/**
	 * Custom finder
	 */
	public Page<Vehicle> getVehiclesByPriceRange(BigDecimal lowest, BigDecimal highest) {
		Sort sort = new Sort(Sort.Direction.ASC, "name");
		Pageable page = new PageRequest(0, 2, sort);
		return vehicleJpaRepository.queryByPriceRange(lowest, highest, page);
	}

	/**
	 * NamedQuery finder
	 */
	public List<Vehicle> getVehiclesByType(String vehicleType) {
		return vehicleJpaRepository.findAllVehiclesByType(vehicleType);
	}

	/**
	 * Count
	 */
	public Long getVehicleCount() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(Vehicle.class)));
		return entityManager.createQuery(cq).getSingleResult();
	}
}
