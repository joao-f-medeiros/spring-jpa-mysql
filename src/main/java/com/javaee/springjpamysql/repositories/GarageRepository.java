package com.javaee.springjpamysql.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javaee.springjpamysql.domain.Garage;

@Repository
public interface GarageRepository extends CrudRepository<Garage, Long>{

}
