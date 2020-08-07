pragma solidity ^0.4.24;  /*specifico versione */
pragma experimental ABIEncoderV2; /*aggiunto per poter stampare la struct, togliere dopo il debug */

contract Journal{
   

  struct Job{
    bytes32   []workers;
    bytes32   []measures;
    bytes32   []images;
    uint jobID;   
    }
    
    bytes32   []  worker;
    bytes32   []  measure;
    bytes32   []  image;

    uint numOfJob = 0;
    Job[] public jobs;
  
    /*aggiungo un nuovo lavoro alla lista dei lavori */
    function addNewJob(string memory _worker, string memory _measure, string memory _image)  public {
       

        
        worker.push(sha256(abi.encodePacked(_worker)));
        measure.push(sha256(abi.encodePacked(_measure)));
        image.push(sha256(abi.encodePacked(_image)));
        
        Job memory job = Job(worker,measure,image,numOfJob);   
        jobs.push(job);
        
        
        numOfJob++;
        
        
    }
    
    /*aggiungo una nuova immagine alla lista dei lavori*/
    function updateJob(uint _jobID, string memory _measure, string memory _image, string memory _worker) {
        for(uint i = 0;i< numOfJob;i++){
                if(jobs[i].jobID==_jobID){
                    jobs[i].measures.push(sha256(abi.encodePacked(_measure)));
                    jobs[i].images.push(sha256(abi.encodePacked(_image)));
                    jobs[i].workers.push(sha256(abi.encodePacked(_worker)));

                    
                }    
        

    }
    }
    
        /* prendo tutti i dati relativi a un job */

    function getJobById(uint _id) view public returns(uint,bytes32[],bytes32[]){
        return (jobs[_id].jobID,jobs[_id].measures,jobs[_id].images);
    }

    
    function getAllJobs() view public returns(Job[] memory){
        return jobs;
    }
    
    
     function getJobByMeasure(bytes32 _measure) view public returns(uint,bytes32[],bytes32[]){
         for(uint i = 0;i< jobs.length;i++){
             for(uint k=0; k< jobs[i].measures.length;k++){
                 if(jobs[i].measures[k]==_measure){
                    return(jobs[i].jobID, jobs[i].workers, jobs[i].images);        
                }       
                 
             }
             
         }
    }
    
    
   
    
            
    function getJobByImage(bytes32 _image) view public returns(uint, bytes32[] ,bytes32[] ){
        for(uint i = 0;i< jobs.length;i++){
            for(uint j =0; j<jobs[i].images.length;j++)
                if(jobs[i].images[j]==_image){
                    return(jobs[i].jobID, jobs[i].workers,jobs[i].measures );        
                }       
    
            
        }
        
    
            }       

    
    
    
    
    
    
}


