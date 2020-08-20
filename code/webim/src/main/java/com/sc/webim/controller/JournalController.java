package com.sc.webim.controller;

import static org.web3j.tx.Contract.staticExtractEventParameters;

import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.abi.EventValues;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tuples.generated.Tuple3;

import com.sc.webim.connection.QuorumConnection;
import com.sc.webim.contracts.Journal;
import com.sc.webim.model.Job;
import com.sc.webim.model.JournalModel;


@Controller
@RequestMapping("/journal")
public class JournalController {
	private Map<Integer,Job> transactions = new HashMap<Integer, Job>();
	
	private Map<Integer, String> allData = new HashMap<Integer, String>();
	
    @Autowired
    QuorumConnection quorumConnection;

    private Map<String,String> allNodeNamesToPublicKeysMap = new HashMap<String, String>(){{
        put("node1","BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=");
        put("node2","QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=");
        put("node3","1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg=");
        put("node4","oNspPPgszVUFw0qmGFfWwh1uxVUXgvBxleXORHj07g8=");
        put("node5","R56gy4dn24YOjwyesTczYa8m5xhP6hF2uTMCju/1xkY=");
        put("node6","UfNSeSGySeKg11DVNEnqrUtxYRVor4+CvluI8tVv62Y=");
        put("node7","ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc=");
    }};

    @RequestMapping(method = RequestMethod.GET)
	public String images(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg, 
			@RequestParam(value = "resp", required = false) String resp) {
		model.addAttribute("title", "journal");
		
		return "journal/list";
	}

    @RequestMapping(value="/journals", method=RequestMethod.GET)
    public String showJournal(Model model) {
        String eventJobEventTopic = "0x4cf8037dff8f2e4212332ce6a37f5353c431bfc409fe36d824e7553dbaf66b86";
        JournalModel journalModel = new JournalModel();
        EthFilter filterToExtractNewJournals = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, Collections.emptyList()).addSingleTopic(eventJobEventTopic);

        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewJournals).subscribe(messageLog -> {
            //Extract sendContractAddress event parameters defined in Thread contract
            EventValues sendContractAddressEventValues = staticExtractEventParameters(Journal.EVENTJOB_EVENT , messageLog);

            //Create sendContractAddress event response object to store individual parameter values
            Journal.EventJobEventResponse eventJobEventResponse = new Journal.EventJobEventResponse();
            


            eventJobEventResponse.worker = (String)sendContractAddressEventValues.getNonIndexedValues().get(0).getValue();
            eventJobEventResponse.measure = (String)sendContractAddressEventValues.getNonIndexedValues().get(1).getValue();
            eventJobEventResponse.images = (ArrayList<String>)sendContractAddressEventValues.getNonIndexedValues().get(2).getValue();

            String worker = eventJobEventResponse.worker.toString();
            String measure = eventJobEventResponse.measure.toString();
            ArrayList<String> images = (ArrayList<String>) eventJobEventResponse.images;  //da ricontrollare

            //Create new ThreadModel instance to save new thread details - contract address, participants
            journalModel.addNewJob(worker, measure, images);
            
            int i=0;
            for(Job j:journalModel.getJobs()) {
                transactions.put(i,j);
                i++;
            }
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
        //Verifico essitsa la misura
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
        
        if (!error)
        {
        	//Scelgo i nodi
        	List<String> privateFor = new ArrayList<String>();
        	privateFor.add("BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=");
        	privateFor.add("QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=");
            
            
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
        	
        	//Call the sendContractAddress event in thread contract to inform participants of new thread contract address and participants
        	TransactionReceipt startThreadTransactionReceipt = threadContract.addNewJob(measure, images).send();
        }
        
        return "redirect:/journal/journals";
    }
}
