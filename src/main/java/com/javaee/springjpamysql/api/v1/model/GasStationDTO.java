package com.javaee.springjpamysql.api.v1.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GasStationDTO {

	private Long id;
	
	@NotBlank
    @Size(min = 2, max = 255)
	private String name;
}
