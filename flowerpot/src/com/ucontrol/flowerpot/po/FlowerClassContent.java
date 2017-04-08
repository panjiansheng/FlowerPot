package com.ucontrol.flowerpot.po;

import java.util.HashMap;
import java.util.Map;

public class FlowerClassContent {
	
	private int id;
	private String name;

	private String word;
	private String xingtai;
	private String gongneng;
	private String xixing;
	private String zhuzhi;
	public String getZhuzhi() {
		return zhuzhi;
	}
	public void setZhuzhi(String zhuzhi) {
		this.zhuzhi = zhuzhi;
	}

	private String zaipei;
	private String yanghu;
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
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getXingtai() {
		return xingtai;
	}
	public void setXingtai(String xingtai) {
		this.xingtai = xingtai;
	}
	public String getGongneng() {
		return gongneng;
	}
	public void setGongneng(String gongneng) {
		this.gongneng = gongneng;
	}
	public String getXixing() {
		return xixing;
	}
	public void setXixing(String xixing) {
		this.xixing = xixing;
	}
	public String getZaipei() {
		return zaipei;
	}
	public void setZaipei(String zaipei) {
		this.zaipei = zaipei;
	}
	public String getYanghu() {
		return yanghu;
	}
	public void setYanghu(String yanghu) {
		this.yanghu = yanghu;
	}
	
	public HashMap<String, Object> getMap() {
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		hashMap.put("id", id);
		hashMap.put("name", name);
		hashMap.put("word", word);
		hashMap.put("img", name+".ipg");
		hashMap.put("xingtai", xingtai);
		hashMap.put("zhuzhi", zhuzhi);
		hashMap.put("xixing", xixing);
		hashMap.put("zaipei", zaipei);
		hashMap.put("yanghu", yanghu);
		return hashMap;
	}
	
	
}
