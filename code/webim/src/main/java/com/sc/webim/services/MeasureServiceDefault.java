package com.sc.webim.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sc.webim.model.dao.MeasureDao;
import com.sc.webim.model.entities.Measure;

@Transactional
@Service("measureService")
public class MeasureServiceDefault implements MeasureService {
	
	private MeasureDao measureRepository;
	
	@Override
	public List<Measure> findAll() {
		return this.measureRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Measure findById(int id) {
		return this.measureRepository.findById(id);
	}

	@Override
	public Measure create(String user_id, Date data_caricamento, String measure_hash) {
		return this.measureRepository.create(user_id, data_caricamento, measure_hash);
	}

	@Override
	public Measure update(Measure measure) {
		return this.measureRepository.update(measure);
	}

	@Override
	public void delete(Measure measure) {
		this.measureRepository.delete(measure);
	}

	@Autowired
	public void setMeasureRepository(MeasureDao measureRepository) {
		this.measureRepository = measureRepository;
	}
	
}