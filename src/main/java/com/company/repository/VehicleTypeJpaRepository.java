package com.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.model.VehicleType;

@Repository
public interface VehicleTypeJpaRepository extends JpaRepository<VehicleType, Long> {
	List<VehicleType> findByNameIsNull();
}
