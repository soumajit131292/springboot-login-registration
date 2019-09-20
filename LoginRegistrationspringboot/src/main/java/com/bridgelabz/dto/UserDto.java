package com.bridgelabz.dto;

import lombok.Data;

@Data
public class UserDto {
public UserDto()
{}
		private String firstName;
	private String lastName;
	private String mobileNumber;
	private String gender;
	private String email;
	private String password;
	public UserDto(String firstName, String lastName, String mobileNUmber, String gender, String email,
			String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNUmber;
		this.gender = gender;
		this.email = email;
		this.password = password;
	}

}
