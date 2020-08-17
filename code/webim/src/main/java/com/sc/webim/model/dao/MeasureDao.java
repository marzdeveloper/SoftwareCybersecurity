package com.sc.webim.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;

public interface MeasureDao {
	
	public Session getSession();
	
	public void setSession(Session session);
	
	List<Measure> findAll();
	
	Measure findById(int id);

	Measure create(String user_id, Date data_caricamento, String measure_hash, String name);
	
	Measure update(Measure measure);
	
	void delete(Measure measure);
	
	Set<Image> getImages(Measure measure);

}