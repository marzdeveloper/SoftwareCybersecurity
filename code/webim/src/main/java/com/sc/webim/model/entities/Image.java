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
    private int user_id;
    private String image_hash;
    // aggiungere gps
    
    private Measure measure_id;

    //Get and Set
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Image_id")
	public int getImage_id() {
		return this.image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
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

	@Column(name = "Image_hash", nullable = false, unique = true)
	public String getImage_hash() {
		return this.image_hash;
	}

	public void setImage_hash(String image_hash) {
		this.image_hash = image_hash;
	}

	//toString
	
	@Override
	public String toString() {
		return "Image - Id: " + image_id + ", User - Id: " + user_id +
				", Data caricamento: " + data_caricamento + ", Hash immagine: " + image_hash;
	}
	
	//Relationships
	
	//Many images have one measure
	@ManyToOne
	@JoinColumn(name = "Measure_id", nullable=true)
	public Measure getMeasure_id() {
		return this.measure_id;
	}

	public void setMeasure_id(Measure measure_id) {
		this.measure_id = measure_id;
	}
    
}
