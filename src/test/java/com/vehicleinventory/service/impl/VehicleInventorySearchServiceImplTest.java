package com.vehicleinventory.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vehicleinventory.config.TestContext;
import com.vehicleinventory.dao.VehicleInventorySearchDao;
import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.model.VehicleType;
import com.vehicleinventory.service.VehicleInventorySearchService;
import com.vehicleinventory.utils.VehicleBuilder;
/**
 * @author RameshReddy Komma
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class VehicleInventorySearchServiceImplTest {

    @InjectMocks
    private VehicleInventorySearchService vehicleInventorySearchService=new VehicleInventorySearchServiceImpl();

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired 
    private VehicleInventorySearchDao vehicleInventorySearchDaoMock;
    
    
    @Before
    public void setUp() throws IllegalAccessException {
        
        Mockito.reset(vehicleInventorySearchDaoMock);
        FieldUtils.writeField(vehicleInventorySearchService,"vehicleInventorySearchDao",vehicleInventorySearchDaoMock,true);
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
    	when(vehicleInventorySearchDaoMock.getAllVehicles()).thenReturn(vehicles);
    	vehicleInventorySearchService.getAllVehicles();
    	verify(vehicleInventorySearchDaoMock, times(1)).getAllVehicles();
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock);
    }
    
    @Test
    public void testGetAllVehiclesEmptyResultSuccess() throws Exception 
    {
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	when(vehicleInventorySearchDaoMock.getAllVehicles()).thenReturn(vehicles);
    	vehicleInventorySearchService.getAllVehicles();
    	verify(vehicleInventorySearchDaoMock, times(1)).getAllVehicles();
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock);
    }
    
    @Test(expected=SQLException.class)
    public void testGetAllVehiclesFailure() throws Exception 
    {
    	when(vehicleInventorySearchDaoMock.getAllVehicles()).thenThrow(new SQLException());
    	vehicleInventorySearchService.getAllVehicles();
    	verify(vehicleInventorySearchDaoMock, times(1)).getAllVehicles();
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock);   	
    }
    
    @Test
    public void testGetVehicleByIdSuccess() throws Exception 
    {
    	Vehicle vehicle = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    	when(vehicleInventorySearchDaoMock.getVehicle(Mockito.anyInt())).thenReturn(vehicle);
    	vehicleInventorySearchService.getVehicle(1);
    	verify(vehicleInventorySearchDaoMock, times(1)).getVehicle(1);
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock);
    }
    
    @Test
    public void testGetVehicleByIdEmptyResultSuccess() throws Exception 
    {
    	Vehicle vehicle=new Vehicle();
    	when(vehicleInventorySearchDaoMock.getVehicle(Mockito.anyInt())).thenReturn(vehicle);
    	vehicleInventorySearchService.getVehicle(1);
    	verify(vehicleInventorySearchDaoMock, times(1)).getVehicle(1);
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock);
    }
    
    @Test(expected=SQLException.class)
    public void testGetVehicleByIdFailure() throws Exception 
    {
    	when(vehicleInventorySearchDaoMock.getVehicle(Mockito.anyInt())).thenThrow(new SQLException());
    	vehicleInventorySearchService.getVehicle(1);
    	verify(vehicleInventorySearchDaoMock, times(1)).getVehicle(1);
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock); 	
    }
  
    @Test
    public void testGetVehiclesByYearSuccess() throws Exception 
    {
    	Vehicle vehicle1 = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    	Vehicle vehicle2 = new VehicleBuilder().id(2).type(VehicleType.AIRPLANE).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	vehicles.add(vehicle1);
    	vehicles.add(vehicle2);
    	when(vehicleInventorySearchDaoMock.getVehiclesByYear(Mockito.anyInt())).thenReturn(vehicles);
    	vehicleInventorySearchService.getVehiclesByYear(2009);
    	verify(vehicleInventorySearchDaoMock, times(1)).getVehiclesByYear(2009);
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock);
    }
    
    @Test
    public void testGetVehiclesByYearEmptyResultSuccess() throws Exception 
    {
    	List<Vehicle> vehicles=new ArrayList<Vehicle>();
    	when(vehicleInventorySearchDaoMock.getVehiclesByYear(Mockito.anyInt())).thenReturn(vehicles);
    	vehicleInventorySearchService.getVehiclesByYear(2009);
    	verify(vehicleInventorySearchDaoMock, times(1)).getVehiclesByYear(2009);
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock);
    }
    
    @Test(expected=SQLException.class)
    public void testGetVehiclesByYearFailure() throws Exception 
    {
    	when(vehicleInventorySearchDaoMock.getVehiclesByYear(Mockito.anyInt())).thenThrow(new SQLException());
    	vehicleInventorySearchService.getVehiclesByYear(2009);
    	verify(vehicleInventorySearchDaoMock, times(1)).getVehiclesByYear(2009);
    	verifyNoMoreInteractions(vehicleInventorySearchDaoMock); 
    }
    
    

}
