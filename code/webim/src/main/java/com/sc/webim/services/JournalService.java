package com.sc.webim.services;

import java.util.ArrayList;

import com.sc.webim.model.Job;

public interface JournalService {
	//Job getJobByHash(Job job);
	
	ArrayList<Job> getAllJobsDB();
	
	Job getJobByMeasureId(int id);
}
