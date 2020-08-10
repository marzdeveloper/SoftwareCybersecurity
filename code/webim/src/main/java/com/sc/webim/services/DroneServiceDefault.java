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
import com.sc.webim.model.dao.ImageDao;
import com.sc.webim.model.entities.Image;

import net.sf.jmimemagic.Magic;


@Transactional
@Service("droneService")
public class DroneServiceDefault implements DroneService {
	private final Path root = Paths.get("uploads");
	private ImageDao imageRepository;
	
	@Override
	public int saveImage(MultipartFile image, int code) {
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
	    					
	    					for(Image im:Imagedb) {
	    						if(hash.equals(im.getImage_hash())){
	    							 //L'immagine gia esiste nel DB
	    							code = -3;
	    							break;
	    						}
	    					}
	    					if (code >= 0) {
	    						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    					    Date date = new Date();
	    					    Date dataCreazione = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(formatter.format(date));
	    						
	    						Image img = imageRepository.create("drone", dataCreazione, hash, image.getOriginalFilename(), latitude + "," + longitude);
	    						imageRepository.update(img);
	    						
	    						Path path = Paths.get(root + "/" + image.getOriginalFilename());
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
	
	public void getGPSImage(String fileName) throws ImageReadException, IOException {
		File file = new File(Paths.get(root + "/" + fileName).toString());
		IImageMetadata metadata =  Sanselan.getMetadata(file);
		
		if (metadata instanceof JpegImageMetadata) {
			JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			TiffImageMetadata exifMetadata = jpegMetadata.getExif();
			
	        if (null != exifMetadata) {
	            TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
	            
	            if (null != gpsInfo) {
	                String gpsDescription = gpsInfo.toString();
	                double longitude = gpsInfo.getLongitudeAsDegreesEast();
	                double latitude = gpsInfo.getLatitudeAsDegreesNorth();
	                
	                TiffField field = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_ALTITUDE);
	                String altitude = field.getValueDescription();

	                System.out.println("    " + "GPS Description: " + gpsDescription);
	                System.out.println("    " + "GPS Longitude (Degrees East): " + longitude);
	                System.out.println("    " + "GPS Latitude (Degrees North): " + latitude);
	                System.out.println("    " + "GPS Altitude: " + altitude);
	            }
	        }
		}
	}
	
	@Override
	public void metadataExample(String fileName) throws ImageReadException, IOException {
		// get all metadata stored in EXIF format (ie. from JPEG or TIFF).
		File file = new File(Paths.get(root + "/" + fileName).toString());
		final IImageMetadata metadata =  Sanselan.getMetadata(file);
		
		// System.out.println(metadata);
		if (metadata instanceof JpegImageMetadata) {
			final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
			// Jpeg EXIF metadata is stored in a TIFF-based directory structure
		    // and is identified with TIFF tags.
		    // Here we look for the "x resolution" tag, but
		    // we could just as easily search for any other tag.
		    //
		    // see the TiffConstants file for a list of TIFF tags.

		    System.out.println("file: " + file.getPath());
		    
		    // print out various interesting EXIF tags.
		    printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_XRESOLUTION);
		    printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME);
		    printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
		    //printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);
		    printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_ISO);
		    printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
		    printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
		    printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);
		    printTagValue(jpegMetadata, GPSTagConstants.GPS_TAG_GPS_LATITUDE_REF);
		    printTagValue(jpegMetadata, GPSTagConstants.GPS_TAG_GPS_LATITUDE);
		    printTagValue(jpegMetadata, GPSTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
		    printTagValue(jpegMetadata, GPSTagConstants.GPS_TAG_GPS_LONGITUDE);
		
		    System.out.println();
		    
		 // simple interface to GPS data
	        final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
	        if (null != exifMetadata) {
	            final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
	            if (null != gpsInfo) {
	                final String gpsDescription = gpsInfo.toString();
	                final double longitude = gpsInfo.getLongitudeAsDegreesEast();
	                final double latitude = gpsInfo.getLatitudeAsDegreesNorth();

	                System.out.println("    " + "GPS Description: " + gpsDescription);
	                System.out.println("    " + "GPS Longitude (Degrees East): " + longitude);
	                System.out.println("    " + "GPS Latitude (Degrees North): " + latitude);
	            }
	        }

	        // more specific example of how to manually access GPS values
	        final TiffField gpsLatitudeRefField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LATITUDE_REF);
	        final TiffField gpsLatitudeField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LATITUDE);
	        final TiffField gpsLongitudeRefField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
	        final TiffField gpsLongitudeField = jpegMetadata.findEXIFValue(GPSTagConstants.GPS_TAG_GPS_LONGITUDE);
	        if (gpsLatitudeRefField != null && gpsLatitudeField != null
	                && gpsLongitudeRefField != null && gpsLongitudeField != null) {
	            // all of these values are strings.
	            final String gpsLatitudeRef = (String) gpsLatitudeRefField.getValue();
	            final RationalNumber gpsLatitude[] = (RationalNumber[]) (gpsLatitudeField.getValue());
	            final String gpsLongitudeRef = (String) gpsLongitudeRefField.getValue();
	            final RationalNumber gpsLongitude[] = (RationalNumber[]) gpsLongitudeField.getValue();

	            final RationalNumber gpsLatitudeDegrees = gpsLatitude[0];
	            final RationalNumber gpsLatitudeMinutes = gpsLatitude[1];
	            final RationalNumber gpsLatitudeSeconds = gpsLatitude[2];

	            final RationalNumber gpsLongitudeDegrees = gpsLongitude[0];
	            final RationalNumber gpsLongitudeMinutes = gpsLongitude[1];
	            final RationalNumber gpsLongitudeSeconds = gpsLongitude[2];

	            // This will format the gps info like so:
	            //
	            // gpsLatitude: 8 degrees, 40 minutes, 42.2 seconds S
	            // gpsLongitude: 115 degrees, 26 minutes, 21.8 seconds E

	            System.out.println("    " + "GPS Latitude: "
	                    + gpsLatitudeDegrees.toDisplayString() + " degrees, "
	                    + gpsLatitudeMinutes.toDisplayString() + " minutes, "
	                    + gpsLatitudeSeconds.toDisplayString() + " seconds "
	                    + gpsLatitudeRef);
	            System.out.println("    " + "GPS Longitude: "
	                    + gpsLongitudeDegrees.toDisplayString() + " degrees, "
	                    + gpsLongitudeMinutes.toDisplayString() + " minutes, "
	                    + gpsLongitudeSeconds.toDisplayString() + " seconds "
	                    + gpsLongitudeRef);

	        }

	        System.out.println();

	        final List<Item> items = jpegMetadata.getItems();
	        for (int i = 0; i < items.size(); i++) {
	            final Item item = items.get(i);
	            System.out.println("    " + "item: " + item);
	        }

	        System.out.println();
		}
	}

	private static void printTagValue(final JpegImageMetadata jpegMetadata, final TagInfo tagInfo) {
		final TiffField field = jpegMetadata.findEXIFValue(tagInfo);
		if (field == null) {
			System.out.println(tagInfo.name + ": " + "Not Found.");
		  } else {
		      System.out.println(tagInfo.name + ": " + field.getValueDescription());
		  }
	}
	
	@Autowired
	public void setImageRepository(ImageDao imageRepository) {
		this.imageRepository = imageRepository;
	}

}
