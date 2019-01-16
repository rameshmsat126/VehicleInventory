package com.vehicleinventory.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.vehicleinventory.config.TestContext;
import com.vehicleinventory.model.Vehicle;
import com.vehicleinventory.model.VehicleType;
import com.vehicleinventory.service.VehicleInventoryService;
import com.vehicleinventory.utils.TestUtil;
import com.vehicleinventory.utils.VehicleBuilder;

import java.sql.SQLException;
import java.util.Arrays;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class VehicleInventoryControllerTest {

    @Autowired
    private VehicleInventoryService vehicleInventoryServiceMock;
    
    @Autowired
    private VehicleInventoryController vehicleInventoryController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        
        Mockito.reset(vehicleInventoryServiceMock);
        MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testVehicleRepoCreationSuccess() throws Exception 
    {
    	        ResponseEntity<String> response=vehicleInventoryController.setVehicleInventoryRepo();
    	        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    	    	assertEquals("Inventory Database created successfully",response.getBody());                
    	   		verify(vehicleInventoryServiceMock, times(1)).setVehicleInventoryRepo();
    	   		verifyNoMoreInteractions(vehicleInventoryServiceMock);
    }
    
    @Test
    public void testVehicleRepoCreationFailure() throws Exception {
    	when(vehicleInventoryServiceMock.setVehicleInventoryRepo()).thenThrow(new SQLException());
    	ResponseEntity<String> response=vehicleInventoryController.setVehicleInventoryRepo();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    	assertEquals("Vehicle DB creation Failed",response.getBody()); 
        verify(vehicleInventoryServiceMock, times(1)).setVehicleInventoryRepo();
        verifyNoMoreInteractions(vehicleInventoryServiceMock);
    }
    
    @Test
    public void testVehicleInfoMessage() throws Exception 
    {
    	 ResponseEntity<String> response=vehicleInventoryController.vehicleInfoMessage();
	        assertEquals(HttpStatus.OK,response.getStatusCode());
	    	assertEquals("Welcome to Vehicle Inventory.",response.getBody());   
    	   	
    }
    @Test
    public void testUpdateVehicleSucess() throws Exception 
    {
    	Vehicle vehicle = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sed").year(2008).make("Nissan").model("altima").engine("2.5 s").build();
    	when(vehicleInventoryServiceMock.updateVehicle(any(Vehicle.class))).thenReturn(vehicle);	
    	ResponseEntity<?> response=vehicleInventoryController.updateVehicle(vehicle);
    	verify(vehicleInventoryServiceMock, times(1)).updateVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryServiceMock);
    	assertEquals(HttpStatus.OK,response.getStatusCode());
    	assertNotNull(response.getBody());
    	assertTrue(response.getBody() instanceof Vehicle);
    	Vehicle updatedVehicle=(Vehicle) response.getBody();
    	assertEquals(vehicle,updatedVehicle);
    	    }
            
    @Test
    public void testUpdateVehicleFailure() throws Exception 
    {
    	Vehicle vehicle = new Vehicle();
    	when(vehicleInventoryServiceMock.updateVehicle(any(Vehicle.class))).thenThrow(new SQLException("Vehicle Insertion Failed"));
    	ResponseEntity<?> response=vehicleInventoryController.updateVehicle(vehicle);
    	verify(vehicleInventoryServiceMock, times(1)).updateVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryServiceMock);
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    	assertNotNull(response.getBody());
    	assertFalse(response.getBody() instanceof Vehicle);
    	String responseMessage=(String) response.getBody();
    	assertEquals("Vehicle Updation Failed",responseMessage);
    		
    }

    @Test
    public void testInsertVehicleSucess() throws Exception 
    {
    	Vehicle vehicle = new VehicleBuilder().id(1).type(VehicleType.CAR).name("Sedan").year(2019).make("Nissan").model("altima").engine("2.5 s").build();
    	when(vehicleInventoryServiceMock.insertVehicle(any(Vehicle.class))).thenReturn(vehicle);	
    	ResponseEntity<?> response=vehicleInventoryController.insertVehicle(VehicleType.CAR, vehicle);
    	verify(vehicleInventoryServiceMock, times(1)).insertVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryServiceMock);
    	assertEquals(HttpStatus.CREATED,response.getStatusCode());
    	assertNotNull(response.getBody());
    	assertTrue(response.getBody() instanceof Vehicle);
    	Vehicle insertedVehicle=(Vehicle) response.getBody();
    	assertEquals(vehicle,insertedVehicle);
    	    }
            
    @Test
    public void testInsertVehicleFailure() throws Exception 
    {
    	Vehicle vehicle = new Vehicle();
    	when(vehicleInventoryServiceMock.insertVehicle(any(Vehicle.class))).thenThrow(new SQLException("Vehicle Insertion Failed"));
    	ResponseEntity<?> response=vehicleInventoryController.insertVehicle(VehicleType.CAR, vehicle);
    	verify(vehicleInventoryServiceMock, times(1)).insertVehicle(vehicle);
    	verifyNoMoreInteractions(vehicleInventoryServiceMock);
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    	assertNotNull(response.getBody());
    	assertFalse(response.getBody() instanceof Vehicle);
    	String responseMessage=(String) response.getBody();
    	assertEquals("Vehicle Insertion Failed",responseMessage);
    		
    }
     
      @Test
    public void deleteVehicleByIdSuccess() throws Exception {
    	when(vehicleInventoryServiceMock.deleteVehicle()).thenReturn(true);
        ResponseEntity<String> response=vehicleInventoryController.deleteVehicle();       
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Vehicle Deleted Successfully",response.getBody());
        verify(vehicleInventoryServiceMock, times(1)).deleteVehicle();
        verifyNoMoreInteractions(vehicleInventoryServiceMock);
    }
    
    
    
    @Test
    public void deleteVehicleByIdFailure() throws Exception {
    	when(vehicleInventoryServiceMock.deleteVehicle()).thenThrow(new SQLException());
    	ResponseEntity<String> response=vehicleInventoryController.deleteVehicle();       
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        assertEquals("Vehicle Deletion Failed",response.getBody());
        verify(vehicleInventoryServiceMock, times(1)).deleteVehicle();
        verifyNoMoreInteractions(vehicleInventoryServiceMock);
    }

   }
