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
	private final long EXPIRATION_TIME = 3000;
	private final String secret = "Soumajit";

	public String generString(UserDto details) {
		String email = details.getEmail();
		String secret = "1234567890";
		return JWT.create().withClaim("emailId", email).withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(Algorithm.HMAC256(secret));
	}

	public String parseToken(String token) {
		Verification verification = null;
		verification = JWT.require(Algorithm.HMAC256(secret));
		JWTVerifier jwtVerifier = verification.build();
		DecodedJWT decodeJWT = jwtVerifier.verify(token);
		Claim claim = decodeJWT.getClaim("emailId");
	
		String emailId=claim.asString();
		return emailId;
	}
}