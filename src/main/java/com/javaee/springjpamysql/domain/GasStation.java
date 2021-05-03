package com.javaee.springjpamysql.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class GasStation implements Persistable<Long> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Garage garage;
    
    private String name;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
