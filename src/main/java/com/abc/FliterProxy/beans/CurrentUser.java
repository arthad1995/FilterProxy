package com.abc.FliterProxy.beans;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
public class CurrentUser {

	private int id;
	private String password;
	private String username;
	private String name;
	private String email;
	private Profile profile;
	public static CurrentUser currentUser = null;
	
	private CurrentUser() {
		super();
	}
	
	public static CurrentUser getInstance() {
		synchronized(CurrentUser.class){
			if(currentUser==null) {
				synchronized (CurrentUser.class) {
					currentUser = new CurrentUser();
				}
			}
		}
		return currentUser;
	}
	
	
}
