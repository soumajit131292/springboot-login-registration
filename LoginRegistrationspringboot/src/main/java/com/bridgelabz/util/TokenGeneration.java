package com.bridgelabz.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenGeneration {
	private final long EXPIRATION_TIME = 9000;
	private final String secret = "Soumajit";

	public String generateToken(String emailId) {
		return JWT.create().withClaim("email", emailId).sign(Algorithm.HMAC512(secret));
	}

	public String parseToken(String token) {
		System.out.println("i am here");
		return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim("email").asString();
	}
}