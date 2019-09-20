package com.bridgelabz.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.dto.UserDto;
import com.bridgelabz.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRegistrationController {

	Logger logger=Logger.getLogger(this.getClass());
	@Autowired
	private UserService userService;

	@GetMapping("/get")
	public List<UserDto> getDetails() {
		return userService.retriveUserFromDatabase();
	}

	@PostMapping("/users")
	public void saveToDatabase(@RequestBody UserDto userDetails) throws MessagingException {
		userService.saveToDatabase(userDetails);
	}

	@PostMapping("/verify/{token}")
	public void verifyUserByMail(@PathVariable("token") String token) {
		if (userService.verifyUser(token)) {
			//userService.changeActiveStatus();
			logger.info("success");
		} else {
		}
	}

	@DeleteMapping("/deleteuser/{id}")
	public void deleteUserById(@PathVariable("id") Integer id) {
		userService.deleteFromDatabase(id);
	}

	@PutMapping("/updateuser/{id}")
	public void updateUser(@PathVariable("id") Integer id, @RequestBody UserDto userDetails) {
		userService.updateUser(id, userDetails);
	}
}