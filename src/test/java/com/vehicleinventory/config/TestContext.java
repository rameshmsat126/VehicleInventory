package com.vehicleinventory.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vehicleinventory.controller.VehicleInventoryController;
import com.vehicleinventory.controller.VehicleInventorySearchController;
import com.vehicleinventory.dao.VehicleInventoryDao;
import com.vehicleinventory.dao.VehicleInventorySearchDao;
import com.vehicleinventory.service.VehicleInventorySearchService;
import com.vehicleinventory.service.VehicleInventoryService;
/**
 * @author RameshReddy Komma
 *
 */
@Configuration
public class TestContext {

	@Bean
	public VehicleInventorySearchService vehicleInventorySearchServiceMock() {
		return Mockito.mock(VehicleInventorySearchService.class);
	}

	@Bean
	public VehicleInventoryService vehicleInventoryServiceMock() {
		return Mockito.mock(VehicleInventoryService.class);
	}

	@Bean
	public VehicleInventorySearchController vehicleInventorySearchController() throws IllegalAccessException {

		VehicleInventorySearchController controller = new VehicleInventorySearchController();

		return controller;
	}

	@Bean
	public VehicleInventoryController vehicleInventoryController() throws IllegalAccessException {

		VehicleInventoryController controller = new VehicleInventoryController();

		return controller;
	}

	@Bean
	public VehicleInventorySearchDao vehicleInventorySearchDao() {
		return Mockito.mock(VehicleInventorySearchDao.class);
	}

	@Bean
	public com.vehicleinventory.dao.VehicleInventoryDao vehicleInventoryDao() {
		return Mockito.mock(VehicleInventoryDao.class);
	}

}
