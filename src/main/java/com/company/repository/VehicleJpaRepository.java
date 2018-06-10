package com.company.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.company.model.Vehicle;
import com.company.model.projections.VehicleDetailView;

@Repository
@RepositoryRestResource(excerptProjection= VehicleDetailView.class)
public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long>, VehicleJpaRepositoryCustom {
	List<Vehicle> findByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal low, BigDecimal high);
	
	List<Vehicle> findByVehicleTypeNameIn(List<String> types);
	
	@Query("select v from Vehicle v where v.price >= :lowest and v.price <= :highest ")
	Page<Vehicle> queryByPriceRange(@Param("lowest") BigDecimal lowest,
											 @Param("highest") BigDecimal high,
											 Pageable page);
	
	List<Vehicle> findAllVehiclesByType(@Param("name") String name);
}
