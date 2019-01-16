package com.vehicleinventory.service;

import java.sql.SQLException;

import com.vehicleinventory.model.Vehicle;

public interface VehicleInventoryService {

	public boolean setVehicleInventoryRepo() throws SQLException;
	
	public Vehicle insertVehicle(Vehicle vehicle) throws SQLException;

	public Vehicle updateVehicle(Vehicle vehicle) throws SQLException;

	public boolean deleteVehicle() throws SQLException;

}
