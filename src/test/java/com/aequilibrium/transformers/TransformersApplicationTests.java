package com.aequilibrium.transformers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

import com.aequilibrium.transformers.data.Transformer;
import com.aequilibrium.transformers.util.TransformerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;


/**
 * @author Abhay
 * The Springboot test class for Transformer application
 */

@SpringBootTest
class TransformersApplicationTests {
	private final static Logger log = LoggerFactory.getLogger(TransformersApplicationTests.class);
	
	/**
	 *  Rest template 
	 */
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	public String getHost() {
		return "http://localhost:80";
	}
	public HttpHeaders getHeaders() {
		   HttpHeaders headers = new HttpHeaders();
		   headers.setContentType(MediaType.APPLICATION_JSON);
		   return headers;
	}
	public String getJson(Object obj) throws Exception{
		ObjectMapper objMapper = new ObjectMapper();
		   return objMapper.writeValueAsString(obj);
		   
	}
	
	/** the test for create transformer
	 * @throws Exception
	 */
	@Test
	public void testCreateTransformer() throws Exception {
		
		   String uri = getHost()+"/transformer/create";
		   
		   HttpEntity<String> requestEntity = new HttpEntity<>(getJson(getTransformersList()),getHeaders());
		   
		   ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		   assertEquals(response.getStatusCode(),HttpStatus.OK);
		   assertTrue((response.getBody().contains("Transformer Created Successfully")));

	}
	
	/**the test for update transformer
	 * @throws Exception
	 */
	@Test
	public void testUpdateTransformer() throws Exception {
		   String uri = getHost()+"/transformer/update";
		   
		   HttpEntity<String> requestEntity = new HttpEntity<>(getJson(getTransformerToUpdate()),getHeaders());
		   
		   ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		   assertEquals(response.getStatusCode(),HttpStatus.OK);
			assertTrue((response.getBody().contains("Transformer Updated Successfully")));
		
	}
	
	/**the test for delete transformer
	 * @throws Exception
	 */
	@Test
	public void testDeleteTransformer() throws Exception {
			String transformerId = "5";
			String uri = getHost()+"/transformer/delete/"+ transformerId;
		   
		   HttpEntity<String> requestEntity = new HttpEntity<>(null,getHeaders());
		   ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		   assertEquals(response.getStatusCode(),HttpStatus.OK);		   
			assertTrue((response.getBody().contains("Transformer Deleted Successfully")));
		
	}

	/**test for getting transformers list
	 * @throws Exception
	 */
	@Test
	public void testGetTransformersList() throws Exception {
			String uri = getHost()+"/transformer/list";
		   
		   
		   HttpEntity<String> requestEntity = new HttpEntity<>(null,getHeaders());
		   
		   ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		   assertEquals(response.getStatusCode(),HttpStatus.OK);
			assertNotNull(response.getBody());
		
	}

	/**test for determining winning team
	 * @throws Exception
	 */
	@Test
	public void testDetermineWinningTeam() throws Exception {
			String transformerIDs = "1,2,3";
			String uri = getHost()+"/transformer/determineWiningTeam/"+ transformerIDs;
		   
		   
		   HttpEntity<String> requestEntity = new HttpEntity<>(null,getHeaders());
		   
		   ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
		   assertEquals(response.getStatusCode(),HttpStatus.OK);
		   assertNotNull(response.getBody());
		
	}
	
	/** data bean of transformer
	 * @return
	 */
	public Transformer getTransformerToUpdate() {
		Transformer t1 = new Transformer(4,"Hubcap","A",4,4,4,4,4,4,4,4);
		return t1;
	}
	
	/** data list of transformers
	 * @return
	 */
	public List getTransformersList() {
		List list = new ArrayList();
		Transformer t1 = new Transformer(1,"Soundwave","D", 8,9,2,6,7,5,6,10);
		Transformer t2 = new Transformer(2,"Bluestreak", "A", 6,6,7,9,5,2,9,7);
		Transformer t3 = new Transformer(3,"Hubcap","A",3,3,3,3,3,3,3,3);
//		Transformer t4 = new Transformer(4,"Hubcap","A",4,4,4,4,4,4,4,4); // used to update the values of the transformer
		Transformer t5 = new Transformer(5,"Hubcap","A",5,5,5,5,5,5,5,5);

		list.add(t1);
		list.add(t2);
		list.add(t3);
//		list.add(t4);
		list.add(t5);
		
		return list;
		
	}
		
}
