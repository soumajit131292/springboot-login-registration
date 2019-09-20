package com.bridgelabz.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.bridgelabz.util.TokenGeneration;

@Configuration
public class ConfigurationClaass {
	@Bean
	public ModelMapper setModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	@Bean
	public BCrypt encoder() {
		return new BCrypt();
	}

	@Bean
	public TokenGeneration token() {
		return new TokenGeneration();
	}

}