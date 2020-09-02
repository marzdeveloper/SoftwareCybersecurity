package com.sc.webim.model.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;

@Transactional
@Repository("measureDao")
public class MeasureDaoDefault extends DefaultDao implements MeasureDao{
	
	@Override
	@Transactional(readOnly = true)
	public List<Measure> findAll() {
		return getSession().
				createQuery("from Measure m", Measure.class).
				getResultList();
	}
	
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Measure> findAllTransactionless() {
		Query q = this.getSession().createQuery("SELECT distinct m from Measure m, Image i WHERE m.transactionless = 1 AND m.measure_id = i.measure_id", Measure.class);
		return q.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Measure findById(int id) {
		return getSession().find(Measure.class, id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Measure findByName(String name) {
		Query q = this.getSession().createQuery("from Measure m WHERE m.name = :name", Measure.class);
		return (Measure) q.setParameter("name", name).getSingleResult();
	}

	@Override
	@Transactional
	public Measure create(String user_id, String timestamp, String measure_hash, String name, boolean transactionless) {
		Measure measure = new Measure();
		measure.setUser_id(user_id);
		measure.setTimestamp(timestamp);
		measure.setMeasure_hash(measure_hash);
		measure.setName(name);
		measure.setTransactionless(transactionless);
		this.getSession().save(measure);
		return measure;
	}
	
	@Override
	@Transactional
	public Measure update(Measure measure) {
		Measure merged = (Measure)this.getSession().merge(measure);
		return merged;
	}
	
		
	@Override
	@Transactional
	public void delete(Measure measure) {
		this.getSession().createQuery("update Image i set i.measure_id = null WHERE i.measure_id = :measure").setParameter("measure", measure).executeUpdate();
		this.getSession().delete(measure);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Set<Image> getImages(Measure measure) {
		Query q = this.getSession().createQuery("from Image i JOIN FETCH i.measure_id WHERE i.measure_id = :measure", Image.class);
		return new HashSet<Image>(q.setParameter("measure", measure).getResultList());
	}

	@Override
	@Transactional(readOnly = true)
	public Measure findByHash(String hash) {
		Query q = this.getSession().createQuery("from Measure m WHERE m.measure_hash = :hash", Measure.class);
		Measure m = null;
		System.out.println("Cercando misura");
		try {
			m = (Measure) q.setParameter("hash", hash).getSingleResult();
		} catch (Exception e) {
			System.out.println("Errore");
			e.printStackTrace();
		}
		return m;
	}
}