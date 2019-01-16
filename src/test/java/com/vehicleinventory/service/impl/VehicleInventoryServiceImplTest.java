package com.vehicleinventory.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

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
import com.vehicleinventory.dao.VehicleInventoryDao;
import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.model.VehicleType;
import com.vehicleinventory.service.VehicleInventoryService;
import com.vehicleinventory.utils.VehicleBuilder;
/**
 * @author RameshReddy Komma
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class VehicleInventoryServiceImplTest {

	@InjectMocks
    private VehicleInventoryService vehicleInventoryService=new VehicleInventoryServiceImpl();
    
    @Autowired
    private VehicleInventoryDao vehicleInventoryDaoMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws IllegalAccessException {
        
        Mockito.reset(vehicleInventoryDaoMock);
        MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        FieldUtils.writeField(vehicleInventoryService,"vehicleInventoryDao",vehicleInventoryDaoMock,true);
    }

    @Test
    public void testVehicleRepoCreationSuccess() throws Exception 
    {  
    	when(vehicleInventoryDaoMock.setVehicleInventoryRepo()).thenReturn(true);   		
    	vehicleInventoryService.setVehicleInventoryRepo();
    	verify(vehicleInventoryDaoMock, times(1)).setVehicleInventoryRepo();
    	   		verifyNoMoreInteractions(vehicleInventoryDaoMock);
    }
    
    @Test(expected=SQLException.class)
    public void testVehicleRepoCreationFailure() throws Exception {
    	when(vehicleInventoryDaoMock.setVehicleInventoryRepo()).thenThrow(new SQLException());
    	vehicleInventoryService.setVehicleInventoryRepo();
    	verify(vehicleInventoryDaoMock, times(1)).setVehicleInventoryRepo();
        verifyNoMoreInteractions(vehicleInventoryDaoMock);
    }
    
    
    @Test
    public void testUpdateVehicleSucess() throws Exception 
    {
    	Vehicle vehicle = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sed").year(2008).make("Nissan").model("altima").engine("2.5 s").build();
    	when(vehicleInventoryDaoMock.updateVehicle(any(Vehicle.class))).thenReturn(vehicle);	
    	Vehicle updatedVehicle=vehicleInventoryService.updateVehicle(vehicle);
    	verify(vehicleInventoryDaoMock, times(1)).updateVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryDaoMock);
       	assertEquals(vehicle,updatedVehicle);
    	    }
            
    @Test(expected=SQLException.class)
    public void testUpdateVehicleFailure() throws Exception 
    {
    	Vehicle vehicle = new Vehicle();
    	when(vehicleInventoryDaoMock.updateVehicle(any(Vehicle.class))).thenThrow(new SQLException("Vehicle Insertion Failed"));
    	vehicleInventoryService.updateVehicle(vehicle);
    	verify(vehicleInventoryDaoMock, times(1)).updateVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryDaoMock);
    }

    @Test
    public void testInsertVehicleSucess() throws Exception 
    {
    	Vehicle vehicle = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    	when(vehicleInventoryDaoMock.insertVehicle(any(Vehicle.class))).thenReturn(vehicle);	
    	vehicleInventoryService.insertVehicle(vehicle);
    	verify(vehicleInventoryDaoMock, times(1)).insertVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryDaoMock);
    	
    	    }
            
    @Test(expected=SQLException.class)
    public void testInsertVehicleFailure() throws Exception 
    {
    	Vehicle vehicle = new Vehicle();
    	when(vehicleInventoryDaoMock.insertVehicle(any(Vehicle.class))).thenThrow(new SQLException("Vehicle Insertion Failed"));
    	vehicleInventoryService.insertVehicle(vehicle);
    	verify(vehicleInventoryDaoMock, times(1)).insertVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryDaoMock);    	
    }
     
      @Test
    public void deleteVehicleByIdSuccess() throws Exception {
    	
    	when(vehicleInventoryDaoMock.deleteVehicle()).thenReturn(true);
    	vehicleInventoryService.deleteVehicle();
    	verify(vehicleInventoryDaoMock, times(1)).deleteVehicle();
        verifyNoMoreInteractions(vehicleInventoryDaoMock);
    }
    
    
    
    @Test(expected=SQLException.class)
    public void deleteVehicleByIdFailure() throws Exception {
    	when(vehicleInventoryDaoMock.deleteVehicle()).thenThrow(new SQLException());
    	vehicleInventoryService.deleteVehicle();
    	verify(vehicleInventoryDaoMock, times(1)).deleteVehicle();
        verifyNoMoreInteractions(vehicleInventoryDaoMock);
    }

   }
