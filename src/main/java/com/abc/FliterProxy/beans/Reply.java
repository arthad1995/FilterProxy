package com.abc.FliterProxy.beans;

import java.sql.Timestamp;


import lombok.Data;

@Data

public class Reply {

	private int id;
	
	private String content;

	private int senderId;
	
	private int boardId;

	private int replyToUserId;

	private Timestamp time;
	
	
	
}
