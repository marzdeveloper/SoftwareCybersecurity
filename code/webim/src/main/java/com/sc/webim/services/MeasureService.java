package com.sc.webim.services;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sc.webim.model.entities.Measure;

public interface MeasureService {
	
	List<Measure> findAll();
	
	Measure findById(int id);
	
	Measure findByName(String name);

	Measure create(String user_id, Date data_caricamento, String measure_hash, String name, boolean transactionless);
	
	Measure update(Measure measure);
	
	void delete(Measure measure);
	
	int saveMeasure(String autor, MultipartFile measure);
}