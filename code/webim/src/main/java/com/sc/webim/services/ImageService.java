package com.sc.webim.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.sanselan.ImageReadException;
import org.springframework.web.multipart.MultipartFile;

import com.sc.webim.model.entities.Image;

public interface ImageService {
	
	List<Image> findAll();
	
	List<Image> findAllTransactionless();
	
	Image findById(int id);
	
	Image findByName(String name);

	Image create(String user_id, Date data_caricamento, String image_hash, String name, String gps);
	
	Image update(Image image);
	
	void delete(Image image);
	
	void delete(int id);
	
	String getPathImage(String imageName);
	
	int saveImage(String user, MultipartFile img);
	
	List<Image> findImages(ArrayList<String> images);
	
	boolean setMeasureImages(List<Image> list, int measure);
}
