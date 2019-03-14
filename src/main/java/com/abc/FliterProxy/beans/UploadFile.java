package com.abc.FliterProxy.beans;

import java.sql.Timestamp;



import lombok.Data;

@Data

public class UploadFile {


	private int id;

	private String path;

	private Timestamp uploadTime;

	private int uploaderId;

	private String type;

	private String name;
	//no constructor
	
}

