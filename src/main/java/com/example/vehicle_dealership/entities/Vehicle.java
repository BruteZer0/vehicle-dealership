package com.example.vehicle_dealership.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Make is required")
    @Size(min = 1, max = 50, message = "Make must be between 1 and 50 characters")
    private String make;

    @NotBlank(message = "Model is required.")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model;

    @NotBlank(message = "Color is required")
    @Size(min = 1, max = 30, message = "Color must be between 1 and 30 characters")
    private String color;

    @NotBlank(message = "Type is required")
    @Size(min = 1, max = 30, message = "Type must be between 1 and 30 characters")
    private String type;

    @Min(value = 1900, message = "Year must be 1900 or later")
    @Max(value = 2027, message = "Year cannot be more than one year in the future")
    private int year;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @PositiveOrZero(message = "Miles cannot be negative")
    private int miles;

}
