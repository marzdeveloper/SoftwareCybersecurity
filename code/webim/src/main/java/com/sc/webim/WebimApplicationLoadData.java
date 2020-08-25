package com.sc.webim;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.model.dao.MeasureDao;
import com.sc.webim.model.dao.RoleDao;
import com.sc.webim.model.dao.UserDetailsDao;
import com.sc.webim.model.entities.Role;
import com.sc.webim.model.entities.User;

@SpringBootApplication(exclude = SpringApplicationAdminJmxAutoConfiguration.class)
public class WebimApplicationLoadData {
	
	public static void main( String[] args ) {
		SpringApplication.run(WebimApplicationLoadData.class, args);
    	
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DataServiceConfigWeb.class)) {

			SessionFactory sf = ctx.getBean("sessionFactory", SessionFactory.class);
			ImageDao imageDao = ctx.getBean(ImageDao.class);
			MeasureDao measureDao = ctx.getBean(MeasureDao.class);
			RoleDao roleDao = ctx.getBean(RoleDao.class);
			UserDetailsDao userDao = ctx.getBean(UserDetailsDao.class);
			
			//N.B. RICORDARSI DI ABILITARE LE ASSERTIONS DA ECLIPSE
			
			try (Session session = sf.openSession()) {
				
				imageDao.setSession(session);
				measureDao.setSession(session);
				roleDao.setSession(session);
				userDao.setSession(session);
				
				session.beginTransaction();
				
				//creo dei roles
				Role r1 = roleDao.create("DRONE");
				Role r2 = roleDao.create("DIRETTORE");
				
				session.getTransaction().commit();
				
				session.beginTransaction();
				
				//creo degli users
				User u1 = userDao.create("drone", "password", true, r1);				
				User u2 = userDao.create("direttore", "password", true, r2);
				
				userDao.update(u1);
				userDao.update(u2);
				session.getTransaction().commit();
				
				//verifico gli users e i ruoli creati
				session.beginTransaction();
				
				List<Role> allRoles = roleDao.findAll();
				
				List<User> allUsers = userDao.findAll();
				
				User direttore = userDao.findUserByUsername("direttore");
				
				User drone = userDao.findUserByUsername("drone");
				
				Role DIRETTORE = roleDao.findRoleByName("DIRETTORE");
				
				Role DRONE = roleDao.findRoleByName("DRONE");
				
				assert direttore.getRoles().size() == 1;
				
				assert direttore.getRoles().contains(DIRETTORE);
				
				assert drone.getRoles().size() == 1;
				
				assert drone.getRoles().contains(DRONE);
				
				System.out.println();
				
				System.out.println("Ruoli: " + allRoles.size());
				for (Role r : allRoles) {
					System.out.println("Name: " + r.getName());
				}
				
				System.out.println();
				
				System.out.println("Utenti: " + allUsers.size());
				for (User u : allUsers) {
					System.out.println("Username: " + u.getUsername());
				}
				
				System.out.println();
				
				System.out.println("La password per entrambi gli utenti è: password");
				
				System.out.println();
				
				session.getTransaction().commit();
				
			}
		}
		 catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
	}

}
