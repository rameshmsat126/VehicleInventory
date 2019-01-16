package com.vehicleinventory.controller;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vehicleinventory.constants.VehicleConstants;
import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.model.VehicleType;
import com.vehicleinventory.service.VehicleInventoryService;


/**
 * @author RameshReddy Komma
 *
 */

@RestController
@RequestMapping(VehicleConstants.VEHICLE_BASE_URI)
public class VehicleInventoryController {

	private static final Logger logger = LoggerFactory.getLogger(VehicleInventoryController.class);

	@Autowired
	private VehicleInventoryService service;

	@RequestMapping(VehicleConstants.VEHICLE_INFO_MESSAGE_URI)
	public @ResponseBody ResponseEntity<String> vehicleInfoMessage() {	
		return new ResponseEntity<String>("Welcome to Vehicle Inventory.", HttpStatus.OK);
	}

	/**
	 * create vehicle inventory tables
	 * 
	 * @return
	 */
	@RequestMapping(value = VehicleConstants.VEHICLE_REPO_URI, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> setVehicleInventoryRepo() {
		logger.info("Start creating Vehicles DB");
		try {
			service.setVehicleInventoryRepo();
			return new ResponseEntity<String>("Inventory Database created successfully", HttpStatus.CREATED);
		} catch (SQLException e) {
			logger.error("Exception Message " + e.getLocalizedMessage());
			return new ResponseEntity<String>("Vehicle DB creation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Insert vehicle inventory details
	 * 
	 * @param vehicleType
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value = VehicleConstants.VEHICLE_TYPE_PATH_PARAM, method = RequestMethod.POST)
	public @ResponseBody  ResponseEntity<?> insertVehicle(@PathVariable("vehicleType") VehicleType vehicleType,
			@RequestBody Vehicle vehicle) {
		logger.info("Start insertVehicle.");
		try {
			vehicle.setType(vehicleType);
			service.insertVehicle(vehicle);
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.CREATED);
		} catch (SQLException e) {
			logger.error("Exception Message " + e.getLocalizedMessage());
			return new ResponseEntity<String>("Vehicle Insertion Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * update the vehicle inventory details
	 * 
	 * @param vehicle
	 * @return Vehicle
	 */
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle) {
		logger.info("Start Update Vehicle::" + vehicle.getId());
		try {
			service.updateVehicle(vehicle);
			return new ResponseEntity<Vehicle>(vehicle, HttpStatus.OK);
			
		} catch (SQLException e) {
			logger.error("Exception Message " + e.getLocalizedMessage());
			return new ResponseEntity<String>("Vehicle Updation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * delete the vehicle inventory details
	 * 
	 * @param id
	 * @return Vehicle
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> deleteVehicle() {
		logger.info("Start Delete Vehicle.");
		try {
			service.deleteVehicle();
			return new ResponseEntity<String>("Vehicle Deleted Successfully", HttpStatus.OK);
		} catch (SQLException e) {
			logger.error("Exception Message " + e.getLocalizedMessage());
			return new ResponseEntity<String>("Vehicle Deletion Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
