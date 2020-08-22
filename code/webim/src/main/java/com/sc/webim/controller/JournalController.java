package com.sc.webim.controller;

import static org.web3j.tx.Contract.staticExtractEventParameters;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.tx.ClientTransactionManager;

import com.sc.webim.connection.QuorumConnection;
import com.sc.webim.contracts.Journal;
import com.sc.webim.contracts.Thread;
import com.sc.webim.model.Job;
import com.sc.webim.model.JournalModel;
import com.sc.webim.model.entities.Image;
import com.sc.webim.model.entities.Measure;
import com.sc.webim.services.ImageService;
import com.sc.webim.services.MeasureService;


@Controller
@RequestMapping("/journal")
public class JournalController {
	private ImageService imageService;
	private MeasureService measureService;
	private ArrayList<Job> transactions = new ArrayList<Job>();
    private JournalModel journalModel = new JournalModel();
	private final Path root = Paths.get("src/main/webapp/WEB-INF/uploads/");


    @Autowired
    QuorumConnection quorumConnection;

    @RequestMapping(method = RequestMethod.GET)
	public String images(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg, 
			@RequestParam(value = "resp", required = false) String resp) {
		model.addAttribute("title", "journal");
		
		return "journal/list";
	}
    
    @RequestMapping(value="/journals", method=RequestMethod.GET)
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

            String worker = eventJobEventResponse.worker.toString();
            String measure = eventJobEventResponse.measure.toString();
            String images = eventJobEventResponse.images.toString();
            /*ArrayList<String> images = new ArrayList<String>();
            for(String img:eventJobEventResponse.images) {
                images.add(img.toString());  	//da ricontrollare
            }*/

            //Create new ThreadModel instance to save new thread details - contract address, participants
            journalModel.addNewJob(worker, measure, images);
            transactions= journalModel.getJobs();
        });
        
        System.out.println("/*********************************************************************/");
        try {
        	System.out.println(transactions.size());
        	
        	for (int i = 0; i < transactions.size(); i++) {
        		System.out.println("Iterazione: " + i);
        		ArrayList<String> images = transactions.get(i).getImages();
        		String measure = transactions.get(i).getMeasure();
        		String worker  = transactions.get(i).getWorker();
        		
        		System.out.println("	Tutte le immagini: " + images.toString());
        		System.out.println("	Tutte le misure: " + measure.toString());
        		System.out.println("	Tutti i workers: " + worker.toString());
        	}
        } catch (Exception e) {
        	System.out.println("Errore:");
        	e.printStackTrace();
        }
        System.out.println("/*********************************************************************/");
        
        model.addAttribute("title", "journal");
        model.addAttribute("threadModels", transactions);
        return "journal/list";
    }
    
    /*@RequestMapping(value="/updateJournal", method=RequestMethod.POST)
    public String updateExistingJournal(@RequestParam("workers") String workers, @RequestParam("measures") String measures, @RequestParam("images") String images,@RequestParam("images") Integer jobId, Model model) throws Exception {
    	if(jobId == null) {
            model.addAttribute("Error","Please enter job id!");
    	}
    	
    	if(workers.isEmpty() || measures.isEmpty() || images.isEmpty() ) {
            model.addAttribute("Error","Please enter worker, measure and image to update!");
    	}
    	transactions.get(jobId).updateJob(workers, measures, images, jobId);
        return "updateJournal/index";
    }*/
    
    @RequestMapping(value="/createJournal", method=RequestMethod.POST)
    public String createJournal(Principal principal, Model model, @RequestParam("measure") String measure, @RequestParam("images") ArrayList<String> images) throws Exception {
        boolean error = false;
        //Verifico esitsa la misura
        if(measure == null || measure.trim().equals(""))
        {
        	error = true;
            System.out.println("La misura è vuota");
        }
        
        //Verifico la lista delle immagini non sia vuota
        if(images.size() == 0)
        {
        	error = true;
            System.out.println("Le immagini non ci sono");
        }
                
        
        //Controllo correttezza delle immagini selezionate
        ArrayList<Image> DbImages = new ArrayList<Image>();
        
        for(String image:images) {
        	try {
            	DbImages.add(imageService.findByName(image));
        	}
        	catch(Exception e) {
        		//e.printStackTrace();
            	error = true;
            	throw new Exception("L'immagine non è sul database");
        	}
        }
        
        //Controllo degli hash
        for(Image image:DbImages) {
        	Path path = Paths.get(root + "/images/" + image.getName());
			byte[] bytes = Files.readAllBytes(path);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String hash = DatatypeConverter.printHexBinary(digest.digest(bytes));  
			
			Image img = imageService.findByName(image.getName());
			if(!hash.equals(img.getImage_hash())){
				//L'immagine sul server è diversa da quella salvata nel DB
	        	error = true;
				throw new Exception("L'immagine nel server non coindice con quella salvata nel database");
			}
        }
        
        Measure measureDB = new Measure();
        //Controlli sulle misure
        try {
        	measureDB = measureService.findByName(measure);
    	}
    	catch(Exception e) {
    		//e.printStackTrace();
        	error = true;
			throw new Exception("La misura non è sul database");
    	}
        
    	Path path = Paths.get(root + "/measures/" + measureDB.getName());
    	byte[] bytes = Files.readAllBytes(path);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		String hash = DatatypeConverter.printHexBinary(digest.digest(bytes));
		
		Measure ms = measureService.findByName(measureDB.getName());
		if(!hash.equals(ms.getMeasure_hash())){
			//L'immagine sul server è diversa da quella salvata nel DB
        	error = true;
			throw new Exception("La misura nel server non coindice con quella salvata nel database");
		}

        
        if (!error)
        {
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
            
            
            /*ArrayList<String> list_partecipanti = new ArrayList<String>();
            list_partecipanti.add("node1");
            list_partecipanti.add("node2");

            //Create comma separated string of participants from sorted list - this uniquely identifies a thread in the application ( participants field in ThreadModel )
            String threadParticipantsString = String.join(",",list_partecipanti); */
        	
        	//Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
        	ClientTransactionManager clientTransactionManager = new ClientTransactionManager(quorumConnection.getQuorum(), quorumConnection.getNodeAddress(), quorumConnection.getNodeKey(), privateFor, 100, 1000);

        	//Deploy the new thread contract. This returns a thread contract object
			Journal threadContract = Journal.deploy(quorumConnection.getQuorum(), clientTransactionManager, BigInteger.valueOf(0), BigInteger.valueOf(100000000)).send();
        	
        	//Extract contract address from thread contract object obtained in 2.d
        	String newThreadContractAddress = threadContract.getContractAddress();
        	
        	String listString = String.join(",", images);
        	
        	//Call the sendContractAddress event in thread contract to inform participants of new thread contract address and participants
        	TransactionReceipt startThreadTransactionReceipt = threadContract.addNewJob(measure, listString).send();
        }
        
        return "redirect:/journal/journals";
    }
    @Autowired
	public void setServices(ImageService imageService, MeasureService measureService) {
		this.imageService = imageService;
		this.measureService = measureService;
	}
}
