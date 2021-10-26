package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Restaurant implements Serializable{

	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	@Column(nullable = false)
	private String phoneNr;	
	
	@Column(nullable = false)
	private String description;
		
	@Column(nullable = false)
	private Double rating;
	
	
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	@JsonBackReference     ///new
	private List<Review> reviews;


	public Restaurant(Long id, String name, String address, String phoneNr, String description, Double rating,
			List<Review> reviews) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNr = phoneNr;
		this.description = description;
		this.rating = rating;
		this.reviews = reviews;
	}
	
	
	public Restaurant() {
		this(-1L, "N/A", "N/A", "N/A", "N/A", 0.0 ,new ArrayList<>());
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhoneNr() {
		return phoneNr;
	}


	public void setPhoneNr(String phoneNr) {
		this.phoneNr = phoneNr;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Double getRating() {
		return rating;
	}


	public void setRating(Double rating) {
		this.rating = rating;
	}


	public List<Review> getReviews() {
		return reviews;
	}


	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}


	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", phoneNr=" + phoneNr
				+ ", description=" + description + ", rating=" + rating + ", reviews=" + reviews + "]";
	}
	
	
	
	
	
	
	
	
}
