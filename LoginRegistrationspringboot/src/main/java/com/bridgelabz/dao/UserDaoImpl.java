package com.bridgelabz.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
		Session currentSession = entityManager.unwrap(Session.class);
		List<UserDetailsForRegistration> result = currentSession
				.createQuery("from UserDetailsForRegistration where email='" + emailId + "'").getResultList();
		System.out.println(result);
		System.out.println(result.size());
		return (result.size() > 0) ? true : false;
	}

	@Transactional
	public boolean checkActiveStatus(String emailId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String activeStatus = "true";
		List<UserDetailsForRegistration> result = currentSession
				.createQuery("from UserDetailsForRegistration where activeStatus='" + activeStatus + "'")
				.getResultList();
		System.out.println(result);
		System.out.println(result.size());
		// return (result.size() > 0) ? true : false;
		if (result.size() > 0)
			return true;
		return false;
	}

	@Transactional
	public boolean deletFromDatabase(Integer userid) {
		Session currentSession = entityManager.unwrap(Session.class);
		int status = 0;
		String hql = "from UserDetailsForRegistration where id=:id";
		Query query = currentSession.createQuery(hql);
		query.setParameter("id", userid);
		List<UserDetailsForRegistration> list = query.getResultList();
		if (list.size() > 0)
			status = currentSession.createQuery("delete from UserDetailsForRegistration where id='" + userid + "'")
					.executeUpdate();
		return (status > 0) ? true : false;

	}

	@Transactional
	public void changeStatus(String emailId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String status = "true";
		currentSession.createQuery(
				"update UserDetailsForRegistration set activeStatus='" + status + "' where email='" + emailId + "'")
				.executeUpdate();
	}

	@Transactional
	public int setToDatabase(UserDetailsForRegistration userDetails) {
		Session currentSession = entityManager.unwrap(Session.class);
		if (!isValidUser(userDetails.getEmail())) {
			currentSession.save(userDetails);
			return 1;
		}
		return 0;
	}

	@Transactional
	public int updatePassword(String emailId, UserDetailsForRegistration userDetails) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.createQuery("update UserDetailsForRegistration set password='" + userDetails.getPassword()
				+ "' where email='" + emailId + "'").executeUpdate();
	}

	@Transactional
	public List<UserDetailsForRegistration> checkUser(String email) {
		String activeStatus = "true";
		List<UserDetailsForRegistration> result = new ArrayList<>();
		Session currentSession = entityManager.unwrap(Session.class);
		if (isValidUser(email)) {
			result = currentSession
					.createQuery("from UserDetailsForRegistration where activeStatus='" + activeStatus + "'and email='"+email+"'")
					.getResultList();
		}
		return result;
	}
}