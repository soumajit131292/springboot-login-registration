package com.bridgelabz.service;

import java.util.List;

import javax.mail.MessagingException;

import com.bridgelabz.dto.ResetPassword;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.LoginUser;
import com.bridgelabz.model.UserDetailsForRegistration;

public interface UserService {

	public List<UserDto> retriveUserFromDatabase();

	public boolean deleteFromDatabase(Integer id);

	public int saveToDatabase(UserDto userDetails) throws MessagingException;

	public boolean verifyUser(String token);

	boolean doLogin(LoginUser loginUser);

	boolean isUserPresent(String email);

	boolean forgotPassword(String emailId) throws MessagingException;

	void sendEmail(String url, String generatedToken) throws MessagingException;

	int updateUser(String token, ResetPassword passwordReset);

}