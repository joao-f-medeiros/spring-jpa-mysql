package com.javaee.springjpamysql.api.v1.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GarageDTO {

	private Long id;
	
	@NotBlank
    @Size(min = 2, max = 255)
	private String name;
	
	@NotBlank
	private String address;
	
	@NotNull
	private GasStationDTO gasStation;
	
	@NotEmpty
	private Set<VehicleDTO> vehicles = new HashSet<>();
	
	@NotEmpty
	private Set<CategoryDTO> categories = new HashSet<>();
}
