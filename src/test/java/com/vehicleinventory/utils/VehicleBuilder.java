package com.vehicleinventory.utils;

import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.model.VehicleType;
/**
 * @author RameshReddy Komma
 *
 */
public class VehicleBuilder {

	private Vehicle vehicle;

	public VehicleBuilder() {
		vehicle = new Vehicle();
	}

	public VehicleBuilder id(int id) {
		vehicle.setId(id);
		return this;
	}

	public VehicleBuilder name(String name) {
		vehicle.setName(name);
		return this;
	}

	public VehicleBuilder model(String model) {
		vehicle.setModel(model);
		return this;
	}

	public VehicleBuilder engine(String engine) {
		vehicle.setEngine(engine);
		return this;
	}

	public VehicleBuilder make(String make) {
		vehicle.setMake(make);
		return this;
	}

	public VehicleBuilder type(VehicleType vehicleType) {
		vehicle.setType(vehicleType);
		return this;
	}
	
	public VehicleBuilder year(int year) {
		vehicle.setYear(year);
		return this;
	}

	public Vehicle build() {
		return vehicle;
	}
}
