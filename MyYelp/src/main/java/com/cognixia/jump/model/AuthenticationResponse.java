package com.cognixia.jump.model;

public class AuthenticationResponse {
	
	private final String JWT;
	
	public AuthenticationResponse() {
		this.JWT = "";
	}

	public AuthenticationResponse(String jWT) {
		super();
		this.JWT = jWT;
	}

	public String getJWT() {
		return JWT;
	}

}
