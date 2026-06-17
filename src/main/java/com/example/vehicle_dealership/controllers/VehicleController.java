package com.example.vehicle_dealership.controllers;

import com.example.vehicle_dealership.entities.Vehicle;
import com.example.vehicle_dealership.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class VehicleController {
    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicle(
            @RequestParam(value = "make", required = false) String make,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice
    ){
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (minPrice != null || maxPrice != null) {
            double effectiveMin = (minPrice != null) ? minPrice : 0.0;
            double effectiveMax = (maxPrice != null) ? maxPrice : Double.MAX_VALUE;

            List<Vehicle> vehicles = this.vehicleService.searchVehicleByPriceRange(effectiveMin, effectiveMax);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
        else if(make == null) {
            List<Vehicle> vehicles = this.vehicleService.getAllVehicle();

            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
        else {
            List<Vehicle> vehicles = this.vehicleService.searchVehicle(make);
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id){
        Optional<Vehicle> vehicle = this.vehicleService.getVehicleByID(id);

        if(vehicle.isPresent()){
            return new ResponseEntity<>(vehicle.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        boolean deleteSuccessful = this.vehicleService.delete(id);

        if (!deleteSuccessful){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody @Valid Vehicle vehicle){
        Vehicle newVehicle = this.vehicleService.create(vehicle);

        return new ResponseEntity<>(newVehicle, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody @Valid Vehicle vehicle, @PathVariable Long id){
        Vehicle updatedVehicle = this.vehicleService.update(id, vehicle);

        if(updatedVehicle == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
        }
    }
}
