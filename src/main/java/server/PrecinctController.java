/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author webst
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
 
import org.springframework.web.bind.annotation.*;
 
//This annotation marks the class as a request handler and maps the request sent from the client to a method in the server
@RestController
public class PrecinctController {
    //The Autowired annotation injects the constructor of PrecinctRepo into this class and that object has the methods from PrecinctRepo and JPA Repo
    @Autowired
    private PrecinctRepo service;
    
    // RESTful API methods for Retrieval operations
    @GetMapping("/precincts")
    public List<Precinct> findAllPrecincts() {
        List<Precinct> allPrecincts=service.findAll();
        for(Precinct p: allPrecincts){
            p.setDemographic(null);
            p.setError(null);
            p.setElection(null);
        }
        return allPrecincts;
    }
    
    @GetMapping("/precincts/search/{partOfName}/{statefp}")
    public ResponseEntity<List<String>> findAllNamesForSearch(@PathVariable String partOfName,@PathVariable String statefp){
        List<Precinct> precincts=service.findByNameStartingWithIgnoreCaseAndStatefp(partOfName,statefp);
        List<String> precinctNames=new ArrayList<String>();
        int maxCount=0;
        for(Precinct p: precincts){
            if(maxCount<5){
                precinctNames.add(p.getName());
                maxCount++;
            }
        }
        if(precinctNames.size()==0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(precinctNames,HttpStatus.FOUND);
    }
    
    @GetMapping("/precincts/error/{statefp}")
    public List<Precinct> findErrorsForPrecincts(@PathVariable String statefp){
        List<Precinct> precincts=service.findByStatefpAndErrorIsNotNull(statefp);
        for(Precinct p:precincts){
            p.setShape_geojson(null);
            p.setDemographic(null);
            p.setElection(null);
        }
        return precincts;
    }
    
    @GetMapping("/precincts/demographic/{id}")
    public Demographic findDemographicForPrecincts(@PathVariable String id){
        Precinct precinct=service.findByOgrFID(Integer.parseInt(id));
        return precinct.getDemographic();
    }
    
    @GetMapping("/precincts/election/{id}")
    public Election findElectionForPrecincts(@PathVariable String id){
        Precinct precinct=service.findByOgrFID(Integer.parseInt(id));
        return precinct.getElection();
    }
    
    @GetMapping("/precincts/{id}")
    public List<Precinct> findPrecinctsInState(@PathVariable String id){
        return service.findByStatefp(id);
    }
    
    // RESTful API method for Update operation
    @PutMapping("/precincts/{id}")
    public void update(@RequestBody Precinct precinct, @PathVariable String id) {
        try {
            Precinct existPrecinct = service.findByOgrFID(Integer.parseInt(id));
            precinct.setOgrFID(Integer.parseInt(id));
            if(precinct.getDemographic()==null){
                precinct.setDemographic(existPrecinct.getDemographic());
            }
            if(precinct.getElection()==null){
                precinct.setElection(existPrecinct.getElection());
            }
            if(precinct.getError()==null){
                precinct.setError(existPrecinct.getError());
            }
            else{
                if(precinct.getError().getCommentTime()==null){
                    precinct.getError().setCommentTime(new Timestamp(System.currentTimeMillis()));
                }
            }
            service.save(precinct);
        } catch (NoSuchElementException e) {
        }      
    }
    
    // RESTful API method for Create operation
    @PostMapping("/precincts")
    public void add(@RequestBody Precinct precinct) {
        System.out.println("hi from java");
        System.out.println(precinct);
        //service.save(precinct);
    }
    
    @Transactional
    @PutMapping("/merge/{name1}/{name2}")
    public ResponseEntity<?> merge(@RequestBody Precinct newPrecinct,@PathVariable String enclosingPrecinctID, @PathVariable String enclosedPrecinctID){
        //TO-DO
        try {
            //delete precinct 1
            //delete precinct 2
            //add new precinct
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  
    }
    
    // RESTful API method for Delete operation
    @DeleteMapping("/precincts/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}