package com.sc.webim.model.entities;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Measure")
public class Measure implements Serializable{
	
	private static final long serialVersionUID = 1847253395173328425L;

	private int measure_id;
	private String measure_hash;
	private String timestamp;
	private String user_id;
	private String name;
	private boolean transactionless;
	
	private Set<Image> images = new HashSet<Image>();
	
	//Get and Set

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Measure_id")
	public int getMeasure_id() {
		return this.measure_id;
	}

	public void setMeasure_id(int measure_id) {
		this.measure_id = measure_id;
	}

	@Column(name = "Measure_hash", nullable = false, unique = true)
	public String getMeasure_hash() {
		return this.measure_hash;
	}

	public void setMeasure_hash(String measure_hash) {
		this.measure_hash = measure_hash;
	}

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Timestamp", nullable = false)
	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Column(name = "User_id", nullable = false)
	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Column(name = "Name", nullable = false, unique = true)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "Transactionless", nullable = false, columnDefinition = "boolean default true")
	public boolean getTransactionless() {
		return this.transactionless;
	}
	
	public void setTransactionless(boolean transactionless) {
		this.transactionless = transactionless;
	}
	
	
	//toString
	
	@Override
	public String toString() {
		return "Measure - Id: " + measure_id + ", User - Id: " + user_id +
				", Timestamp: " + timestamp + ", Hash misura: " + measure_hash;
	}
	
	//Relationships
	
	//One measure has many images
	@OneToMany(mappedBy="measure_id", fetch = FetchType.EAGER)
	public Set<Image> getImages() {
		return this.images;
	}

	public void addImage(Image image) {
		image.setMeasure_id(this);
		this.images.add(image);		
    }
	
	public void setImages(Set<Image> images) {
		this.images = images;
	}
	
}
