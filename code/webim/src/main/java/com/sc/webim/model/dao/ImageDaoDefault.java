package com.sc.webim.model.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sc.webim.model.entities.Image;

@Transactional
@Repository("imageDao")
public class ImageDaoDefault extends DefaultDao implements ImageDao{
	
	@Override
	@Transactional(readOnly = true)
	public List<Image> findAll() {
		return getSession().createQuery("from Image i", Image.class).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Image> findAllTransactionless() {
		Query q = this.getSession().createQuery("FROM Image i WHERE i.image_id in (SELECT DISTINCT i.image_id from Image i WHERE i.measure_id IS NULL) OR i.image_id in (SELECT DISTINCT i from Image i, Measure m WHERE i.measure_id = m.measure_id AND m.transactionless=1)", Image.class);
		return q.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Image findById(int id) {
		return getSession().find(Image.class, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Image findByName(String name) {
		Query q = this.getSession().createQuery("from Image i WHERE i.name = :name", Image.class);
		return (Image) q.setParameter("name", name).getSingleResult();
	}
	
	@Override
	@Transactional
	public boolean delete(Image image) {
		//controllo che non vengano eliminate immagini che fanno parte del giornal dei lavori
		Query q = this.getSession().createQuery("SELECT distinct i FROM Image i, Measure m WHERE i.measure_id = m.measure_id AND m.transactionless = 0", Image.class);
		if(!q.getResultList().contains(image)) {
			this.getSession().delete(image);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public Image create(String user_id, Date data_caricamento, String image_hash, String name, String gps, Date data_originale, int height, int width, String maker){
		Image image = new Image();
		image.setUser_id(user_id);
		image.setData_caricamento(data_caricamento);
		image.setImage_hash(image_hash);
		image.setName(name);
		image.setGPS(gps);
		image.setDataOriginale(data_originale);
		image.setHeight(height);
		image.setWidth(width);
		image.setMaker(maker);
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

	@Override
	@Transactional(readOnly = true)
	public Image findByHash(String hash) {
		Query q = this.getSession().createQuery("from Image i WHERE i.image_hash = :hash", Image.class);
		Image i = null;
		try {
			i = (Image) q.setParameter("hash", hash).getSingleResult();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return i;
	}
}