package com.sc.webim.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

@Entity
@Table(name="Image")
public class Image implements Serializable{

	private static final long serialVersionUID = 1847253395173328425L;
	
    private int image_id;
	private Date data_caricamento;
    private String user_id;
    private String image_hash;
    private String name;
    private String gps;
    private Measure measure_id;
    private Date data_originale;

    //Get and Set
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Image_id")
	public int getImage_id() {
		return this.image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "Data_caricamento", nullable = false)
	public Date getData_caricamento() {
		return this.data_caricamento;
	}

	public void setData_caricamento(Date data_caricamento) {
		this.data_caricamento = data_caricamento;
	}

	@Column(name = "User_id", nullable = false)
	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Column(name = "Image_hash", nullable = false, unique = true)
	public String getImage_hash() {
		return this.image_hash;
	}

	public void setImage_hash(String image_hash) {
		this.image_hash = image_hash;
	}
	
	@Column(name = "GPS", nullable = false)
	public String getGPS() {
		return this.gps;
	}

	public void setGPS(String gps) {
		this.gps = gps;
	}

	@Column(name = "Name", nullable = false, unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "Data_Originale", nullable = false)
	public Date getDataOriginale() {
		return this.data_originale;
	}

	public void setDataOriginale(Date data_originale) {
		this.data_originale = data_originale;
	}
	
	//toString
	
	@Override
	public String toString() {
		return "Image - Id: " + image_id + ", User - Id: " + user_id + ", Name: " + name
				+ ", Data caricamento: " + data_caricamento + ", Hash immagine: " + image_hash
				+ ", GPS: " + gps;
	}
	
	//Relationships
	
	//Many images have one measure
	@ManyToOne
	@JoinColumn(name = "Measure_id", nullable=true)
	public Measure getMeasure_id() {
		return this.measure_id;
	}

	public void setMeasure_id(Measure measure) {
		this.measure_id = measure;
	}
    
}
