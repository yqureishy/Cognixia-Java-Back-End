package com.cognixia.jump.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository repo;
	
	@Autowired
	ReviewRepository reviewRepo;
	
	@Autowired        
	UserRepository userRepository;

	private List<Review> topReviews;
	
	
	public Restaurant getRestaurantById(long id) throws ResourceNotFoundException{
		
		Optional<Restaurant> found = repo.findById(id); 
		
		if(found == null) {
			throw new ResourceNotFoundException("Restaurant not found"+ id);
		}
				      
		return found.get();
	}
	
	
	public List<Restaurant> getAllRestaurants(){
		return repo.findAll();
	}
	
	public List<Review> getReviewsOfRestaurant(long id) throws ResourceNotFoundException{
		
		Restaurant restaurant = getRestaurantById(id);
		
		return restaurant.getReviews();
		
	}
	

	public Restaurant addRestaurant(Restaurant restaurant) {
		
		restaurant.setId(-1L);
		
		Restaurant toAdd = repo.save(restaurant);
		
		return toAdd;	
	}
	
	
	public Restaurant deleteRestaurant(long id) throws ResourceNotFoundException {
		
		Restaurant toDelete = getRestaurantById(id);
		
		repo.deleteById(id);
		
		return toDelete;
	}
	

	
	
	public Restaurant updateRestaurant(long id, String field, String updated) throws ResourceNotFoundException {
		
		if(repo.existsById(id)) {
			
			Restaurant toUpdate = getRestaurantById(id);
			
			switch(field.toLowerCase()) {
				case "name": 
					toUpdate.setName(updated);
					break;
				
				case "address": 
					toUpdate.setAddress(updated);
					break;
					
				case "description":
					toUpdate.setDescription(updated);
					break;
					
				case "phoneNr":
					toUpdate.setPhoneNr(updated);
					
					
				default: // Restaurant field does not exist
					throw new ResourceNotFoundException("field of Restaurant");
			}			
			
			return repo.save(toUpdate);
		}
	
		throw new ResourceNotFoundException("Restaurant"+ id);
		
	}
	
	// Get First Restaurant Found
	public Restaurant getRestaurantByName(String name) throws ResourceNotFoundException{
		List<Restaurant> listOfRest = repo.findRestaurantsByName(name);
		
		if(listOfRest.isEmpty())
			throw new ResourceNotFoundException("Restaurant");	
		else 
			return listOfRest.get(0);
				
	}
	
	//Get all Restaurants By Name 
	public List<Restaurant> getRestaurantsByName(String name) throws ResourceNotFoundException {
		List<Restaurant> results = repo.findRestaurantsByName("%" + name + "%");
		
		if(results.isEmpty())
			throw new ResourceNotFoundException("Restaurant");	
		else 
			return results;
	}
	

	
	
	// Get Top 5 Reviews of Restaurant 
	public List<Review> getTopThreeReviews(long id){
		return reviewRepo.findTopThreeReviews(id);
	}
	
	
}