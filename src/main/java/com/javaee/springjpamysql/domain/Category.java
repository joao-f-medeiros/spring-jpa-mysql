package com.javaee.springjpamysql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString(exclude = "garages")
public class Category implements Persistable<Long> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String description;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private Set<Garage> garages = new HashSet<>();

    @Override
    public boolean isNew() {
        return id == null;
    }
}
