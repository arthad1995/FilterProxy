package com.abc.FliterProxy.controllers;

import java.io.IOException;
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

import com.abc.FliterProxy.beans.Board;
import com.abc.FliterProxy.beans.Reply;
import com.abc.FliterProxy.http.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.var;



@RestController
@RequestMapping("/replys")
public class ReplyController {
	 @Autowired
	  private RestTemplate restTemplate;
	 @PostMapping
		public Response<String> postReply(@RequestBody Reply reply){
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     HttpEntity<Reply> entity = new HttpEntity<Reply>(reply,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8083/replys", HttpMethod.POST, entity, String.class);
	      
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
	 
	 
	 @PutMapping
		public Response<String> modifyReply(@RequestBody Reply reply){
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     HttpEntity<Reply> entity = new HttpEntity<Reply>(reply,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8083/replys", HttpMethod.PUT, entity, String.class);
	      
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
		public Response<String> deleteReply(@PathVariable int id){
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8083/replys/"+id, HttpMethod.DELETE, null, String.class);
		      
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
		public Response<Reply[]> getBoardReply(@PathVariable int id){
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8083/replys/"+id, HttpMethod.GET, null, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<Reply[]> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<Reply[]>>() {});
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
