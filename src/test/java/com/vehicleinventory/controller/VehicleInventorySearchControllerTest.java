package com.vehicleinventory.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.vehicleinventory.config.TestContext;
import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.model.VehicleType;
import com.vehicleinventory.service.VehicleInventorySearchService;
import com.vehicleinventory.service.VehicleInventoryService;
import com.vehicleinventory.utils.TestUtil;
import com.vehicleinventory.utils.VehicleBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class VehicleInventorySearchControllerTest {

	@Autowired 
    private VehicleInventorySearchService vehicleInventorySearchServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired 
    private VehicleInventorySearchController vehicleInventorySearchController;
    
    
    @Before
    public void setUp() {
        
        Mockito.reset(vehicleInventorySearchServiceMock);

        MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllVehiclesSuccess() throws Exception 
    {
    	Vehicle vehicle1 = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    	Vehicle vehicle2 = new VehicleBuilder().id(2).type(VehicleType.AIRPLANE).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	vehicles.add(vehicle1);
    	vehicles.add(vehicle2);
    	when(vehicleInventorySearchServiceMock.getAllVehicles()).thenReturn(vehicles);
    	ResponseEntity<List<Vehicle>> response= vehicleInventorySearchController.getAllVehicles();
    	verify(vehicleInventorySearchServiceMock, times(1)).getAllVehicles();
    	assertEquals(HttpStatus.OK,response.getStatusCode());
        List<Vehicle> responseBody=(List<Vehicle>) response.getBody();
        assertEquals(vehicles,responseBody);
    }
    
    @Test
    public void testGetAllVehiclesEmptyResultSuccess() throws Exception 
    {
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	when(vehicleInventorySearchServiceMock.getAllVehicles()).thenReturn(vehicles);
    	ResponseEntity<List<Vehicle>> response= vehicleInventorySearchController.getAllVehicles();
    	verify(vehicleInventorySearchServiceMock, times(1)).getAllVehicles();
    	assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        List<Vehicle> responseBody=(List<Vehicle>) response.getBody();
        assertEquals(vehicles,responseBody);
    }
    
    @Test
    public void testGetAllVehiclesFailure() throws Exception 
    {
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	when(vehicleInventorySearchServiceMock.getAllVehicles()).thenThrow(new SQLException());
    	ResponseEntity<List<Vehicle>> response= vehicleInventorySearchController.getAllVehicles();
    	verify(vehicleInventorySearchServiceMock, times(1)).getAllVehicles();
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());    	
    }
    
    @Test
    public void testGetVehicleByIdSuccess() throws Exception 
    {
    	Vehicle vehicle = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    	    
       	when(vehicleInventorySearchServiceMock.getVehicle(Mockito.anyInt())).thenReturn(vehicle);
    	ResponseEntity<Vehicle> response= vehicleInventorySearchController.getVehicleById(1);
    	verify(vehicleInventorySearchServiceMock, times(1)).getVehicle(1);
    	assertEquals(HttpStatus.OK,response.getStatusCode());
        Vehicle responseBody=(Vehicle) response.getBody();
        assertEquals(vehicle,responseBody);
    }
    
    @Test
    public void testGetVehicleByIdEmptyResultSuccess() throws Exception 
    {
    	Vehicle vehicle=new Vehicle();
    	when(vehicleInventorySearchServiceMock.getVehicle(Mockito.anyInt())).thenReturn(vehicle);
    	ResponseEntity<Vehicle> response= vehicleInventorySearchController.getVehicleById(1);
    	verify(vehicleInventorySearchServiceMock, times(1)).getVehicle(1);
    	assertEquals(HttpStatus.OK,response.getStatusCode());
    	Vehicle responseBody=(Vehicle) response.getBody();
        assertEquals(vehicle,responseBody);
    }
    
    @Test
    public void testGetVehicleByIdFailure() throws Exception 
    {
    	when(vehicleInventorySearchServiceMock.getVehicle(Mockito.anyInt())).thenThrow(new SQLException());
    	ResponseEntity<Vehicle> response= vehicleInventorySearchController.getVehicleById(1);
    	verify(vehicleInventorySearchServiceMock, times(1)).getVehicle(1);
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());    	
    }
  
    @Test
    public void testGetVehiclesByYearSuccess() throws Exception 
    {
    	Vehicle vehicle1 = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    	Vehicle vehicle2 = new VehicleBuilder().id(2).type(VehicleType.AIRPLANE).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	vehicles.add(vehicle1);
    	vehicles.add(vehicle2);
    	when(vehicleInventorySearchServiceMock.getVehiclesByYear(Mockito.anyInt())).thenReturn(vehicles);
    	ResponseEntity<List<Vehicle>> response= vehicleInventorySearchController.getVehiclesByYear(2009);
    	verify(vehicleInventorySearchServiceMock, times(1)).getVehiclesByYear(2009);
    	assertEquals(HttpStatus.OK,response.getStatusCode());
        List<Vehicle> responseBody=(List<Vehicle>) response.getBody();
        assertEquals(vehicles,responseBody);
    }
    
    @Test
    public void testGetVehiclesByYearEmptyResultSuccess() throws Exception 
    {
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	when(vehicleInventorySearchServiceMock.getVehiclesByYear(Mockito.anyInt())).thenReturn(vehicles);
    	ResponseEntity<List<Vehicle>> response= vehicleInventorySearchController.getVehiclesByYear(2009);
    	verify(vehicleInventorySearchServiceMock, times(1)).getVehiclesByYear(2009);
    	assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        List<Vehicle> responseBody=(List<Vehicle>) response.getBody();
        assertEquals(vehicles,responseBody);
    }
    
    @Test
    public void testGetVehiclesByYearFailure() throws Exception 
    {
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	when(vehicleInventorySearchServiceMock.getVehiclesByYear(Mockito.anyInt())).thenThrow(new SQLException());
    	ResponseEntity<List<Vehicle>> response= vehicleInventorySearchController.getVehiclesByYear(2009);
    	verify(vehicleInventorySearchServiceMock, times(1)).getVehiclesByYear(2009);
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());   
    }
    
    

}
