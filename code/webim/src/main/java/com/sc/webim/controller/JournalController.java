package com.sc.webim.controller;

import static org.web3j.tx.Contract.staticExtractEventParameters;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.tx.ClientTransactionManager;

import com.sc.webim.connection.QuorumConnection;
import com.sc.webim.contracts.Journal;
import com.sc.webim.model.Job;
import com.sc.webim.model.JournalModel;
import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;
import com.sc.webim.services.ImageService;
import com.sc.webim.services.JournalService;
import com.sc.webim.services.MeasureService;

@Controller
@RequestMapping("/journal")
public class JournalController {
	private ImageService imageService;
	private MeasureService measureService;
	private JournalService journalService;
	
	private ArrayList<Job> transactions;
    private JournalModel journalModel;
	private final Path root = Paths.get("src/main/webapp/WEB-INF/uploads/");

    @Autowired
    QuorumConnection quorumConnection;

    @RequestMapping(method = RequestMethod.GET)
	public String showJournal(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg, @RequestParam(value = "resp", required = false) String resp) {
    	transactions = new ArrayList<Job>();
    	journalModel = new JournalModel();
    	
    	EthFilter filterToExtractNewJournals = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, Collections.emptyList()).addSingleTopic(EventEncoder.encode(Journal.EVENTJOB_EVENT));

        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewJournals).subscribe(messageLog -> {
            //Extract sendContractAddress event parameters defined in Thread contract
            EventValues sendContractAddressEventValues = staticExtractEventParameters(Journal.EVENTJOB_EVENT , messageLog);

            //Create sendContractAddress event response object to store individual parameter values
            Journal.EventJobEventResponse eventJobEventResponse = new Journal.EventJobEventResponse();
            
            eventJobEventResponse.worker = (String)sendContractAddressEventValues.getNonIndexedValues().get(0).getValue();
            eventJobEventResponse.measure = (String)sendContractAddressEventValues.getNonIndexedValues().get(1).getValue();
            eventJobEventResponse.images = (String)sendContractAddressEventValues.getNonIndexedValues().get(2).getValue();
            eventJobEventResponse.time = (BigInteger)sendContractAddressEventValues.getNonIndexedValues().get(4).getValue();
            
            String worker = eventJobEventResponse.worker.toString();
            String measure = eventJobEventResponse.measure.toString();
            String images = eventJobEventResponse.images.toString();
            long time = eventJobEventResponse.time.longValue() *1000;
                     
            Date date = new Date(time);
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");  
		    String data = formatter.format(date);
            
            journalModel.addNewJob(worker, measure, images, data);
            int job_id = journalModel.getJobGenerated() - 1;
            transactions.add(journalModel.getJobById(job_id));
        });

    	
        ArrayList<Job> AllJob = new ArrayList<Job>();        
        for (int i = 0; i < transactions.size(); i++) {
        	Job j = transactions.get(i);
          	AllJob.add(journalService.getJobByHash(j));
        }
		
        model.addAttribute("jobs", AllJob);
    	model.addAttribute("title", "Giornale dei lavori");
    	model.addAttribute("alertMsg", msg);
    	model.addAttribute("typeMsg", resp);
		return "journal/list";
	}
    
    /*@RequestMapping(value="/journals", method=RequestMethod.GET)
    public String showJournal(Model model) {
        //String eventJobEventTopic = "0xe789b4eeafbb3620234d3bd2c4767fd4e3baf0f8291ee7cb387280ce129e41d1";
        EthFilter filterToExtractNewJournals = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, Collections.emptyList()).addSingleTopic(EventEncoder.encode(Journal.EVENTJOB_EVENT));

        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewJournals).subscribe(messageLog -> {
            //Extract sendContractAddress event parameters defined in Thread contract
            EventValues sendContractAddressEventValues = staticExtractEventParameters(Journal.EVENTJOB_EVENT , messageLog);

            //Create sendContractAddress event response object to store individual parameter values
            Journal.EventJobEventResponse eventJobEventResponse = new Journal.EventJobEventResponse();
            
            eventJobEventResponse.worker = (String)sendContractAddressEventValues.getNonIndexedValues().get(0).getValue();
            eventJobEventResponse.measure = (String)sendContractAddressEventValues.getNonIndexedValues().get(1).getValue();
            eventJobEventResponse.images = (String)sendContractAddressEventValues.getNonIndexedValues().get(2).getValue();
            eventJobEventResponse.time = (BigInteger)sendContractAddressEventValues.getNonIndexedValues().get(2).getValue();

            String worker = eventJobEventResponse.worker.toString();
            String measure = eventJobEventResponse.measure.toString();
            String images = eventJobEventResponse.images.toString();
            long time = eventJobEventResponse.time.longValue();
            
            Date date = new Date(time);

            /*ArrayList<String> images = new ArrayList<String>();
            for(String img:eventJobEventResponse.images) {
                images.add(img.toString());  	//da ricontrollare
            }

            //Create new ThreadModel instance to save new thread details - contract address, participants
            journalModel.addNewJob(worker, measure, images, date);
            transactions= journalModel.getJobs();
        });
        
        try {
        	System.out.println(transactions.size());
        	
        	for (int i = 0; i < transactions.size(); i++) {
        		System.out.println("Iterazione: " + i);
        		ArrayList<String> images = transactions.get(i).getImages();
        		String measure = transactions.get(i).getMeasure();
        		String worker  = transactions.get(i).getWorker();
        		Date date = transactions.get(i).getDate();
        		
        		System.out.println("	Tutte le immagini: " + images.toString());
        		System.out.println("	Tutte le misure: " + measure.toString());
        		System.out.println("	Tutti i workers: " + worker.toString());
        		System.out.println("	date: " + date.toString());

        	}
        } catch (Exception e) {
        	System.out.println("Errore:");
        	e.printStackTrace();
        }
        
        model.addAttribute("title", "journal");
        model.addAttribute("threadModels", transactions);
        return "journal/list2";
    }*/
    
    @RequestMapping(value="/newJob", method=RequestMethod.GET)
    public String newJob(Model model, @RequestParam(value = "msg", required = false) String msg, @RequestParam(value = "resp", required = false) String resp) {
    	ArrayList<Job> list_jobs = journalService.getAllJobsDB();
    	
    	model.addAttribute("title", "Journal");
    	model.addAttribute("jobs", list_jobs);
    	model.addAttribute("alertMsg", msg);
    	model.addAttribute("typeMsg", resp);
        return "journal/newJob";
    }  
    
    @RequestMapping(value = "/{name}/getDetails", method = RequestMethod.GET)
	public String getDetails(Locale locale, Model model, @PathVariable("name") String name) {
    	Job job = new Job();
    	Measure m = measureService.findByName(name);
    	if (m != null) {
    		job = journalService.getJobByMeasureId(m.getMeasure_id());    		
    	}
		model.addAttribute("job", job);
		return "journal/modal";
	}
    
    @SuppressWarnings({ "unused", "deprecation", "null" })
	@RequestMapping(value="/createJournal", method=RequestMethod.POST, produces = "application/json", headers="Accept=application/json")
    public @ResponseBody String createJournal(Principal principal, Model model, @RequestParam("measure") String measure) {
        boolean error = false;
		String msg = "Operation failed";
		try {
			//Verifico esista la misura
	        if(measure != null || !measure.trim().equals(""))
	        {
	        	//Controllo la misura esista nel DB
	        	Measure m = measureService.findByName(measure);
	        	if (m != null) {
	        		//Controllo correttezza delle immagini selezionate
	                List<Image> DbImages = new ArrayList<>(m.getImages());
	                //List<Image> DbImages = imageService.findByMeasure(m);
	                
	                //Controllo degli hash
	                String hash_images= new String();
	                for(Image image:DbImages) {
	                	Path path = Paths.get(root + "/images/" + image.getName());
	        			byte[] bytes = Files.readAllBytes(path);
	        			MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        			String hash = DatatypeConverter.printHexBinary(digest.digest(bytes));  
	        			
	        			Image img = imageService.findByName(image.getName());
	        			if(!hash.equals(img.getImage_hash())){
	        				//L'immagine sul server è diversa da quella salvata nel DB
	        	        	error = true;
	        				msg = "The image on the server does not co-index with the one saved in the database";
	        				break;
	        			}
	        			hash_images += hash +",";
	                }
	        		
	                if (!error) {
	                	if(hash_images != null) {
	                        hash_images = hash_images.substring(0, hash_images.length() -1);
	                    }
	                	
	                	Path path = Paths.get(root + "/measures/" + m.getName());
	                	byte[] bytes = Files.readAllBytes(path);
	            		MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            		String hash = DatatypeConverter.printHexBinary(digest.digest(bytes));
	            		
	            		if(!hash.equals(m.getMeasure_hash())){
	            			//L'immagine sul server è diversa da quella salvata nel DB
	                    	error = true;
	                    	msg =  "The measure on the server does not co-index with the one saved in the database";
	            		} else {
	            			String hash_measure = hash;
	            			
	            			//Scelgo i nodi
	                    	List<String> privateFor = new ArrayList<String>();
	                    	//aggiungo le public key di tutti i nodi
	                    	privateFor.add("BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=");
	                    	privateFor.add("QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=");
	                    	privateFor.add("1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg=");
	                    	privateFor.add("oNspPPgszVUFw0qmGFfWwh1uxVUXgvBxleXORHj07g8=");
	                    	privateFor.add("R56gy4dn24YOjwyesTczYa8m5xhP6hF2uTMCju/1xkY=");
	                    	privateFor.add("UfNSeSGySeKg11DVNEnqrUtxYRVor4+CvluI8tVv62Y=");
	                    	privateFor.add("ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc=");
	                    	
	                    	//Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
	                    	ClientTransactionManager clientTransactionManager = new ClientTransactionManager(quorumConnection.getQuorum(), quorumConnection.getNodeAddress(), quorumConnection.getNodeKey(), privateFor, 100, 1000);

	                    	//Deploy the new thread contract. This returns a thread contract object
	            			Journal threadContract = Journal.deploy(quorumConnection.getQuorum(), clientTransactionManager, BigInteger.valueOf(0), BigInteger.valueOf(100000000)).send();
	                    	
	                    	//Extract contract address from thread contract object obtained in 2.d
	                    	String newThreadContractAddress = threadContract.getContractAddress();
	                    	
	                    	//String listString = String.join(",", hash_im);
	                    	
	                    	//Call the sendContractAddress event in thread contract to inform participants of new thread contract address and participants
	                    	TransactionReceipt startThreadTransactionReceipt = threadContract.addNewJob(hash_measure, hash_images).send();
	                    	
	                    	m.setTransactionless(false);
	            			measureService.update(m);
	            			msg = "Operation succeded";
	            		}
	                }
	        	} else {
	        		error = true;
	        		msg = "The measure was not found";
	        	}
	        } else {
	        	error = true;
	        	msg = "The measure does not exist";
	        }
		}  catch (Exception e) {
			//System.out.println("Error: " + e.getMessage());
			error = true;
			msg = "An unexpected error occurred";
		}
        
        String response = "{\"success\":" + !error + ", \"msg\":\"" + msg + "\"}";
		return response;
    }
    
    @Autowired
	public void setServices(ImageService imageService, MeasureService measureService, JournalService journalService) {
		this.imageService = imageService;
		this.measureService = measureService;
		this.journalService = journalService;
	}
}
