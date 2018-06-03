package com.javaee.springjpamysql.api.v1.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

	private Long id;

	@NotBlank
    private String description;
}
