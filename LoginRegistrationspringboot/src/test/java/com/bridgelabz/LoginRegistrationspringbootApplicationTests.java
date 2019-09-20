package com.bridgelabz;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.bridgelabz.dao.UserDaoImpl;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.UserDetailsForRegistration;
import com.bridgelabz.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginRegistrationspringbootApplicationTests {

	@Autowired
	private UserService userService;
	@MockBean
	private UserDaoImpl userDaoImpl;

	@Test
	public void retriveUserFromDatabaseTest() {
	when(userService.retriveUserFromDatabase()).thenReturn(Stream.of(new UserDto("souma","roy","7003717208","male","soumajit131292@gmail.com","1234567890")).collect(Collectors.toList()));	
    assertEquals(1, userService.retriveUserFromDatabase().size());
	}

	/*public void updateUser()
	{
		String id="1";
		String userDetails=
		when(userService.updateUser(id, userDetails))
	}
	@Test
	public void saveToDatabaseTest() {
	UserDto userDetails=new UserDto("souma","roy","7003717208","male","soumajit131292@gmail.com","1234567890");	
	when(userService.saveToDatabase(userDetails)).thenReturn(1);
	//System.out.println(userService.retriveUserFromDatabase());
	assertEquals(1,userService.retriveUserFromDatabase().size());
	}*/
	/*public void deleteUserTest()
	{
		
	}*/
}