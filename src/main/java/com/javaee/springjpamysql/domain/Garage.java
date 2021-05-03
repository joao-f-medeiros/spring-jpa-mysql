package com.javaee.springjpamysql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@EntityListeners(AuditListener.class)
@ToString(exclude = "categories")
public class Garage implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;
	
	@Lob
	private String address;
	
	@OneToOne(cascade = CascadeType.ALL)
	private GasStation gasStation;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "garage")
	private Set<Vehicle> vehicles = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
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

	@Override
	@JsonIgnore
	public boolean isNew() {
		return id == null;
	}
}
