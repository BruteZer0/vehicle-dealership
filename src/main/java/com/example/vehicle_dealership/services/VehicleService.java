package com.example.vehicle_dealership.services;

import com.example.vehicle_dealership.entities.Vehicle;
import com.example.vehicle_dealership.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicle(){
        List<Vehicle> vehicleList = vehicleRepository.findAll();

        return vehicleList;
    }

    public Optional<Vehicle> getVehicleByID(long id){
        var vehicle = vehicleRepository.findById(id);

        return vehicle;
    }

    public List<Vehicle> searchVehicle(String make){
        return vehicleRepository.findByMakeContainingIgnoreCase(make);
    }

    public List<Vehicle> searchVehicleByPriceRange(Double minPrice, Double maxPrice){
        return vehicleRepository.findByPriceBetween(minPrice, maxPrice);
    }


    public boolean delete(Long id){
        Optional<Vehicle> vehicleToDelete = vehicleRepository.findById(id);

        if(vehicleToDelete.isEmpty()){
            return false;
        }

        vehicleRepository.delete(vehicleToDelete.get());
        return true;
    }

    public Vehicle create(Vehicle vehicle) {
        Vehicle newVehicle = vehicleRepository.save(vehicle);

        return newVehicle;
    }

        public Vehicle update (Long id, Vehicle vehicle){
            Optional<Vehicle> updateVehicle = vehicleRepository.findById(id);

            if (updateVehicle.isEmpty()) {
                return null;
            }

            Vehicle vehicleToUpdate = updateVehicle.get();

            vehicleToUpdate.setMake(vehicle.getMake());
            vehicleToUpdate.setModel(vehicle.getModel());
            vehicleToUpdate.setType(vehicle.getType());
            vehicleToUpdate.setColor(vehicle.getColor());
            vehicleToUpdate.setYear(vehicle.getYear());
            vehicleToUpdate.setMiles(vehicle.getMiles());
            vehicleToUpdate.setPrice(vehicle.getPrice());

            vehicleRepository.save(vehicleToUpdate);

            return vehicleToUpdate;
        }
}
