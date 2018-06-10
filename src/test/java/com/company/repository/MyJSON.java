package com.company.repository;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class MyJSON {
	private LinkedHashMap<String,LinkedHashMap<String,Object>> _embedded
	=new LinkedHashMap<>();
    private LinkedHashMap<String,LinkedHashMap<String,Object>> _links
    =new LinkedHashMap<>();
    private LinkedHashMap<String,LinkedHashMap<String,Object>> _page
    =new LinkedHashMap<>();
	
	public MyJSON() {
		
	}
	public MyJSON setValues(String objStr) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		MyJSON mj = objectMapper.readValue(objStr, MyJSON.class);  
		return mj;
	}
	
	public LinkedHashMap<String,Object> getEmbedded() {
		return _embedded.get("_embdedded");
	}
	
}
