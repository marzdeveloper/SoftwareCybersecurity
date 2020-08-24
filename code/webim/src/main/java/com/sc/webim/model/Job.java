package com.sc.webim.model;

import java.util.ArrayList;
import java.util.Date;


public class Job {
	String worker;
	String measure;
	ArrayList<String> images;
	int jobID;
	Date date;
	
	/*create new job*/
	public Job(String _worker, String _measure,String _images, int _jobID, Date _date) {
		//this.images = new ArrayList<String>();
		
		this.worker=_worker;
		this.measure=_measure;
		String[] imgs = _images.split(",");
		this.images = new ArrayList<String>();
		for(String img:imgs) {
			this.images.add(img.trim());
		}
		this.jobID = _jobID;
		this.date = _date;
	}
	
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
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
	public Date getDate() {
		return date;
	}
	public void setdate(Date date) {
		this.date = date;
	}
}
