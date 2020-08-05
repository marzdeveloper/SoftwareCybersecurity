package com.sc.webim.model.dao;

import java.util.List;

import org.hibernate.Session;

import com.sc.webim.model.entities.Role;

public interface RoleDao {
	Session getSession();
	
	public void setSession(Session session);

	Role create(String name);
	
	Role update(Role role);
	
	void delete(Role role);
	
	List<Role> findAll();
	
	Role findRoleByName(String name);
	
	Role findRoleById(long id);
}
