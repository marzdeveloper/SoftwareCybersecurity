package com.sc.webim.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sc.webim.model.dao.UserDetailsDao;
import com.sc.webim.model.entities.Role;
import com.sc.webim.model.entities.User;

@Service("userDetailsService")
public class UserDetailsServiceDefault implements UserDetailsService {

  @Autowired
  private UserDetailsDao userDetailsDao;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  User user = userDetailsDao.findUserByUsername(username.trim().toLowerCase());
	  UserBuilder builder = null;
	  if (user != null) {
		  //qui "mappiamo" uno User della nostra app in uno User di spring
	      builder = org.springframework.security.core.userdetails.User.withUsername(username);
	      builder.disabled(!user.isEnabled());
	      builder.password(user.getPassword());
	            
	      String [] roles = new String[user.getRoles().size()];

	      int j = 0;
	      for (Role r : user.getRoles()) {
	    	  roles[j++] = r.getName();
	      }
	            
	      builder.roles(roles);
	  } else {
		  throw new UsernameNotFoundException("User not found.");
	  }
	  return builder.build();
  }
  
}
