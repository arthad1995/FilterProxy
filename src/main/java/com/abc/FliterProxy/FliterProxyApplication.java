package com.abc.FliterProxy;




import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FliterProxyApplication {
	
	   

	public static void main(String[] args) {
		SpringApplication.run(FliterProxyApplication.class, args);
	}
	 @Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
	
}
