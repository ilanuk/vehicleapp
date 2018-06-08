package com.company.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.company.model.VehicleType;

@Repository
public class VehicleTypeRepository {
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Create
	 */
	public VehicleType create(VehicleType mt) {
		entityManager.persist(mt);
		entityManager.flush();
		return mt;
	}

	/**
	 * Update
	 */
	public VehicleType update(VehicleType mt) {
		mt = entityManager.merge(mt);
		entityManager.flush();
		return mt;
	}

	/**
	 * Delete
	 */
	public void delete(VehicleType mt) {
		entityManager.remove(mt);
		entityManager.flush();
	}

	/**
	 * Find
	 */
	public VehicleType find(Long id) {
		return entityManager.find(VehicleType.class, id);
	}
}
