package com.javaee.springjpamysql.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vehicle {

    private Long id;
	
	private String name;
	
	private Integer year;
	
	private FuelType fuelType;

    private Garage garage;
}
