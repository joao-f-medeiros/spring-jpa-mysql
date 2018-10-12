package com.javaee.springjpamysql.repositories;

import org.springframework.data.repository.CrudRepository;

import com.javaee.springjpamysql.domain.Garage;

public interface GarageRepository extends CrudRepository<Garage, Long>{

}
