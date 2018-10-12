package com.javaee.springjpamysql.api.v1.model;

import com.javaee.springjpamysql.domain.FuelType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {

	private Long id;
	
	private String name;
	
	private Integer year;
	
	private FuelType fuelType;
}
