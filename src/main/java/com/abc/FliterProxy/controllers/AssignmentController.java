package com.abc.FliterProxy.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.abc.FliterProxy.beans.Assignment;
import com.abc.FliterProxy.beans.Board;
import com.abc.FliterProxy.http.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.var;



@RestController
@RequestMapping("/assignments")
public class AssignmentController {
	 @Autowired
	  private RestTemplate restTemplate;
	 
		@PostMapping
		public Response<String> addAssignement(@RequestBody Assignment assi) throws ParseException{
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		     HttpEntity<Assignment> entity = new HttpEntity<Assignment>(assi,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/assignments", HttpMethod.POST, entity, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<String> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<Assignment>>() {});
				} catch (JsonParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			return res;
		}
		
		@PutMapping
		public Response<String> update(@RequestBody Assignment assi){
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		     HttpEntity<Assignment> entity = new HttpEntity<Assignment>(assi,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/assignments", HttpMethod.PUT, entity, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<String> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<String>>() {});
				} catch (JsonParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			return res;
		}
		
		@DeleteMapping("/{id}")
		public Response<String> deleteAssignment(@PathVariable int id){
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/assignments/"+id, HttpMethod.DELETE, null, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<String> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<String>>() {});
				} catch (JsonParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			return res;
		}
		
		@GetMapping("/{id}")
		public Response<Assignment> getAssignment(@PathVariable int id){
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/assignments/"+id, HttpMethod.GET, null, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<Assignment> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<Assignment>>() {});
				} catch (JsonParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			return res;
		}
		
		@GetMapping
		public  Response<Assignment[]> getAllAssignment(){
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/assignments", HttpMethod.GET, null, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<Assignment[]> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<Assignment[]>>() {});
				} catch (JsonParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			return res;
		}
		
}
