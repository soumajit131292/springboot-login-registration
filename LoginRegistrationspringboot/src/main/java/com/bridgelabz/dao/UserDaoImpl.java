package com.bridgelabz.dao;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.dto.UserDto;
import com.bridgelabz.model.UserDetailsForRegistration;

@Repository
public class UserDaoImpl implements UserDao {
	private Logger logger = Logger.getLogger(this.getClass());
	private EntityManager entityManager;

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	private BCrypt bcryptEncoder;

	@Transactional
	public List<UserDetailsForRegistration> retriveUserDetails() {
		Session currentSession = entityManager.unwrap(Session.class);
		List<UserDetailsForRegistration> users = currentSession.createQuery("from UserDetailsForRegistration")
				.getResultList();
		return users;
	}

	
	  @Transactional 
	  public boolean isValidUser(String emailId) {
		  Session
	  currentSession = entityManager.unwrap(Session.class);
		  int result=currentSession.createQuery("from UserDetailsForRegistration where email='"+ emailId+"'").executeUpdate(); 
		   return (result>0)?true:false;
	  }
	 
	@Transactional
	public void deletFromDatabase(Integer userid) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.createQuery("delete from UserDetailsForRegistration where id='" + userid + "'").executeUpdate();
	}
	
	@Transactional
	public void changeStatus(String emailId)
	{
		Session currentSession=entityManager.unwrap(Session.class);
		String status="true";
		currentSession.createQuery("update UserDetailsForRegistration set activeStatus='"+status+"'where email='"+emailId+"'").executeUpdate();
	}

	@Transactional
	public List<UserDetailsForRegistration> checkPassword(Integer userid) {
		Session currentSession = entityManager.unwrap(Session.class);
		List<UserDetailsForRegistration> users = currentSession
				.createQuery("from UserDetailsForRegistration where id='" + userid + "'").getResultList();
		for (UserDetailsForRegistration details : users) {
			String password = details.getPassword();
			if (bcryptEncoder.checkpw("1234567", password)) {
				logger.info("true");
			}
		}
		return users;
	}

	@Transactional
	public int setToDatabase(UserDetailsForRegistration userDetails) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(userDetails);
		return 1;
	}

	@Transactional
	public int updateMobileNumberToDatabase(Integer id, UserDetailsForRegistration userDetails) {
		Session currentSession = entityManager.unwrap(Session.class);
		// System.out.println(id+"in userdao");
		logger.info("in userDaos");
		return currentSession.createQuery("update UserDetailsForRegistration set mobileNumber='"
				+ userDetails.getMobileNumber() + "'where id='" + id + "'").executeUpdate();
	}
}