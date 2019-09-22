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
import com.bridgelabz.dto.ResetPassword;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.LoginUser;
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
		return modelmapper.map(details, UserDetailsForRegistration.class);
	}

	public UserDetailsForRegistration dtoToEntityForPasswordResey(ResetPassword details) {
		return modelmapper.map(details, UserDetailsForRegistration.class);
	}

	public UserDto entityToDto(UserDetailsForRegistration details) {
		return (modelmapper.map(details, UserDto.class));
	}

	private String hashPassword(String plainTextPassword) {
		String salt = bcryptEncoder.gensalt();
		return bcryptEncoder.hashpw(plainTextPassword, salt);
	}

	@Override
	public void sendEmail(String url, String generatedToken) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo("soumajit131292@gmail.com");
		helper.setSubject("hello");
		helper.setText(url + generatedToken);
		emailSender.send(message);
	}

	@Override
	public boolean doLogin(LoginUser loginUser) {
		String email = loginUser.getEmail();
		List<UserDetailsForRegistration> result = userdaoimpl.checkUser(loginUser.getEmail());
		return (bcryptEncoder.checkpw(loginUser.getPassword(), result.get(0).getPassword())) ? true : false;
	}

	@Override
	public boolean isUserPresent(String email) {
		return userdaoimpl.isValidUser(email);
	}

	@Override
	public boolean forgotPassword(String emailId) throws MessagingException {
		String generatedToken = token.generateToken(emailId);
		String url = "";
		sendEmail(url, generatedToken);
		return true;
	}

	@Override
	public boolean verifyUser(String fromGeneratedToken) {
		String emailId = token.parseToken(fromGeneratedToken);
		if (userdaoimpl.isValidUser(emailId)) {
			userdaoimpl.changeStatus(emailId);
			return true;
		}
		return false;
	}

	@Override
	public List<UserDto> retriveUserFromDatabase() {
		List<UserDto> users = new ArrayList<UserDto>();
		List<UserDetailsForRegistration> details = userdaoimpl.retriveUserDetails();
		for (UserDetailsForRegistration obj : details) {
			UserDto abc = entityToDto(obj);
			users.add(abc);
		}
		return users;
	}

	@Override
	public boolean deleteFromDatabase(Integer id) {
		return (userdaoimpl.deletFromDatabase(id)) ? true : false;
	}

	public void changeActiveStatus(String emailId) {
		userdaoimpl.changeStatus(emailId);
	}

	@Override
	public int saveToDatabase(UserDto userDetails) throws MessagingException {
		String password = userDetails.getPassword();
		String generatedToken = token.generateToken(userDetails.getEmail());
		String url = "http://localhost:8080/api/verify/";
		userDetails.setPassword(hashPassword(password));
		if (userdaoimpl.setToDatabase(dtoToEntity(userDetails)) > 0) {
			sendEmail(url, generatedToken);
			return 1;
		}
		return 0;
	}

	@Override
	public int updateUser(String generatedtoken, ResetPassword passwordReset) {
		String emailId = token.parseToken(generatedtoken);
		String encodePassword = hashPassword(passwordReset.getPassword());
		passwordReset.setPassword(encodePassword);
		return userdaoimpl.updatePassword(emailId, dtoToEntityForPasswordResey(passwordReset));
	}
}