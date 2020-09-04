package com.sc.webim.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.model.dao.MeasureDao;
import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;
import com.sc.webim.model.Job;

@Transactional
@Service("journalService")
public class JournalServiceDefault implements JournalService {
	private MeasureDao measureRepository;
	
	@Override
	public ArrayList<Job> getAllJobsDB() {
		ArrayList<Job> list_job = new ArrayList<Job>();
		Job job = new Job();
		List<Measure> list_measure = measureRepository.findAllTransactionless();
		for (Measure m: list_measure) {
			List<Image> list = new ArrayList<>(measureRepository.getImages(m));
			ArrayList<String> list_img = new ArrayList<String>();			
			for (Image i: list) {
				list_img.add(i.getName());
			}
			job = new Job();
			job.setMeasure(m.getName());
			job.setImages(list_img);
			list_job.add(job);
		}
		return list_job;
	}
	
	@Override
	public Job getJobByMeasureId(int id) {
		Job job = new Job();
		Measure m = measureRepository.findById(id);
		List<Image> list = new ArrayList<>(measureRepository.getImages(m));
		
		ArrayList<String> list_img = new ArrayList<String>();			
		for (Image i: list) {
			list_img.add(i.getName());
		}
		
		job.setMeasure(m.getName());
		job.setImages(list_img);
		return job;
	}
	
	@Autowired
	public void setImageRepository(ImageDao imageRepository, MeasureDao measureRepository) {
		this.measureRepository = measureRepository;
	}
}
