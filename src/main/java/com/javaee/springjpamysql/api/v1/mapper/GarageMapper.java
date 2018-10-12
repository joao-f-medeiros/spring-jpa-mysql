package com.javaee.springjpamysql.api.v1.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.javaee.springjpamysql.api.v1.model.GarageDTO;
import com.javaee.springjpamysql.domain.Category;
import com.javaee.springjpamysql.domain.Garage;
import com.javaee.springjpamysql.domain.GasStation;
import com.javaee.springjpamysql.domain.Vehicle;
import com.javaee.springjpamysql.repositories.CategoryRepository;
import com.javaee.springjpamysql.repositories.GasStationRepository;
import com.javaee.springjpamysql.repositories.VehicleRepository;

public class GarageMapper {
	
	private GasStationMapper gasStationMapper;
	private CategoryMapper categoryMapper;
	private VehicleMapper vehicleMapper;
	
	private GasStationRepository gasStationRepository;
	private CategoryRepository categoryRepository;
	private VehicleRepository vehicleRepository;
	
	public GarageMapper(GasStationMapper gasStationMapper, CategoryMapper categoryMapper, VehicleMapper vehicleMapper,
						GasStationRepository gasStationRepository, CategoryRepository categoryRepository,
						VehicleRepository vehicleRepository) {
		this.gasStationMapper = gasStationMapper;
		this.categoryMapper = categoryMapper;
		this.vehicleMapper = vehicleMapper;
		
		this.gasStationRepository = gasStationRepository;
		this.categoryRepository = categoryRepository;
		this.vehicleRepository = vehicleRepository;
	}

	public GarageDTO garageToGarageDTO(Garage garage) {
		final GarageDTO garageDTO = new GarageDTO();
		garageDTO.setId(garage.getId());
		garageDTO.setName(garage.getName());
		garageDTO.setAddress(garage.getAddress());
		garageDTO.setGasStation(gasStationMapper.gasStationToGasStationDTO(garage.getGasStation()));
		
		if (garage.getCategories() != null && garage.getCategories().size() > 0){
			garage.getCategories()
                    .forEach(category -> garageDTO.getCategories().add(categoryMapper.categoryToCategoryDTO(category)));
        }
		
		if (garage.getVehicles() != null && garage.getVehicles().size() > 0){
			garage.getVehicles()
                    .forEach(vehicle -> garageDTO.getVehicles().add(vehicleMapper.vehicleToVehicleDTO(vehicle)));
        }
		
		return garageDTO;
	}

	public Garage garageDTOToGarage(GarageDTO garageDTO) {
		final Garage garage = new Garage();
		garage.setId(garageDTO.getId());
		garage.setName(garageDTO.getName());
		garage.setAddress(garageDTO.getAddress());
		
		GasStation detachedGasStation = fillGasStation(garageDTO, garage);
		fillCategories(garageDTO, garage);
		fillVehicles(garageDTO, garage, detachedGasStation);
		return garage;
    }

	private GasStation fillGasStation(GarageDTO garageDTO, final Garage garage) {
		GasStation detachedGasStation = gasStationMapper.gasStationDTOToGasStation(garageDTO.getGasStation());
		detachedGasStation.setGarage(garage);
		if(detachedGasStation.getId() != null) {
			Optional<GasStation> gasStationOptional= gasStationRepository.findById(detachedGasStation.getId());
			if(!gasStationOptional.isPresent()) {	
				throw new IllegalArgumentException("Gas Station Not Found For ID value: " + detachedGasStation.getId());
			}
			gasStationOptional.get().setGarage(garage);
			garage.setGasStation(gasStationOptional.get());
		}else {
			garage.setGasStation(detachedGasStation);
		}
		return detachedGasStation;
	}

	private void fillCategories(GarageDTO garageDTO, final Garage garage) {
		if (garageDTO.getCategories() != null && garageDTO.getCategories().size() > 0){
            Set<Category> detachedCategories = garageDTO.getCategories().stream()
            	.map(categoryDTO -> categoryMapper.categoryDTOToCategory(categoryDTO))
            	.collect(Collectors.toSet());
            
            detachedCategories.forEach(category -> {
            	Optional<Category> categoryOptional = categoryRepository.findById(category.getId());
            	if(categoryOptional.isPresent()) {
            		Category categorySaved = categoryOptional.get();
            		garage.addCategory(categorySaved);
            	}
            });
        }
	}

	private void fillVehicles(GarageDTO garageDTO, final Garage garage, GasStation detachedGasStation) {
		if (garageDTO.getVehicles() != null && garageDTO.getVehicles().size() > 0){
			Set<Vehicle> detachedVehicles = garageDTO.getVehicles().stream()
	            	.map(vehicleDTO -> vehicleMapper.vehicleDTOToVehicle(vehicleDTO))
	            	.collect(Collectors.toSet());
			
			detachedVehicles.forEach(vehicle -> {
				if(vehicle.getId() != null) {
					Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicle.getId());
					if(!vehicleOptional.isPresent()) {
						throw new IllegalArgumentException("Vehicle Not Found For ID value: " + detachedGasStation.getId());
					}
					Vehicle vehicleSaved = vehicleOptional.get();
					garage.addVehicle(vehicleSaved);
				} else {
					garage.addVehicle(vehicle);
				}
			});
        }
	}
}
