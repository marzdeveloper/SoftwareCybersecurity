package com.sc.webim.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sc.webim.Utils;
import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.model.dao.MeasureDao;
import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;

import net.sf.jmimemagic.Magic;

@Transactional
@Service("imageService")
public class ImageServiceDefault implements ImageService {
	
	private final Path root = Paths.get("src/main/webapp/WEB-INF/uploads/images");
	private ImageDao imageRepository;
	private MeasureDao measureRepository;
	
	@Override
	public List<Image> findAll() {
		return this.imageRepository.findAll();
	}
	
	@Override
	public List<Image> findAllTransactionless() {
		return this.imageRepository.findAllTransactionless();
	}

	@Transactional(readOnly=true)
	@Override
	public Image findById(int id) {
		return this.imageRepository.findById(id);
	}
	
	
	@Transactional(readOnly=true)
	@Override
	public Image findByName(String name) {
		return this.imageRepository.findByName(name);
	}

	@Override
	public Image create(String user_id, Date data_caricamento, String measure_hash, String gps, String name, Date data_originale) {
		return this.imageRepository.create(user_id, data_caricamento, measure_hash, gps, name, data_originale);
	}

	@Override
	public Image update(Image image) {
		return this.imageRepository.update(image);
	}

	@Override
	public void delete(Image image) {
		this.imageRepository.delete(image);
	}
	
	@Override
	public boolean delete(int id) {
		Image img = findById(id);
		Path path = Paths.get(root + "/" + img.getName());
		File file = new File(path.toString()); 
		if(this.imageRepository.delete(img)) {
			file.delete();
			return true;
		}
		return false;
	}
	
	@Override
	public String getPathImage(String imageName) {
		Path path = Paths.get(root + "/" + imageName);
		return path.toString();
	}
	
	@Override
	public int saveImage(String user, MultipartFile image) {
		int code = 0;
		try {
			byte[] bytes = image.getBytes();
			
			//Controllo il file sia una immagine
			String mimeType = Magic.getMagicMatch(image.getBytes(), false).getMimeType();	    
		    String type = mimeType.split("/")[0];
	        if(type.equals("image")) { //è una immagine
	        	code = 1;
	        	//Controllo l'immagine abbia metadata GPS
	    		IImageMetadata metadata =  Sanselan.getMetadata(image.getBytes());
	    		
	    		if (metadata instanceof JpegImageMetadata) {
	    			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
	    			TiffImageMetadata exifMetadata = jpegMetadata.getExif();
	    			
	    			TiffField field = jpegMetadata.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
	    			String StringDataOriginale = field.getValueDescription();
	    			StringDataOriginale = StringDataOriginale.replace("'", "");
	    			StringDataOriginale = StringDataOriginale.replace(":", "-");
	    			
	    	        if (null != exifMetadata) {
	    	            TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
	    	            
	    	            if (null != gpsInfo) {
	    	            	code = 2;
	    	                double longitude = gpsInfo.getLongitudeAsDegreesEast();
	    	                double latitude = gpsInfo.getLatitudeAsDegreesNorth();
	    	                
	    	                //Controllo l'immagine non esista gia nel DB
	    					MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    					String hash = DatatypeConverter.printHexBinary(digest.digest(bytes));  
	    					List <Image> Imagedb = this.imageRepository.findAll();
	    					String image_name = image.getOriginalFilename();
	    					
	    					for(Image im:Imagedb) {
	    						if(hash.equals(im.getImage_hash())){
	    							 //L'immagine gia esiste nel DB
	    							code = -3;
	    							break;
	    						}
	    						if(image_name.equals(im.getName())) {
	    							//L'immagine ha lo stesso nome di un'altra sul DB
	    							code = -4;
	    							break;
	    						}
	    					}
	    					
	    					//Controllo che non ci siano immagini con lo stesso nome nel database
	    					
	    					if (code >= 0) {
	    						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    						SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	    					    Date date = new Date();
	    					    Date dataCreazione = Utils.date(formatter.format(date));
	    					    Date dataOriginale = formatter1.parse(StringDataOriginale);  
	    					    
	    						Image img = imageRepository.create(user, dataCreazione, hash, image_name, latitude + "," + longitude, dataOriginale);
	    						imageRepository.update(img);
	    						
	    						Path path = Paths.get(root + "/" + image_name);
	    						Files.write(path, bytes);
	    						code = 3;
	    					}
	    	            } else {
	    	            	//L'immagine non ha metadata GPS
	    	            	code = -2;
	    	            }
	    	        }
	    		}
	        } else {
	        	//Il file non è una immagine
	        	code = -1;
	        }
		}
		catch(Exception e) {
			e.printStackTrace();
			code = -9;
		}
		return code;
	};
	
	@Override
	public List<Image> findImages(ArrayList<String> images) {
		List<Image> list = new ArrayList<Image>();
		for (int i = 0; i < images.size(); i ++) {
			int id = Integer.parseInt(images.get(i));
			Image img = findById(id);
			if (img == null) {
				return null;
			} else {
				list.add(img);
			}
		}
		return list;
	}
	
	@Override
	public boolean setMeasureImages(List<Image> list, int id) {
		//Debe essitere prima la Measure
		//Questo si fa perche non c'è API
		boolean resp = false;
		
		Measure m = measureRepository.findById(id);
		//Controlliamo esista una misura per asegnare
		if (m != null) {
			List<?> img_list = new ArrayList<>(measureRepository.getImages(m));
			//Controlliamo la misura non habbia gia delle immagini assegnate
			if (img_list.size() == 0) {
				boolean errore = false;
				//Controlliamo le immagini non abbiano una misura gia asegnata
				for (int i = 0; i < list.size(); i++) {
					Measure img_m = list.get(i).getMeasure_id();
					if (img_m != null) {
						errore = true;
						break;
					}
				}
				if (!errore) {
					//Assegniamo la misura alle immagini
					for (int i = 0; i < list.size(); i++) {
						Image img = list.get(i);
						img.setMeasure_id(m);
						this.imageRepository.update(img);
					}
					resp = true;
				}
			}
		}
		
		return resp;
	}
	
	@Autowired
	public void setImageRepository(ImageDao imageRepository, MeasureDao measureRepository) {
		this.imageRepository = imageRepository;
		this.measureRepository = measureRepository;
	}
}
