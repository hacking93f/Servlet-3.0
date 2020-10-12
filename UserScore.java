package com;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserScore {
	
	private String fileName;
	
	UserScore(String fileName){
		
		
		this.fileName = fileName;
		
	}
	
	
	public void LoadData() throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonparser = new JSONParser();
		Object obj = jsonparser.parse(new FileReader(this.fileName));
		JSONObject jsonobject = (JSONObject)obj;
		
		
	}
	
	
		 
		
		
	}
	



