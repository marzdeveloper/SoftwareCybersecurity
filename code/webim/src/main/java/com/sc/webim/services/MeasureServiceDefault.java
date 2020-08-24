package com.sc.webim.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sc.webim.Utils;
import com.sc.webim.model.dao.MeasureDao;
import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;

@Transactional
@Service("measureService")
public class MeasureServiceDefault implements MeasureService {
	private final Path root = Paths.get("src/main/webapp/WEB-INF/uploads/measures");
	private MeasureDao measureRepository;
	
	@Override
	public List<Measure> findAll() {
		return this.measureRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Measure findById(int id) {
		return this.measureRepository.findById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Measure findByName(String name) {
		return this.measureRepository.findByName(name);
	}

	@Override
	public Measure create(String user_id, Date data_caricamento, String measure_hash, String name, boolean transactionless) {
		return this.measureRepository.create(user_id, data_caricamento, measure_hash, name, transactionless);
	}

	@Override
	public Measure update(Measure measure) {
		return this.measureRepository.update(measure);
	}

	@Override
	public void delete(Measure measure) {
		this.measureRepository.delete(measure);
	}

	@Override
	public int saveMeasure(String autor, MultipartFile measure) {
		int resp = 0;
		try {
			byte[] bytes = measure.getBytes();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String hash = DatatypeConverter.printHexBinary(digest.digest(bytes));
			Measure m = this.measureRepository.findByHash(hash);
			if (m == null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			    Date date = new Date();
			    
			    String s1 = measure.getOriginalFilename();
			    String[] s = s1.split("\\.");

			    m = create(autor, Utils.date(formatter.format(date)), hash, "m-" + formatter2.format(date).toString() + "." + s[s.length-1], true);
			    
			    Path path = Paths.get(root + "/" + "m-" + formatter2.format(date).toString() + "." + s[s.length-1]);
				Files.write(path, bytes);
			    
				resp = m.getMeasure_id();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			resp = -1;
		}
		return resp;
	}
	
	@Autowired
	public void setMeasureRepository(MeasureDao measureRepository) {
		this.measureRepository = measureRepository;
	}
}