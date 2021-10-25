package com.cognixia.jump.controller;



import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.service.RestaurantService;


@RestController
@RequestMapping("/api")
public class RestaurantController {
	
	@Autowired
	RestaurantService service;
	
	
	@GetMapping("/restaurant/{id}")
	public Restaurant getRestaurantById(@PathVariable long id) throws ResourceNotFoundException{
		return service.getRestaurantById(id);
	}
	
	
	@GetMapping("/restaurants")
	public List<Restaurant> getAllRestaurants(){
		return service.getAllRestaurants();
	}
	
	
	@GetMapping("/{id}/reviews")
	public List<Review> getReviewsOfRestaurant(@PathVariable long id) throws ResourceNotFoundException{
		return service.getReviewsOfRestaurant(id); 
	}
	

	@PostMapping("/restaurant/add")
	public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant){
        return ResponseEntity.status(201).body(service.addRestaurant(restaurant));
    }
	
	
	@DeleteMapping("/restaurant/delete/{id}")
	public Restaurant deleteRestaurant(@PathVariable long id) throws ResourceNotFoundException{
		Restaurant deleted = service.deleteRestaurant(id); 
		
		return deleted;
	}
	

 	

	@PatchMapping("/restaurant/update/{id}/{field}")
	public Restaurant updateRestaurant(@PathVariable long id, @PathVariable String field, @PathParam(value="update") String update) throws ResourceNotFoundException{
		Restaurant updated = service.updateRestaurant(id, field, update);
		
		return updated;
	}
	

	@GetMapping("/restaurant/name/{name}")
	public ResponseEntity<?> getRestaurantByName(@PathVariable String name) throws ResourceNotFoundException{
		Restaurant searched = service.getRestaurantByName(name); 
		
		return new ResponseEntity<>(searched, HttpStatus.OK);
	}
	

	@GetMapping("/restaurants/name/{name}")
	public List<Restaurant> getRestaurantsByName(@PathVariable String name) throws ResourceNotFoundException{
		return service.getRestaurantsByName(name); 
	}
	
	

	
	

	@GetMapping("/{id}/top5reviews")
	public List<Review> getTopThreeReviews(@PathVariable long id){
		return service.getTopThreeReviews(id); 
	}
	
}