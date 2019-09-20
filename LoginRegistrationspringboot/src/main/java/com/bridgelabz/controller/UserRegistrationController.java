package com.bridgelabz.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.dto.UserDto;
import com.bridgelabz.exception.ErrorResponse;
import com.bridgelabz.model.LoginUser;
import com.bridgelabz.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@GetMapping("/get")
	public List<UserDto> getDetails() {
		return userService.retriveUserFromDatabase();
	}

	@PostMapping("/users")
	public ResponseEntity<ErrorResponse> saveToDatabase(@RequestBody UserDto userDetails) throws MessagingException {
		if (userService.saveToDatabase(userDetails) > 0)
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);
		else
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "not verified"),
					HttpStatus.BAD_REQUEST);

	}

	@GetMapping("/verify/{token}")
	public ResponseEntity<ErrorResponse> verifyUserByMail(@PathVariable("token") String token) {
		System.out.println("hello in verify");
		if (userService.verifyUser(token)) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "not verified"),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PostMapping("/login")
	public ResponseEntity<ErrorResponse> login(@RequestBody LoginUser loginUser) {
		System.out.println(loginUser.getEmail());
		System.out.println(loginUser.getPassword());
		if (userService.doLogin(loginUser)) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "not logged in "),
					HttpStatus.BAD_REQUEST);

		}

	}

	@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<ErrorResponse> deleteUserById(@PathVariable("id") Integer id) {
		if (userService.deleteFromDatabase(id)) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "not found"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/updateuser/{id}")
	public ResponseEntity<ErrorResponse> updateUser(@PathVariable("id") Integer id, @RequestBody UserDto userDetails) {
		userService.updateUser(id, userDetails);
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.OK.value(), "success"), HttpStatus.OK);

	}
}