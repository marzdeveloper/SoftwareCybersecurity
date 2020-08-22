package com.sc.webim.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;

public interface ImageDao {

	public Session getSession();
	
	public void setSession(Session session);
	
	List<Image> findAll();
	
	Image findById(int id);
	
	Image findByName(String name);
	
	Image create(String user_id, Date data_caricamento, String image_hash, String name, String gps);
	
	Image update(Image image);
	
	void delete(Image image);
	
	List<Image> findAllByMeasureId(int measure);
	
}