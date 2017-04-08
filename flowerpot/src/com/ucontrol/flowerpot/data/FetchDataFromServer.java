package com.ucontrol.flowerpot.data;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import util.AppContext;
import android.R.integer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucontrol.flowerpot.common.LoadFileToLocal;
import com.ucontrol.flowerpot.httpconnection.HttpConnect;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.po.FriendReply;
import com.ucontrol.flowerpot.po.FriendSay;

public class FetchDataFromServer {

	private Gson gson;
	private LoadFileToLocal loadFileToLocal;
	public FetchDataFromServer(){
		this.gson=new Gson();
		this.loadFileToLocal=new LoadFileToLocal();
	}
	
	public List<FlowerState> getFlowerStates() {
		String result = HttpConnect.getFlowerStates();
		if(result==null){
			return null;
		}
		List<FlowerState> flowerStates= gson.fromJson(result, new TypeToken<List<FlowerState>>(){}.getType());
		return flowerStates;
	}
	
	

	
	
	public List<FriendSay> getFriendSays(String type,String pageno) {
		String result = HttpConnect.getFriendSays(type,pageno);
		if(result==null){
			return null;
		}
		List<FriendSay> friendSays= gson.fromJson(result, new TypeToken<List<FriendSay>>(){}.getType());
		for(FriendSay friendSay:friendSays){
			loadFileToLocal.LoadFile(AppContext.SERVER_IMG_PATH + friendSay.getIconpath(),
					AppContext.LOCAL_IMG_PATH);
			for(String imagepath:friendSay.getImagepath()){
				
				loadFileToLocal.LoadFile(AppContext.SERVER_IMG_PATH + imagepath,
						AppContext.LOCAL_IMG_PATH);
			}
			
			
		}
		return friendSays ;
	}
	
	
	public List<FriendReply> getFriendReplies(String serverid) {
		String result = HttpConnect.getFriendRelies(serverid);
		if(result==null){
			return null;
		}
		List<FriendReply> friendReplies= gson.fromJson(result, new TypeToken<List<FriendReply>>(){}.getType());
		for(FriendReply friendReply:friendReplies){
			loadFileToLocal.LoadFile(AppContext.SERVER_IMG_PATH + friendReply.getIconpath(),
					AppContext.LOCAL_IMG_PATH);
			
		}
		return friendReplies ;
	}
	
}
