package com.javaee.springjpamysql.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Garage {

	private Long id;
	
	private String name;
	
	private String address;
	
	private GasStation gasStation;
	
	private Set<Vehicle> vehicles = new HashSet<>();
	
	private Set<Category> categories = new HashSet<>();
	
	public void addCategory(Category category) {
		category.getGarages().add(this);
		this.categories.add(category);
	}
	
	public void addVehicle(Vehicle vehicle) {
		vehicle.setGarage(this);
		this.vehicles.add(vehicle);
	}
}
