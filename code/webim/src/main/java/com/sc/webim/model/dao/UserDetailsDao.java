package com.sc.webim.model.dao;

import java.util.List;

import org.hibernate.Session;

import com.sc.webim.model.entities.Role;
import com.sc.webim.model.entities.User;


public interface UserDetailsDao {
	Session getSession();
	
	public void setSession(Session session);
	
	User findUserByUsername(String username);
	
	User create(String username, String password, boolean isEnabled, Role role);
	
	User update(User user);
	
	boolean delete(User user);

	public String encryptPassword(String password);
	
	List<User> findAll();
}
