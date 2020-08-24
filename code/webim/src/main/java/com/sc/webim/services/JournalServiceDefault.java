package com.sc.webim.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.stream.ImageInputStream;
import javax.xml.bind.DatatypeConverter;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.ImageMetadata.Item;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.GPSTagConstants;
import org.apache.sanselan.formats.tiff.constants.TiffTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.sc.webim.Utils;
import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.model.dao.MeasureDao;
import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;
import com.sc.webim.model.Job;

import net.sf.jmimemagic.Magic;

@Transactional
@Service("journalService")
public class JournalServiceDefault implements JournalService {
	private ImageDao imageRepository;
	private MeasureDao measureRepository;
	
	@Override
	public Job getJobByHash(Job job) {
		ArrayList<String> list_img = new ArrayList<String>();
		
		String hash_m = job.getMeasure();
		Measure m = measureRepository.findByHash(hash_m);
		
		ArrayList<String> list_hash_img = job.getImages();
		for (String hash_img: list_hash_img) {
			Image img = imageRepository.findByHash(hash_img);
			list_img.add(img.getName());
		}
		
		Job j = new Job();
		j.setJobID(job.getJobID());
		j.setMeasure(m.getName());
		j.setImages(list_img);
		j.setdate(job.getDate());
		
		return j;
	};
	
	@Override
	public ArrayList<Job> getAllJobsDB() {
		ArrayList<Job> list_job = new ArrayList<Job>();
		Job job = new Job();
		List<Measure> list_measure = measureRepository.findAllTransactionless();
		for (Measure m: list_measure) {
			List<Image> list = new ArrayList<>(measureRepository.getImages(m));
			ArrayList<String> list_img = new ArrayList<String>();			
			for (Image i: list) {
				list_img.add(i.getName());
			}
			job = new Job();
			job.setMeasure(m.getName());
			job.setImages(list_img);
			list_job.add(job);
		}
		return list_job;
	}
	
	@Override
	public Job getJobByMeasureId(int id) {
		Job job = new Job();
		Measure m = measureRepository.findById(id);
		List<Image> list = new ArrayList<>(measureRepository.getImages(m));
		
		ArrayList<String> list_img = new ArrayList<String>();			
		for (Image i: list) {
			list_img.add(i.getName());
		}
		
		job.setMeasure(m.getName());
		job.setImages(list_img);
		return job;
	}
	
	@Autowired
	public void setImageRepository(ImageDao imageRepository, MeasureDao measureRepository) {
		this.imageRepository = imageRepository;
		this.measureRepository = measureRepository;
	}
}
