/**
 * 
 */
package com.vehicleinventory.constants;

/**
 * @author RameshReddy Komma
 *
 */
public class VehicleConstants {

	public static final String DB_DRIVER = "org.h2.Driver";
	public static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	public static final String DB_USER = "";
	public static final String DB_PASSWORD = "";
	public static final String VEHICLE_BASE_URI = "/vehicle";
	public static final String VEHICLE_SEARCH_BASE_URI = "/vehicleSearch";
	public static final String VEHICLE_INFO_MESSAGE_URI = "/infoMessage";
	public static final String VEHICLE_REPO_URI = "/repo";
	public static final String VEHICLE_TYPE_PATH_PARAM = "/type/{vehicleType}";
	public static final String GET_VEHICLE_DATA_BASED_ON_YEAR_URI = "/year";
	public static final String YEAR_PATH__PARAM="/{year}";
	public static final String VEHICLE_ID_PATH_PARAM = "{id}";
}
