package com.sc.webim.model;

import java.util.ArrayList;

public class JournalModel {
	ArrayList<Job> jobs;	
    public int jobGenerated=0;
       
    public JournalModel() {
    	this.jobs = new ArrayList<Job>();
    }
    
    public Job addNewJob(String _worker, String _measure, String _image, String _date) {
    	Job job = new Job(_worker,  _measure,  _image,  jobGenerated, _date);
        this.jobs.add(job);
        jobGenerated++;
        return job;
    }

	public ArrayList<Job> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}

	public int getJobGenerated() {
		return jobGenerated;
	}

	public Job getJobById(int _jobId) {
		Job job = null;
		try {
			job = jobs.get(_jobId);
		}
		catch(Exception e) {
			//e.getStackTrace();
		}
		return job;
	}
	
}
