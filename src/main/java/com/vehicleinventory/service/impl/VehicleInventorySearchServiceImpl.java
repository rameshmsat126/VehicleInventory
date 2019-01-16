package com.vehicleinventory.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicleinventory.dao.VehicleInventorySearchDao;
import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.service.VehicleInventorySearchService;
/**
 * @author RameshReddy Komma
 *
 */

@Service
public class VehicleInventorySearchServiceImpl implements VehicleInventorySearchService{

	@Autowired
	private VehicleInventorySearchDao vehicleInventorySearchDao;
	
	public List<Vehicle> getAllVehicles() throws SQLException {
		return (List<Vehicle>) vehicleInventorySearchDao.getAllVehicles();
	}

	public List<Vehicle> getVehiclesByYear(int year) throws SQLException {
		return (List<Vehicle>) vehicleInventorySearchDao.getVehiclesByYear(year);
	}

	public Vehicle getVehicle(int type) throws SQLException {
		return (Vehicle) vehicleInventorySearchDao.getVehicle(type);
	}
}