package com.bridgelabz.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.bridgelabz.dao.UserDaoImpl;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.UserDetailsForRegistration;
import com.bridgelabz.util.TokenGeneration;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userdaoimpl;
	@Autowired
	private BCrypt bcryptEncoder;
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private TokenGeneration token;

	public UserDetailsForRegistration dtoToEntity(UserDto details) {
		UserDetailsForRegistration userDetails = modelmapper.map(details, UserDetailsForRegistration.class);
		return userDetails;
	}

	public UserDto entityToDto(UserDetailsForRegistration details) {
		UserDto userDetails = (modelmapper.map(details, UserDto.class));
		System.out.println("hello in modelmapper");
		return userDetails;
	}

	private String hashPassword(String plainTextPassword) {
		String salt = bcryptEncoder.gensalt();
		return bcryptEncoder.hashpw(plainTextPassword, salt);
	}

	@Override
	public void sendEmail(UserDto details) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo("soumajit131292@gmail.com");
		helper.setSubject("hello");
		helper.setText("http://localhost:8080/verify/" + token.generString(details));
		emailSender.send(message);
	}

	@Override
	public boolean verifyUser(String fromGeneratedToken) {
		String emailId = token.parseToken(fromGeneratedToken);
		if (userdaoimpl.isValidUser(emailId)) {
			userdaoimpl.changeStatus(emailId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<UserDto> retriveUserFromDatabase() {
		List<UserDto> users = new ArrayList<UserDto>();
		List<UserDetailsForRegistration> details = userdaoimpl.retriveUserDetails();
		for (UserDetailsForRegistration obj : details) {
			UserDto abc = entityToDto(obj);
			System.out.println(abc);
			users.add(abc);
		}
		System.out.println(details);
		return users;
	}

	@Override
	public void deleteFromDatabase(Integer id) {
		userdaoimpl.deletFromDatabase(id);
	}

	public void changeActiveStatus(String emailId) {
		userdaoimpl.changeStatus(emailId);
	}

	@Override
	public int saveToDatabase(UserDto userDetails) throws MessagingException {
		String password = userDetails.getPassword();
		userDetails.setPassword(hashPassword(password));
		userdaoimpl.setToDatabase(dtoToEntity(userDetails));
		sendEmail(userDetails);
		return 1;
		// return userDetails;
	}

	@Override
	public int updateUser(Integer id, UserDto userDetails) {
		return userdaoimpl.updateMobileNumberToDatabase(id, dtoToEntity(userDetails));
	}
}