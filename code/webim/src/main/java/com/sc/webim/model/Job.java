package com.sc.webim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Job {
	
	
	ArrayList<String> workers ;
	ArrayList<String> measures;
	ArrayList<String> images;
	int jobID;
	
	/*create new job*/
	public Job(String _worker, String _measure, String _image, int _jobID) {
		this.workers = new ArrayList<String>();
		this.measures = new ArrayList<String>();
		this.images = new ArrayList<String>();
		
		this.workers.add(_worker);
		this.measures.add(_measure);
		this.images.add(_image);
		this.jobID = _jobID;

	}
	

	
	public ArrayList<String> getWorkers() {
		return workers;
	}
	public void setWorkers(ArrayList<String> workers) {
		this.workers = workers;
	}
	public ArrayList<String> getMeasures() {
		return measures;
	}
	public void setMeasures(ArrayList<String> measures) {
		this.measures = measures;
	}
	public ArrayList<String> getImages() {
		return images;
	}
	public void setImages(ArrayList<String> images) {
		this.images = images;
	}
	public int getJobID() {
		return jobID;
	}
	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

}
