package com.sc.webim.controller;

import static org.web3j.tx.Contract.staticExtractEventParameters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.abi.EventValues;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.tx.ClientTransactionManager;

import com.sc.webim.connection.QuorumConnection;

import org.web3j.model.Journal;

import com.sc.webim.model.JournalModel;
public class JournalController {
	
	//QuorumConnection defines all necessary parameters for application to connect with its respective node in Quorum
    @Autowired
    QuorumConnection quorumConnection;

    //Create map of all nodes names on Quorum along to the public keys of their respective accounts
    private Map<String,String> allNodeNamesToPublicKeysMap = new HashMap<String, String>(){{
        put("node1","BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=");
        put("node2","QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=");
        put("node3","1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg=");
        put("node4","oNspPPgszVUFw0qmGFfWwh1uxVUXgvBxleXORHj07g8=");
        put("node5","R56gy4dn24YOjwyesTczYa8m5xhP6hF2uTMCju/1xkY=");
        put("node6","UfNSeSGySeKg11DVNEnqrUtxYRVor4+CvluI8tVv62Y=");
        put("node7","ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc=");
    }};

    //Map to hold thread participants string with corresponding ThreadModel instance - holds all thread data
    private Map<Integer,JournalModel> allJournals = new HashMap<Integer, JournalModel>();

    /*Display all threads that this node is a participant in:
    1. Subscribe to and extract all new thread events from Quorum (event sendContractAddress in thread contract)
    2. Create ThreadModel instances to persist extracted event parameters in application - contract address and participants
    3. Subscribe to and extract all send message events from Quorum (event sendMessage in thread contract)
    4. Persist event parameters in ThreadModel instances. ThreadModel object is identified based on participants returned by sendMessage event
    */
    @RequestMapping(value="/journals", method=RequestMethod.GET)
    public String createNewJournal(Model model) {


        //Keccak hash of sendContractAddress event signature defined in thread contract
        String eventJobEventTopic = "0x4cf8037dff8f2e4212332ce6a37f5353c431bfc409fe36d824e7553dbaf66b86";
        JournalModel journalModel = new JournalModel();

        
        //Create filter to extract out sendContractAddress events from all Thread contracts
        //Keccak hash of event signature is provided as a topic to filter out specifically sendContractAddress events only i.e new threads
        EthFilter filterToExtractNewJournals = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, Collections.emptyList()).addSingleTopic(eventJobEventTopic);

        //Subscribe to all sendContractAddress events based on filter created above and read log of each event
        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewJournals).subscribe(messageLog -> {

            //Extract sendContractAddress event parameters defined in Thread contract
            EventValues sendContractAddressEventValues = staticExtractEventParameters(Journal.EVENTJOB_EVENT , messageLog);

            //Create sendContractAddress event response object to store individual parameter values
            Journal.EventJobEventResponse eventJobEventResponse = new Journal.EventJobEventResponse();
            
            eventJobEventResponse.worker = (List)sendContractAddressEventValues.getNonIndexedValues().get(0).getValue();
            eventJobEventResponse.measure = (List)sendContractAddressEventValues.getNonIndexedValues().get(1).getValue();
            eventJobEventResponse.image = (List)sendContractAddressEventValues.getNonIndexedValues().get(2).getValue();

            String worker = eventJobEventResponse.worker.toString();
            String measure = eventJobEventResponse.measure.toString();
            String image = eventJobEventResponse.image.toString();

            //Create new ThreadModel instance to save new thread details - contract address, participants
            
            journalModel.addNewJob(worker, measure, image, journalModel.getJobGenerated());
            
            
            allJournals.put(journalModel.getJobGenerated(),journalModel);
            

        });

        
        return("new journal has been generated, job code: "+journalModel.getJobGenerated());

    }
    
    
   
    
    /*Handle update thread request
    1. Data received from update thread form (threads.html)
        1.a New message
        1.b Thread participants
        1.c Contract Address of thread
    2. Validation
        2.a Check if message is empty
    3. Create a sendMessage event to be sent to all thread participants
        3.a Create list of thread participants public keys to be used as privateFor parameter.
        3.b Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
        3.c Load the thread contract based on contract address
        3.d Call the sendMessage event in the contract to inform participants of new message in this thread. Parameters sent - participants, message, sender
     */
    @RequestMapping(value="/updateJournal", method=RequestMethod.POST)
    public String updateExistingJournal(@RequestParam("workers") String workers, @RequestParam("measures") String measures, @RequestParam("images") String images,@RequestParam("images") Integer jobId, Model model) throws Exception {

    	if(jobId == null) {
    		
            model.addAttribute("Error","Please enter job id!");
    		
    	}
    	
    	if(workers.isEmpty() || measures.isEmpty() || images.isEmpty() ) {
    		
            model.addAttribute("Error","Please enter worker, measure and image to update!");
    		
    	}
    	
    	allJournals.get(jobId).updateJob(workers, measures, images, jobId);
    	

        return "updateJournal/index";
    }
    
    
    
    
    
    
    
    

}
