package com.sc.webim.model;

import java.util.ArrayList;

public class Job {
	String worker;
	String measure;
	ArrayList<String> images;
	int jobID;
	String date;
	
	/*create new job*/
	public Job() {
		
	}
	/* dentro _images dati sono salvati in questo ordine :
	 * hash +","+img.getName()+","+img.getUser_id()+","+img.getData_caricamento()+","+img.getGPS()+
	 * 	","+img.getWidth()+","+img.getHeight()+",";
	 * dentro _measure
	 * hash, nome

	 */

	public Job(String _worker, String _measure,String _images, int _jobID, String _date) {
		this.worker=_worker;
		
		
		String[] measData = _measure.split(",");

		this.measure= measData[0];
		
		
		String[] imgData = _images.split(",");
		this.images = new ArrayList<String>();

		for(int i=0;i<imgData.length ;i=i+7) {
			this.images.add(imgData[i]);
			
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
	public String getDate() {
		return date;
	}
	public void setdate(String date) {
		this.date = date;
	}
}
