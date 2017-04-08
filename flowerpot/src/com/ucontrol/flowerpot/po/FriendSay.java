package com.ucontrol.flowerpot.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendSay {

	private int id;
	private String type;
	private String serverid;
	private String name;
	private String content;
	private String iconpath;
	private String replynum;
	private List<String> imagepath;
	private String time;
	
	
	
	public FriendSay(){
		
	}
	
	public  FriendSay(String serverid,String name,String content,String iconpath,List<String> imagepath,String replynum,String time,String type) {
		this.serverid=serverid;
		this.type=type;
		this.name=name;
		this.content=content;
		this.iconpath=iconpath;
		this.imagepath=imagepath;
		this.time=time;
		this.replynum=replynum;
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



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getIconpath() {
		return iconpath;
	}



	public void setIconpath(String icon) {
		this.iconpath = icon;
	}



	public List<String>  getImagepath() {
		
		return imagepath;
		
	}



	public void setImagepath(List<String>  img) {
		this.imagepath = img;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}
	
	public String getServerid() {
		return serverid;
	}



	public void setServerid(String serverid) {
		this.serverid = serverid;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}
	public String getReplynum() {
		return replynum;
	}

	public void setReplynum(String replynum) {
		this.replynum = replynum;
	}
	
	
	public Map<String, Object> getMap() {
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		hashMap.put("id", id);
		hashMap.put("serverid", serverid);
		hashMap.put("name", name);
		hashMap.put("content", content);
		hashMap.put("iconpath", iconpath);
		hashMap.put("replynum", replynum);
		hashMap.put("imagepath", imagepath);
		hashMap.put("time", time);
		hashMap.put("type", type);
		return hashMap;
	}


	
}
