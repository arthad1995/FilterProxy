package com.abc.FliterProxy.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.abc.FliterProxy.beans.Assignment;
import com.abc.FliterProxy.beans.Submission;
import com.abc.FliterProxy.beans.UploadFile;
import com.abc.FliterProxy.beans.User;
import com.abc.FliterProxy.http.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.var;



@RestController
@RequestMapping("/submissions")
public class SubmissionController {
	 @Autowired
	  private RestTemplate restTemplate;
	 
		@PostMapping
	public Response<String> submit(  @RequestParam("submission") String sub, @RequestParam("file") MultipartFile file){

			System.out.println(sub);
			System.out.println("hererere");
			 HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			 MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
			 map.add("submission", sub);
			// map.add("file", file);
			 map.add("file", new FileSystemResource(convert(file)));
			 HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
			 var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions", HttpMethod.POST, entity, String.class);
		
		      
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
		
		
		@GetMapping("/assignment/studentSubmissions/{id}")
		public Response<Submission[]> allByStudentSubmission(@PathVariable int id) throws Exception{
			
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions//assignment/studentSubmissions/"+id, HttpMethod.GET, null, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<Submission[]> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<Submission[]>>() {});
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
		public ResponseEntity<Resource> submissionDownload(@PathVariable int id) throws Exception{
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions/" +id, HttpMethod.GET, null, byte[].class);
		      
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
		
		
		@GetMapping("assignment/{id}")
		public ResponseEntity<Resource> assignmentSubmissionDownload(@PathVariable int id) throws Exception{
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions/assignment/" +id, HttpMethod.GET, null, byte[].class);
		      
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
		
		@GetMapping("history/{id}")
		public Response<UploadFile[]> previousSubmission(@PathVariable int id) throws Exception{
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions/history/"+id, HttpMethod.GET, null, String.class);
		      
		 	 if(reponse.getStatusCodeValue()==400) {
				 return new Response<>(false);
			 }
		      String s  = reponse.getBody();
		      
		     System.out.println(s);
		     Response<UploadFile[]> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<UploadFile[]>>() {});
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
		
		@PostMapping("/grade")
		public Response<String> gradeAssignment(@RequestBody Submission sub){
			System.out.println(sub);
			 HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		     HttpEntity<Submission> entity = new HttpEntity<Submission>(sub,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions/grade", HttpMethod.POST, entity, String.class);
		     
		      String s  = reponse.getBody();
		      System.out.println(s);
		      System.out.println( s.substring(s.indexOf("\"message\"")+10, s.indexOf("\"payload\"")-1));
		 	 if(s.charAt(11)=='f') {
				 return errorHandle(s);
			 }

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
		
		
		@PostMapping("/uploads")
		public Response<String> upload(@RequestParam("submission") String sub, @RequestParam("file") MultipartFile file){
			 HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			 MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
			 map.add("submission", sub);
			// map.add("file", file);
			 map.add("file", new FileSystemResource(convert(file)));
			 HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);
			 var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions/uploads", HttpMethod.POST, entity, String.class);
		
			  String s  = reponse.getBody();
			  if(s.charAt(11)=='f') {
					 return errorHandle(s);
				 }

		    
		      
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
		
		
		
		@GetMapping("/uploads")
		public Response<UploadFile[]> getAllPublicUploadedFiles(){
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions/uploads", HttpMethod.GET, null, String.class);
		     
		      
		      String s  = reponse.getBody();
		      if(s.charAt(11)=='f') {
					 return new Response<>(false,getMessage(s),null);
				 }
		    
		      
		   
		      
		     Response<UploadFile[]> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<UploadFile[]>>() {});
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
		public Response<Submission[]> getAllSubmissions(){
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions", HttpMethod.GET, null, String.class);
		     
		      
		      String s  = reponse.getBody();
		      if(s.charAt(11)=='f') {
					 return new Response<>(false,getMessage(s),null);
				 }
		    
		      
		   
		      
		     Response<Submission[]> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<Submission[]>>() {});
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
		
		
		@GetMapping("/fileDeatil/{id}")
		public Response<UploadFile> submissionfileDetailDownload(@PathVariable int id) throws Exception{
			HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
		      var reponse= restTemplate.exchange(
		    	         "http://localhost:8082/submissions/fileDeatil/"+id, HttpMethod.GET, null, String.class);
		     
		      
		      String s  = reponse.getBody();
		      if(s.charAt(11)=='f') {
					 return new Response<>(false,getMessage(s),null);
				 }
		    
		      
		   
		      
		     Response<UploadFile> res = null;
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					res = objectMapper.readValue(s, new TypeReference<Response<UploadFile>>() {});
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
		
		
		
		
		
		
		
		private static Response<String> errorHandle(String s){
			
			String message = s.substring(s.indexOf("message")+9, s.indexOf("payload")-2);
			String payload = s.substring(s.indexOf("payload")+10, s.length()-2);
			return new Response<String>(false,message,payload);
		}
		
		
		private static String getPayload(String s) {
			return s.substring(s.indexOf("payload")+10, s.length()-2);
		}
		private static String getMessage(String s) {
			return s.substring(s.indexOf("message")+9, s.indexOf("payload")-2);
		}
		
		  public static File convert(MultipartFile file)
		  {    
		    File convFile = new File(file.getOriginalFilename());
		    try {
		        convFile.createNewFile();
		          FileOutputStream fos = new FileOutputStream(convFile); 
		            fos.write(file.getBytes());
		            fos.close(); 
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } 

		    return convFile;
		 }
}
