package com.sc.webim.model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;

@Transactional
@Repository("imageDao")
public class ImageDaoDefault extends DefaultDao implements ImageDao{
	
	@Override
	@Transactional(readOnly = true)
	public List<Image> findAll() {
		return getSession().createQuery("from Image i", Image.class).getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Image findById(int id) {
		return getSession().find(Image.class, id);
	}

	@Override
	@Transactional
	public void delete(Image image) {
		this.getSession().delete(image);	
	}

	@Override
	@Transactional
	public Image create(String user_id, Date data_caricamento, String image_hash, Measure measure_id){
		Image image = new Image();
		image.setUser_id(user_id);
		image.setData_caricamento(data_caricamento);
		image.setImage_hash(image_hash);
		image.setMeasure_id(measure_id);
		this.getSession().save(image);
		return image;
	}

	@Override
	@Transactional
	public Image update(Image image) {
		return (Image)this.getSession().merge(image);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Image> findAllByMeasureId(int measure) {
		Query q = this.getSession().createQuery("SELECT distinct i from Image i JOIN FETCH i.measure_id WHERE i.measure_id.measure_id = :measure", Image.class);
		return q.setParameter("measure", measure).getResultList();
	}

}