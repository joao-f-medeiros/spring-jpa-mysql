package com.javaee.springjpamysql.repositories;

import com.javaee.springjpamysql.domain.Garage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageRepository extends CrudRepository<Garage, Long>{

    List<Garage> findByName(String name);
}
