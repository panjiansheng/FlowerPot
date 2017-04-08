package com.ucontrol.flowerpot.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.AppContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.po.FriendSay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqliteControl {

	private static final String DATABASE_NAME = "FlowerPotApp.db" ;
	SQLiteDatabase db;
	
	//在SD上创建数据库
	public SqliteControl() {
		
		String filename = AppContext.LOCAL_DATABASE_PATH;
		db =AppContext.getInstance().openOrCreateDatabase(filename,0,null);
	}
	
	
	public void FirstStart() {

			CreateTable_FlowerState();
			CreateTable_FriendSay();

	}
	
	public void CreateTable_FlowerState() {
		try {
			db.execSQL( "CREATE TABLE flowerstate (" //创建新闻内容表
					+ "id INTEGER PRIMARY KEY,"
					+ "flowerid TEXT,"
					+ "name TEXT,"
					+ "imagepath TEXT,"
					+ "temperature Text,"
					+ "humidity TEXT,"
					+ "ph TEXT"
					+ ");" );
		} catch (Exception e){
		}
	}
	
	public void CreateTable_FriendSay() {
		try {
			db.execSQL( "CREATE TABLE friendsay (" //创建新闻内容表
					+ "id INTEGER PRIMARY KEY,"
					+ "serverid TEXT,"
					+ "name TEXT,"
					+ "content TEXT,"
					+ "iconpath Text,"
					+ "imagepath TEXT,"
					+ "replynum TEXT,"
					+ "time TEXT,"
					+ "type TEXT"
					+ ");" );
		} catch (Exception e){
		}
	}
	
	public void InsertIntoFriendsay(String serverid,String name,String content,String icon,String img,String replynum,String time,String type) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("serverid", serverid);
		contentValues.put("name", name);
		contentValues.put("content", content);
		contentValues.put("iconpath", icon);
		contentValues.put("imagepath", img);
		contentValues.put("replynum", replynum);
		contentValues.put("time", time);
		contentValues.put("type", type);
		db.insert("friendsay", null, contentValues);
	}
	
	public void DeleteFriendSay() {
		db.delete("friendsay",null,null);
	}
	
	public List<FriendSay> getFriendSay(String type) {
		List<FriendSay> friendsaylist=new ArrayList<FriendSay>();
		String sql="select id,serverid,name,content,iconpath,imagepath,replynum,time,type from friendsay where type="+type;
		Cursor cursor = db.rawQuery(sql,  null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){//遍历Cursor
			FriendSay friendSay = new FriendSay();
			friendSay.setId(cursor.getInt(0));
			friendSay.setServerid(cursor.getString(1));
			friendSay.setName(cursor.getString(2));
			friendSay.setContent(cursor.getString(3));
			friendSay.setIconpath(cursor.getString(4));
			friendSay.setImagepath(Arrays.asList(cursor.getString(5).split(",")));
			friendSay.setReplynum(cursor.getString(6));
			friendSay.setTime(cursor.getString(7));
			friendSay.setType(cursor.getString(8));
			friendsaylist.add(friendSay);
			//listNewsMap.put(newsEntity.getId(),newsEntity);
		}
		cursor.close();
		return friendsaylist;
	}
	
	
	public void InsertIntoFlwoerstate(String flowerid,String name,String imagepath,String temperature,String humidity,String ph) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("flowerid", flowerid);
		contentValues.put("name", name);
		contentValues.put("imagepath", imagepath);
		contentValues.put("temperature", temperature);
		contentValues.put("humidity", humidity);
		contentValues.put("ph", ph);
		db.insert("flowerstate", null, contentValues);
	}
	

	
	public void UpdateFlwoerstate(int id,String flowerid,String name,String imagepath,String temperature,String humidity,String ph) {
		db.delete("flowerstate","id=?",new String[]{"*"});
		ContentValues contentValues = new ContentValues();
		contentValues.put("id", id);
		contentValues.put("flowerid", flowerid);
		contentValues.put("name", name);
		contentValues.put("imagepath", imagepath);
		contentValues.put("temperatrue", temperature);
		contentValues.put("humidity", humidity);
		contentValues.put("ph", ph);
		 String[] args = {String.valueOf(id)};
		 db.update("flowerstate", contentValues, "id=?", args);
	}
	
	
	public void deleteFlwoerstate() {

		db.delete("flowerstate",null,null);
	}
	
	public List<FlowerState> GetFlowerState() {
		List<FlowerState> flowerStateList=new ArrayList<FlowerState>();
		String sql="select id,flowerid,name,imagepath,temperature,humidity,ph from flowerstate ";
		Cursor cursor = db.rawQuery(sql,  null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){//遍历Cursor
			FlowerState flowerState = new FlowerState();
			flowerState.setId(cursor.getInt(0));
			flowerState.setFlowerid(cursor.getString(1));
			flowerState.setName(cursor.getString(2));
			flowerState.setImagepath(cursor.getString(3));
			flowerState.setTemperature(cursor.getString(4));
			flowerState.setHumidity(cursor.getString(5));
			flowerState.setPh(cursor.getString(6));
			flowerStateList.add(flowerState);
			//listNewsMap.put(newsEntity.getId(),newsEntity);
		}
		cursor.close();
		return flowerStateList;
	}

	

	
	public SQLiteDatabase getDatabase(){
		return db;
	}
	
	public void close(){
		db.close();
		
		}
}
