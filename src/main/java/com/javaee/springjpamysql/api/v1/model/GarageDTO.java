package com.javaee.springjpamysql.api.v1.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarageDTO {

	private Long id;
	
	private String name;
	
	private String address;
	
	private GasStationDTO gasStation;
	
	private Set<VehicleDTO> vehicles = new HashSet<>();
	
	private Set<CategoryDTO> categories = new HashSet<>();
}
