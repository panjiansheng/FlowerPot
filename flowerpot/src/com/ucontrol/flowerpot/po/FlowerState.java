package com.ucontrol.flowerpot.po;

import java.util.HashMap;
import java.util.Map;

import android.R.integer;

public class FlowerState {

	private int id;
	private String flowerid;

	private String name;
	private String imagepath;
	private String temperature;
	private String humidity;
	private String ph;
	
	public FlowerState(){
		
	}
	
	public FlowerState(int id,String flowerid,String name,String imagepath,String temperature,String humidity,String ph) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.flowerid=flowerid;
		this.name=name;
		this.imagepath=imagepath;
		this.temperature=temperature;
		this.humidity=humidity;
		this.ph=ph;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getFlowerid() {
		return flowerid;
	}

	public void setFlowerid(String flowerid) {
		this.flowerid = flowerid;
	}

	public Map<String, Object> getMap() {
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		hashMap.put("id", id);
		hashMap.put("flowerid", flowerid);
		hashMap.put("imagepath", imagepath);
		hashMap.put("name", name);
		hashMap.put("temperature", temperature);
		hashMap.put("humidity", humidity);
		hashMap.put("ph", ph);
		return hashMap;
	}
	
}
