package com.abc.FliterProxy.beans;

import java.sql.Timestamp;


import lombok.Data;

@Data

public class Notification {
	private int id;

	private int receiverId;
	////////////RECEVIERID
	

	private String content;
	

	private Timestamp time;

	public Notification(int receiverId, String content, Timestamp time) {
		super();
		this.receiverId = receiverId;
		this.content = content;
		this.time = time;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	

}