package com.sc.webim.model;

import java.util.ArrayList;

public class JournalModel {
	ArrayList<Job> jobs;	
    public int jobGenerated=0;
       
    public JournalModel() {
    	this.jobs = new ArrayList<Job>();
    }
    
    public void addNewJob(String _worker, String _measure, String _image, String _date) {
    	Job job = new Job(_worker,  _measure,  _image,  jobGenerated, _date);
        this.jobs.add(job);
        jobGenerated++;
    }
    
    /*update job : metto una nuova immagine-misura nel job scelto tramite id
	public void updateJob(String _worker, String _measure, String _image, int _jobId) {
        for(int i=0;i< this.jobs.size();i++) {
    		if(this.jobs.get(i).getJobID() == _jobId) {
    			this.jobs.get(i).workers.add(_worker);
    			this.jobs.get(i).measures.add(_measure);
    			this.jobs.get(i).images.add(_image);
    		}
        }
	} */

	public ArrayList<Job> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}

	public int getJobGenerated() {
		return jobGenerated;
	}

	/*
	
	public Job getJobByMeasure(String _measure) {
		for(int i=0;i< this.jobs.size();i++) {
			
		
			for(int j=0;j< this.jobs.get(i).getMeasures().size();j++) {
				
				if(this.jobs.get(i).getMeasures().get(j) == _measure)
				
					return(this.jobs.get(i));
				
				
			}
		
    		}
		
	}
	
	
	public Job getJobByImage(String _image) {
		for(int i=0;i< this.jobs.size();i++) {
			
		
			for(int j=0;j< this.jobs.get(i).getImages().size();j++) {
				
				if(this.jobs.get(i).getImages().get(j) == _image)
				
					return(this.jobs.get(i));
				
				
			}
		
    		}
		
	}
	    */

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
