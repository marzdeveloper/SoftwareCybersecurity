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
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tuples.generated.Tuple3;

import com.sc.webim.connection.QuorumConnection;
import com.sc.webim.contracts.Journal;
import com.sc.webim.contracts.Thread;
import com.sc.webim.model.Job;
import com.sc.webim.model.JournalModel;
import com.sc.webim.model.ThreadModel;


@Controller
@RequestMapping("/journal")
public class JournalController {
	private ArrayList<Job> transactions = new ArrayList<Job>();
    private JournalModel journalModel = new JournalModel();
	private Map<Integer, String> allData = new HashMap<Integer, String>();
	private Map<String,ThreadModel> allThreads;
	
    @Autowired
    QuorumConnection quorumConnection;

    @RequestMapping(method = RequestMethod.GET)
	public String images(Locale locale, Model model, @RequestParam(value = "msg", required = false) String msg, 
			@RequestParam(value = "resp", required = false) String resp) {
		model.addAttribute("title", "journal");
		
		return "journal/list";
	}
    
    /* *************************************************************************************************************************************************************************** */
    @RequestMapping(value="/threads", method=RequestMethod.GET)
    public String showThreads(Model model) {
        allThreads = new HashMap<String, ThreadModel>();

        String sendContractAddressEventTopic = "0x4cf8037dff8f2e4212332ce6a37f5353c431bfc409fe36d824e7553dbaf66b86";
        EthFilter filterToExtractNewThreads = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, Collections.emptyList()).addSingleTopic(sendContractAddressEventTopic);

        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewThreads).subscribe(messageLog -> {
            EventValues sendContractAddressEventValues = staticExtractEventParameters(Thread.SENDCONTRACTADDRESS_EVENT, messageLog);

            Thread.SendContractAddressEventResponse sendContractAddressEventResponse = new Thread.SendContractAddressEventResponse();
            sendContractAddressEventResponse.participants = (String)sendContractAddressEventValues.getNonIndexedValues().get(0).getValue();
            sendContractAddressEventResponse.contractAddress = (String)sendContractAddressEventValues.getNonIndexedValues().get(1).getValue();

            ThreadModel threadModel = new ThreadModel(sendContractAddressEventResponse.contractAddress,sendContractAddressEventResponse.participants);
            allThreads.put(sendContractAddressEventResponse.participants,threadModel);
        });


        String sendMessageEventTopic = "0x0eabeffe119b8ffbb23292e86677821e520cbaeb5401f69cb0d565b69fae8e6f";
        EthFilter filterToExtractNewMessagesFromExistingThreads = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,Collections.emptyList()).addSingleTopic(sendMessageEventTopic);

        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewMessagesFromExistingThreads).subscribe(messageLog -> {
            EventValues messageEventValues = staticExtractEventParameters(Thread.SENDMEASURE_EVENT, messageLog);

            Thread.SendMeasureEventResponse measureTypedResponse = new Thread.SendMeasureEventResponse();
            measureTypedResponse.autor = (String) messageEventValues.getNonIndexedValues().get(0).getValue();
            measureTypedResponse.image = (String) messageEventValues.getNonIndexedValues().get(1).getValue();
            measureTypedResponse.measure = (String) messageEventValues.getNonIndexedValues().get(2).getValue();

            Map<String,String> measureSenderMap = new HashMap<String, String>();
            measureSenderMap.put(measureTypedResponse.measure,measureTypedResponse.image);
            allThreads.get(measureTypedResponse.autor).updateThreadMessages(measureSenderMap);
        });

        model.addAttribute("threadModels", allThreads);
        return "journal/list";
    }
    
    @RequestMapping(value="/threads", method=RequestMethod.POST)
    public String createNewThread(@RequestParam("message") String message,@RequestParam("threadParticipants") ArrayList<String> threadParticipants, Model model) throws Exception {
        if(!threadParticipants.contains(quorumConnection.getNode()))
        {
            model.addAttribute("Error","Please include " + quorumConnection.getNode() + " in participants!");
            return showThreads(model);
        }

        if(message.trim().equals("") || (message == null))
        {
            model.addAttribute("Error","Please enter a message!");
            return showThreads(model);
        }

        List<String> privateFor = new ArrayList<String>();
        for(String threadParticipant : threadParticipants)
            privateFor.add(allNodeNamesToPublicKeysMap.get(threadParticipant));

        Collections.sort(threadParticipants);
        String threadParticipantsString = String.join(",",threadParticipants);

        if(allThreads.get(threadParticipantsString)!=null)
        {
            model.addAttribute("Error","Thread already exists!");
            return showThreads(model);
        }

        ClientTransactionManager clientTransactionManager = new ClientTransactionManager(quorumConnection.getQuorum(), quorumConnection.getNodeAddress(), quorumConnection.getNodeKey(), privateFor, 100, 1000);
        Thread threadContract = Thread.deploy(quorumConnection.getQuorum(), clientTransactionManager, BigInteger.valueOf(0), BigInteger.valueOf(100000000)).send();
        String newThreadContractAddress = threadContract.getContractAddress();
        TransactionReceipt startThreadTransactionReceipt = threadContract.startThread(threadParticipantsString,newThreadContractAddress).send();
        TransactionReceipt sendMessageTransactionReceipt = threadContract.sendMessageToThread(message, quorumConnection.getNode()).send();

        return showThreads(model);
    }
    /* *************************************************************************************************************************************************************************** */
    
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
}
