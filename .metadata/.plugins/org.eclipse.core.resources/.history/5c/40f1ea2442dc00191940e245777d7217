package com.bridgelabz.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.bridgelabz.dto.UserDto;

public class TokenGeneration {
	private final long EXPIRATION_TIME = 9000;
	private final String secret = "Soumajit";

	public String generString(UserDto details) {
		String email = details.getEmail();
		return JWT.create().withClaim("email", email).sign(Algorithm.HMAC512(secret));
	}

	public String parseToken(String token) {
		System.out.println("i am here");
		return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim("email").asString();
	}
}