package com.abc.FliterProxy.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.abc.FliterProxy.Daos.UserDao;
import com.abc.FliterProxy.beans.CurrentUser;
import com.abc.FliterProxy.beans.Profile;
import com.abc.FliterProxy.beans.User;
import com.abc.FliterProxy.http.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.var;

@RestController
@RequestMapping("/users")
public class UserController {
	
	 @Autowired
	  private RestTemplate restTemplate;
	 
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao ud;
	
	@PostMapping("/register")
	public Response<String> register(@RequestBody User user) {
		System.out.println(user);
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users/register", HttpMethod.POST, entity, String.class);
	      
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
	
	
	
	@PostMapping("/login")
	public Response<User> login(@RequestBody User user){
		System.out.println("dsdasdasdasdsad");
		System.out.println("this is user name ====================================="+user.getUsername());
		User u = ud.findByUsername(user.getUsername());
	
	if(u==null)
		return new Response<>(false);
		if(!u.getPassword().equals(passwordEncoder.encode(user.getPassword()))) {
		
			return new Response<>(false);
		}
		
			
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		 MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		 map.add("username", user.getUsername());
		 map.add("password", user.getPassword());
		 HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		 var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/login", HttpMethod.POST, entity, String.class);
		 System.out.println(reponse.getStatusCode());
		 if(reponse.getStatusCodeValue()==400) {
			 return new Response<>(false);
		 }
		 String s = reponse.getBody();
	   
	 	Response<Profile[]> res = null;
		try {
			res = new ObjectMapper().readValue(s, new TypeReference<Response<Profile[]>>() {});
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
		CurrentUser  currentUser =CurrentUser.getInstance();
		currentUser.setId(u.getId());
		currentUser.setEmail(u.getEmail());
		currentUser.setName(u.getUsername());

		currentUser.setProfile(res.getPayload()[0]);
		currentUser.setUsername(u.getUsername());
		
	//	u.setProfile(res.getPayload()[0]);
		
		return new Response<User>(true,u) ;
	}
	
	
	// login user
	@GetMapping
	public Response<User[]> getAll() {
		
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users", HttpMethod.GET, null, String.class);
	      
	 	 if(reponse.getStatusCodeValue()==400) {
			 return new Response<>(false);
		 }
	      String s  = reponse.getBody();
	      
	     System.out.println(s);
	     Response<User[]> res = null;
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				res = objectMapper.readValue(s, new TypeReference<Response<User[]>>() {});
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
	
	@GetMapping("/id/{id}")
	public Response<User> getUserById(@PathVariable int id) {
	
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users/id/"+id, HttpMethod.GET, null, String.class);
	      
	 	 if(reponse.getStatusCodeValue()==400) {
			 return new Response<>(false);
		 }
	      String s  = reponse.getBody();
	      
	     System.out.println(s);
	     Response<User> res = null;
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				res = objectMapper.readValue(s, new TypeReference<Response<User>>() {});
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
	
	
	@GetMapping("/name/{name}")
	public Response<User> getUserByName(@PathVariable String name) {

		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users/name/"+name, HttpMethod.GET, null, String.class);
	      
	 	 if(reponse.getStatusCodeValue()==400) {
			 return new Response<>(false);
		 }
	      String s  = reponse.getBody();
	      
	     System.out.println(s);
	     Response<User> res = null;
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				res = objectMapper.readValue(s, new TypeReference<Response<User>>() {});
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
	public Response<String> edit(@RequestBody User user) {
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users", HttpMethod.PUT, entity, String.class);
	      
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
	public Response<String> delete(@PathVariable int id) {
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users/"+id, HttpMethod.DELETE, null, String.class);
	      
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
	
	
	
	@PostMapping("/resetRequest/{name}")
	public Response<String> requestReset(@PathVariable String name) {
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    //  HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users/resetRequest/"+name, HttpMethod.POST, null, String.class);
	      
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
	
	
	
	
	@PutMapping("/reset")
	public Response<String> resetPwd(@RequestBody User user) {
		 HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      var reponse= restTemplate.exchange(
	    	         "http://localhost:8081/users/reset", HttpMethod.PUT, entity, String.class);
	      
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


}
