package com.javaee.springjpamysql.api.v1.mapper;

import org.springframework.stereotype.Component;

import com.javaee.springjpamysql.api.v1.model.VehicleDTO;
import com.javaee.springjpamysql.domain.Vehicle;

@Component
public class VehicleMapper {

	public VehicleDTO vehicleToVehicleDTO(Vehicle vehicle) {
		final VehicleDTO vehicleDTO = new VehicleDTO();
		vehicleDTO.setId(vehicle.getId());
		vehicleDTO.setName(vehicle.getName());
		vehicleDTO.setYear(vehicle.getYear());
		vehicleDTO.setFuelType(vehicle.getFuelType());
		return vehicleDTO;
	}

    public Vehicle vehicleDTOToVehicle(VehicleDTO vehicleDTO) {
    	final Vehicle vehicle = new Vehicle();
		vehicle.setId(vehicleDTO.getId());
		vehicle.setName(vehicleDTO.getName());
		vehicle.setYear(vehicleDTO.getYear());
		vehicle.setFuelType(vehicleDTO.getFuelType());
		return vehicle;
    }
}
