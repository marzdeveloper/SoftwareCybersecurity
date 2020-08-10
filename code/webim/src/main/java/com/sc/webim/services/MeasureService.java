package com.sc.webim.services;

import java.util.Date;
import java.util.List;

import com.sc.webim.model.entities.Measure;

public interface MeasureService {
	
	List<Measure> findAll();
	
	Measure findById(int id);

	Measure create(String user_id, Date data_caricamento, String measure_hash, String gps, String name);
	
	Measure update(Measure measure);
	
	void delete(Measure measure);
	
}