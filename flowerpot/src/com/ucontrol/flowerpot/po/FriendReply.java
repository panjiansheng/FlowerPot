package com.ucontrol.flowerpot.po;

import java.util.HashMap;
import java.util.Map;

public class FriendReply {

	private String serverid;
	private String name;
	private String content;
	private String time;
	private String iconpath;
	
	public FriendReply(String serverid,String name,String iconpath,String content,String time) {
		// TODO Auto-generated constructor stub
		this.serverid=serverid;
		this.name=name;
		this.content=content;
		this.time=time;
		this.iconpath=iconpath;
	}
	
	public String getServerid() {
		return serverid;
	}
	public void setServerid(String serverid) {
		this.serverid = serverid;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIconpath() {
		return iconpath;
	}
	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}
	
	public Map<String, Object> getMap() {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("serverid", serverid);
		map.put("name", name);
		map.put("content", content);
		map.put("time", time);
		map.put("iconpath", iconpath);
		return map;
	}
	
}
