package com.bridgelabz.service;

import java.util.List;

import javax.mail.MessagingException;

import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.UserDetailsForRegistration;

public interface UserService {

	public List<UserDto> retriveUserFromDatabase();

	public void deleteFromDatabase(Integer id);

	public int saveToDatabase(UserDto userDetails) throws MessagingException;

	public int updateUser(Integer id, UserDto userDetails);

	public void sendEmail(UserDto userDetails) throws MessagingException;

	public boolean verifyUser(String token);



}