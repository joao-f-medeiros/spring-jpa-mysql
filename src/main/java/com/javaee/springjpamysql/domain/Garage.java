package com.javaee.springjpamysql.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Garage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Lob
	private String address;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GasStation gasStation;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "garage")
	private Set<Vehicle> vehicles = new HashSet<>();
	
	@ManyToMany
    @JoinTable(name = "garage_category",
        joinColumns = @JoinColumn(name = "garage_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
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
