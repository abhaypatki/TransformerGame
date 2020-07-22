package com.aequilibrium.transformers;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aequilibrium.transformers.data.Transformer;
import com.aequilibrium.transformers.service.TransformerService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Abhay
 * Game of Transformers Spring Boot Application with Restful API to create, update, delete, list transformers
 * and determine the winning team between Autobots and Decepticons team. 
 */

@SpringBootApplication
@RestController
public class TransformersController {
	/**
	 * Logger for the application
	 */
	private final static Logger log = LoggerFactory.getLogger(TransformersController.class);
	
	/**
	 *  Transformer Service
	 */
	@Autowired
	TransformerService service;

	/**
	 * SpringBoot application start 
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("In TransformersApplication..." );
		
		SpringApplication.run(TransformersController.class, args);
	}


	/**
	 * @param transformer
	 * @return The method to create transformer
	 * @throws Exception
	 */
	@PostMapping(path = "/transformer/create")
	public ResponseEntity<String> createTransformer(final @RequestBody  Transformer []transformer) throws Exception{
		log.info("In createTransformer() method...");
		
		if (service.createTransformer(transformer)) {
			log.info("Transformer Created Successfully");
			return new ResponseEntity<String>( "Transformer Created Successfully", HttpStatus.OK);
		} else {
			log.info("Transformer Creation Failed");
			return new ResponseEntity<String>( "Transformer Creation Failed", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * method to get all the transformers list
	 * @return
	 * @throws Exception
	 */
	@GetMapping(path = "/transformer/list")
	public  ResponseEntity<String>  getTransformersList() throws Exception {
		log.info("In getTransformersList()...");
		
		ObjectMapper json = new ObjectMapper();
		List list = service.getTransformersList();
		
		String response = json.writeValueAsString(list) ;
		log.info("In getTransformersList()...Response:"+ response);
		
		return new ResponseEntity<String>( response, HttpStatus.OK);
	}

	/**
	 * method to delete the transformer based on transformer id
	 * @param transformerId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(path = "/transformer/delete/{transformerId}")
	public  ResponseEntity<String>  deleteTransformer(@PathVariable int transformerId) throws Exception {
		log.info("In deleteTransformer()...");
		service.deleteTransformer(transformerId);
		log.info("Transformer Deleted Successfully");
		return new ResponseEntity<String>( "Transformer Deleted Successfully", HttpStatus.OK);
	}

	/**
	 * method to update the transformer details
	 * @param newTransformer
	 * @return
	 * @throws Exception
	 */
	@PostMapping(path = "/transformer/update")
	public  ResponseEntity<String>  updateTransformer(final @RequestBody Transformer newTransformer) throws Exception{
		log.info("In updateTransformer()...");
		
		if(service.updateTransformer(newTransformer)) {
			log.info("Transformer Updated Successfully");
			return new ResponseEntity<String>( "Transformer Updated Successfully", HttpStatus.OK); 
		}
		log.info("Transformer Update Failed");
		return new ResponseEntity<String>( "Transformer Update Failed", HttpStatus.BAD_REQUEST);
	} 
	

	/**
	 * method to determine the winning team for the battle between Autobots and Decepticons teams
	 * @param transformersIDList
	 * @return
	 * @throws Exception
	 */
	@GetMapping(headers = "Content-type=text/plain", path = "/transformer/determineWiningTeam/{transformersIDList}" )
	public  ResponseEntity<String>  determineWiningTeam(@PathVariable ArrayList<Integer> transformersIDList) throws Exception {
		log.info("In determineWiningTeam()...");
		
		System.out.println(transformersIDList.toString());
		StringBuffer lsteam = new StringBuffer();
		StringBuffer wteam = new StringBuffer();
		
		HashMap result = service.determineWiningTeam(transformersIDList);
		((List)result.get("loosingSurvivors")).stream().forEach(k-> lsteam.append(k+" "));
		((List)result.get("winners")).forEach( k -> wteam.append(k + " 	"));
		
		String resultStr = String.format(" %s battles \n Winning Team (%s): %s \n Survivors from the losing team (%s): %s", 
				result.get("numBattles"), result.get("winningTeam"), wteam, result.get("loosingTeam"), lsteam  );
		
		log.info("Determine Winning Team Response:"+ resultStr);
		
		return new ResponseEntity<String>( resultStr, HttpStatus.OK);
	}
	
}