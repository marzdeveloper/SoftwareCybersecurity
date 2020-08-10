package com.sc.webim.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sc.webim.DataServiceConfigWeb;
import com.sc.webim.Utils;
import com.sc.webim.model.dao.*;
import com.sc.webim.model.entities.*;

public class LoadData {
	
	public static void main( String[] args ) {
    	
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
								
//				session.refresh(sender1);
//				session.refresh(sender2);
//				session.refresh(sender3);
//
//				assert sender1.getDevices().size()==0;
//				assert sender2.getDevices().size()==2;
//				assert sender3.getDevices().size()==1;
//				assert sender4.getDevices().size()==0;
//							
//				assert device4.getPositionings().contains(positioning4);
//				assert location3.getPositionings().contains(positioning4);
//				assert positioning4.getLocation().equals(location3);
//
//				List<Device> allDevices = deviceDao.findAll();
//				
//				System.out.println("Number of devices: " + allDevices.size());
//				for (Device d : allDevices) {
//					System.out.println(" - " + d.getBrand() + " : " + d.getCheckIn() + " : " + d.getCheckOut() + " : " + d.getDocument()+ 
//							" : " + d.getModel() + " : " + d.getReason()+ " : " + d.getSerialNumber()+ " : " + d.getSender());
//					
//					Set<Job> jobs = deviceDao.getJobs(d);
//					System.out.println("Number of job: " + jobs.size());
//					for (Job j : jobs) {
//						System.out.println("  - " + j.getDescription());					
//					}
//				}
//				
//				List<Employee> allEmployees = employeeDao.findAll();
//				System.out.println("Number of employees: " + allEmployees.size());
//				for (Employee em : allEmployees) {
//					System.out.println(" - " + em.getName()+ " : " + em.getSurname() + " : "+ em.getPositionings() + " : "+ em.getWorkstations());
//					Set<Team> teams = em.getTeams();
//					
//					if (teams == null) {
//						teams= new HashSet<Team>();
//					}
//					
//					System.out.println("Number of teams: " + teams.size());
//					for (Team tm : teams) {
//						System.out.println("  - " + tm.getDescription() + " - " + tm.getName() + " - " + tm.getType());
//					}
//				}
				
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
				
				session.beginTransaction();
				
				//creo delle immagini
				Image image = imageDao.create("drone", Utils.date("2020-08-31"), "1234",null);
				
				imageDao.update(image);
				session.getTransaction().commit();
				
				session.beginTransaction();
				
				//creo delle misure
				Measure measure = measureDao.create("drone", Utils.date("2020-08-31"), "5678", "gps", "name");
				
				measureDao.update(measure);
				
				//assegno la misura ad image
				image.setMeasure_id(measure);
				
				imageDao.update(image);
				session.getTransaction().commit();
				
			}
		}
		 catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
	}

}