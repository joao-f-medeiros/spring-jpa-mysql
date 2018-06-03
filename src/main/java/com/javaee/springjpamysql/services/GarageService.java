package com.javaee.springjpamysql.services;

import java.util.List;

import com.javaee.springjpamysql.api.v1.model.GarageDTO;

public interface GarageService {

	List<GarageDTO> getAll();

	GarageDTO getById(Long id);

	GarageDTO createNew(GarageDTO garage);

	GarageDTO save(Long id, GarageDTO garage);

	void deleteById(Long id);

}
