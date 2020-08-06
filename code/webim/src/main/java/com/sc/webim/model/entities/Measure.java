package com.sc.webim.model.entities;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Measure")
public class Measure implements Serializable{
	
	private static final long serialVersionUID = 1847253395173328425L;

	private int measure_id;
	private String measure_hash;
	private Date data_caricamento;
	private int user_id;
	// aggiungere gps
	
	private Set<Image> images = new HashSet<Image>();
	
	//Get and Set

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Temporal(TemporalType.DATE)
	@Column(name = "Data_caricamento", nullable = true)
	public Date getData_caricamento() {
		return this.data_caricamento;
	}

	public void setData_caricamento(Date data_caricamento) {
		this.data_caricamento = data_caricamento;
	}

	@Column(name = "User_id", nullable = false, unique = true)
	public int getUser_id() {
		return this.user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	//toString
	
	@Override
	public String toString() {
		return "Measure - Id: " + measure_id + ", User - Id: " + user_id +
				", Data caricamento: " + data_caricamento + ", Hash misura: " + measure_hash;
	}
	
	//Relationdhips
	
	//One measure has many images
	@OneToMany(mappedBy="Measure")
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
