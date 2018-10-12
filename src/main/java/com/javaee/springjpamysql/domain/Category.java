package com.javaee.springjpamysql.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    private Long id;
	
    private String description;

    private Set<Garage> garages = new HashSet<>();
}
