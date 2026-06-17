package com.example.vehicle_dealership.repositories;

import com.example.vehicle_dealership.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByMakeContainingIgnoreCase(String vehicle);

    List<Vehicle> findByPriceBetween(Double minPrice, Double maxPrice);
}
