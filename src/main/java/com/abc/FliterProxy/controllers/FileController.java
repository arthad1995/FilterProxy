package com.abc.FliterProxy.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.var;



@RestController
@RequestMapping("/files")
public class FileController {
	 @Autowired
	  private RestTemplate restTemplate;
	 
	 @GetMapping("/{id}")
		public ResponseEntity<Resource> DownloadFile(@PathVariable int id) throws Exception{
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/files/" +id, HttpMethod.GET, null, byte[].class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return null;
			 }
		     byte[] arr = reponse.getBody();
		     System.out.println(arr.length);
		    // System.out.println(s);
		     ResponseEntity<Resource> res = null;
		     
		     ByteArrayResource resource = new ByteArrayResource(arr);

			    return ResponseEntity.ok()
			        .headers(reponse.getHeaders())
			        .contentLength(arr.length)
			        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
		}
}
