package com.vehicleinventory.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicleinventory.dao.VehicleInventoryDao;
import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.service.VehicleInventoryService;

/**
 * @author RameshReddy Komma
 *
 */
@Service
public class VehicleInventoryServiceImpl implements VehicleInventoryService {

	@Autowired
	private VehicleInventoryDao vehicleInventoryDao;

	@Override
	public boolean setVehicleInventoryRepo() throws SQLException {
		return vehicleInventoryDao.setVehicleInventoryRepo();
	}

	@Override
	public Vehicle insertVehicle(Vehicle vehicle) throws SQLException {
		vehicleInventoryDao.insertVehicle(vehicle);
		return vehicle;
	}

	@Override
	public Vehicle updateVehicle(Vehicle vehicle) throws SQLException {
		return vehicleInventoryDao.updateVehicle(vehicle);
	}

	@Override
	public boolean deleteVehicle() throws SQLException {
		return vehicleInventoryDao.deleteVehicle();
	}

}
