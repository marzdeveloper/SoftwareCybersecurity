pragma solidity >=0.6.0;  /*specifico versione */

contract Journal{

  struct Job{
    address   worker;
    string   measure;
    string   images;
    uint jobID;   
    }
    
    event eventJob(
        address  worker,
        string  measure,
        string   images,
        uint jobId   
        );
    
    address   worker;
    uint numOfJob = 0;
    Job[]  public jobs;
    
    
    
    constructor() public {
    	worker = msg.sender;
    }
    
    
    
    function addNewJob(string memory measure, string memory images) public {
    	worker = msg.sender;
    	Job memory job = Job(worker,measure,images,numOfJob);   
    	jobs.push(job);
        emit eventJob(jobs[numOfJob].worker ,jobs[numOfJob].measure, jobs[numOfJob].images, numOfJob);
        numOfJob++;
    }
  
    /*aggiungo un nuovo lavoro alla lista dei lavori 
    function addNewJob(string memory _worker, string memory _measure, string memory _image)  public {
       

        
        worker.push(sha256(abi.encodePacked(_worker)));
        measure.push(sha256(abi.encodePacked(_measure)));
        image.push(sha256(abi.encodePacked(_image)));
        
        Job memory job = Job(worker,measure,image,numOfJob);   
        jobs.push(job);
        
        
        emit eventJob(jobs[numOfJob].workers ,jobs[numOfJob].measures, jobs[numOfJob].images, numOfJob);
        
        numOfJob++;
        
        
    } */
    
    /*aggiungo una nuova immagine alla lista dei lavori
    function updateJob(uint _jobID, string memory _measure, string memory _image, string memory _worker) public{
        for(uint i = 0;i< numOfJob;i++){
                if(jobs[i].jobID==_jobID){
                    jobs[i].measures.push(sha256(abi.encodePacked(_measure)));
                    jobs[i].images.push(sha256(abi.encodePacked(_image)));
                    jobs[i].workers.push(sha256(abi.encodePacked(_worker)));

                    emit eventJob(jobs[i].workers ,jobs[i].measures, jobs[i].images, numOfJob);


                    
                }    
        

    }
    } */
    
        /* prendo tutti i dati relativi a un job */

    function getJobById(uint _id) view public returns(uint ,string memory,string memory){
        return (jobs[_id].jobID,jobs[_id].measure,jobs[_id].images);
    }

    /*
    function getAllJobs() view public returns(Job[] memory){
        return jobs;
    }
    */
    
    
    /*
     function getJobByMeasure(bytes32 _measure) view public returns(uint,bytes32[],bytes32[]){
         for(uint i = 0;i< jobs.length;i++){
             for(uint k=0; k< jobs[i].measures.length;k++){
                 if(jobs[i].measures[k]==_measure){
                    return(jobs[i].jobID, jobs[i].workers, jobs[i].images);        
                }       
                 
             }
             
         }
    }  */
    
    
   
    
            /*
    function getJobByImage(bytes32 _image) view public returns(uint, bytes32[] ,bytes32[] ){
        for(uint i = 0;i< jobs.length;i++){
            for(uint j =0; j<jobs[i].images.length;j++)
                if(jobs[i].images[j]==_image){
                    return(jobs[i].jobID, jobs[i].workers,jobs[i].measures );        
                }       
    
            
        }
        
    
            }       */

    
    
    
    
    
    
}
