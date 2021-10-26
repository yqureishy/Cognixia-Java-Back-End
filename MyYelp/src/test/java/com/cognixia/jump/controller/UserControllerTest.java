//package com.cognixia.jump.controller;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.cognixia.jump.controller.UserController;
//import com.cognixia.jump.exception.ResourceNotFoundException;
//import com.cognixia.jump.model.User;
//import com.cognixia.jump.service.UserService;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(UserController.class)
//public class UserControllerTest {
//	
//private final String STARTING_URI = "http://localhost:8080/api/";
//	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@MockBean
//	private UserService service;
//	
//	@InjectMocks
//	private UserController controller;
//	
//	@Test
//	void testGetAllUsers() throws Exception {
//		
//		// Arrange or Assemble
//		String uri = STARTING_URI + "car";
//		
//		List<User> allUsers = Arrays.asList(
//					new User(1L, "Nissan", 100),
//					new Car(2L, "BMW", 200)
//				);
//		
//		// Mocking the service when it is called in our Controller methods
//		// not actually executing anything in the service
//		// but instead return the hard coded values above.
//		when(service.getAllCars()).thenReturn(allCars);
//		
//		// Act and Assert
//		mockMvc.perform(get(uri))
//					.andDo(print())
//					.andExpect(status().isOk())
//					.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//					//.andExpect(jsonPath("$[0]").value(allCars.get(0)) );
//					.andExpect(jsonPath("$.length()").value(allCars.size()) )
//					.andExpect(jsonPath("$[0].id").value(allCars.get(0).getId()))
//					.andExpect(jsonPath("$[0].type").value(allCars.get(0).getType()))
//					.andExpect(jsonPath("$[0].miles").value(allCars.get(0).getMiles()))
//					.andExpect(jsonPath("$[1].id").value(allCars.get(1).getId()))
//					.andExpect(jsonPath("$[1].type").value(allCars.get(1).getType()))
//					.andExpect(jsonPath("$[1].miles").value(allCars.get(1).getMiles()));
//		
//		// clean up and verification
//		verify(service, times(1)).getAllCars();
//		verifyNoMoreInteractions(service);
//	}
//	
//	@Test
//	void testGetCarById() throws Exception {
//		long id = 1;
//		String uri = STARTING_URI + "/car/{id}";
//		
//		Car car = new Car(id, "BMW", 1000);
//		
//		GsonBuilder builder = new GsonBuilder();
//		builder.setPrettyPrinting();
//		Gson gson = builder.create();
//		
//		String carJSON = gson.toJson(car);
//		
//		when(service.getCarById(id)).thenReturn(car);
//		
//		mockMvc.perform(get(uri, id))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(content().json(carJSON));
////				.andExpect(jsonPath("$.id").value(car.getId()))
////				.andExpect(jsonPath("$.type").value(car.getType()))
////				.andExpect(jsonPath("$.miles").value(car.getMiles()));
//		
//		// clean up and verification
//		verify(service, times(1)).getCarById(id);
//		verifyNoMoreInteractions(service);	
//		
//	}
//	
//	@Test
//	void testGetCarNotFoundException() throws Exception {
//		
//		long id = 1;
//		String uri = STARTING_URI + "car/{id}";
//		Exception exception = new ResourceNotFoundException("Car with id: " + id + " is not found.");
//				
//		when(service.getCarById(id)).thenThrow(exception);
//		
//		mockMvc.perform(get(uri, id))
//				.andDo(print())
//				.andExpect(status().isNotFound())
//				.andExpect(jsonPath("$.message").value(exception.getMessage()));
//					
//		
//		// clean up and verification
//		verify(service, times(1)).getCarById(id);
//		verifyNoMoreInteractions(service);	
//		
//	}
//	
//	// Write a test for get cars by type
//	
//	// test for get cars by max miles
//	@Test
//	void testGetCarByMaxMiles() throws Exception{
//		
//		int maxMiles = 200;
//		String uri = STARTING_URI + "car/miles/max/{miles}";
//		
//		List<Car> allCars = Arrays.asList(
//				new Car(1L, "Nissan", 100),
//				new Car(2L, "Silverado", 200)
//			);
//		
//		when(service.getCarsOfMaxMiles(maxMiles)).thenReturn(allCars);
//		
//		mockMvc.perform(get(uri, maxMiles))
//						.andDo(print())
//						.andExpect(status().isOk())
//						.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//						.andExpect( jsonPath("$.length()").value(allCars.size()) )
//						.andExpect(jsonPath("$[0].id").value(allCars.get(0).getId()))
//						.andExpect(jsonPath("$[0].type").value(allCars.get(0).getType()))
//						.andExpect(jsonPath("$[0].miles").value(allCars.get(0).getMiles()))
//						.andExpect(jsonPath("$[1].id").value(allCars.get(1).getId()))
//						.andExpect(jsonPath("$[1].type").value(allCars.get(1).getType()))
//						.andExpect(jsonPath("$[1].miles").value(allCars.get(1).getMiles()));
//		
//		verify(service, times(1)).getCarsOfMaxMiles(maxMiles);
//		verifyNoMoreInteractions(service);
//	}
//	
//	
//	@Test
//	void testCreateCar() throws Exception {
//		String uri = STARTING_URI + "add/car";
//		
//		Car car = new Car(1L, "Hyundai", 15000);
//		
//		//MediaType textPlainUtf8 = new MediaType(MediaType.TEXT_PLAIN, Charset.forName("UTF-8"));
//		
//		String carJson = car.toJson();
//		
//		when(service.addCar(Mockito.any(Car.class))).thenReturn(car);
//		
//		mockMvc.perform(post(uri).content(carJson).contentType(MediaType.APPLICATION_JSON))
//							.andDo(print())
//							.andExpect(status().isCreated())
//							.andExpect(content().contentType(MediaType.APPLICATION_JSON));
//		
//		
//		
////		mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
////				.content(asJSONString(car)))
//	}
//	
//
//}
