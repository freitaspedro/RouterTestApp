package br.pedrofreitas.myroutertestapp.fragment;

import java.util.LinkedHashMap;
import java.util.Map;

public class Info {
	
	private LinkedHashMap <String, String> result;	
	private String[] keys = {"Reboot", "DNS", "Acesso Remoto", "X", "Y"};
	
	
	public Info(Map<String, String> result) {
		this.result = (LinkedHashMap<String, String>) result;
	}
	
	public String[] getHeadlines() {
		return keys;
	}
	
	public String[] getDetails() {    	
		return new String[] {this.result.get(keys[0]), 
							 this.result.get(keys[1]),
							 this.result.get(keys[2]),
							 this.result.get(keys[3]),
							 this.result.get(keys[4])};        
	}    
    	   
    
}
