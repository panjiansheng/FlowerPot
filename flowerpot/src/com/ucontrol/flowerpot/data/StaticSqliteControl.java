package com.ucontrol.flowerpot.data;

import java.util.ArrayList;
import java.util.List;

import util.AppContext;

import com.ucontrol.flowerpot.po.FlowerClassContent;
import com.ucontrol.flowerpot.po.FlowerState;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StaticSqliteControl {

	private static final String DATABASE_NAME = "flowerpot_static.db" ;
	SQLiteDatabase db;
	
	//在SD上创建数据库
	public StaticSqliteControl() {
		
		String filename = AppContext.Local_static_DATABASE_PATH;
		db =AppContext.getInstance().openOrCreateDatabase(filename,0,null);
	}
	
	
	
	public List<FlowerClassContent> getFlowerClassContent() {
		List<FlowerClassContent> flowerStateList=new ArrayList<FlowerClassContent>();
		String sql="select id,name,word,xingtai,gongneng,xixing,zaipei,yanghu from flower_class_static ";
		Cursor cursor = db.rawQuery(sql,  null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){//遍历Cursor
			FlowerClassContent flowerContent = new FlowerClassContent();
			flowerContent.setId(cursor.getInt(0));
			flowerContent.setName(cursor.getString(1));
			flowerContent.setWord(cursor.getString(2));
			flowerContent.setXingtai(cursor.getString(3));
			flowerContent.setGongneng(cursor.getString(4));
			flowerContent.setXixing(cursor.getString(5));
			flowerContent.setZaipei(cursor.getString(6));
			flowerContent.setYanghu(cursor.getString(7));
			flowerStateList.add(flowerContent);
			//listNewsMap.put(newsEntity.getId(),newsEntity);
		}
		cursor.close();
		return flowerStateList;
	}
	
	public FlowerClassContent getFlowerClassContentById(int id) {
		FlowerClassContent flowerContent=new FlowerClassContent();
		String sql="select id,name,word,xingtai,gongneng,xixing,zaipei,yanghu from flower_class_static where id="+id;
		Cursor cursor = db.rawQuery(sql,  null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){//遍历Cursor
			
			flowerContent.setId(cursor.getInt(0));
			flowerContent.setName(cursor.getString(1));
			flowerContent.setWord(cursor.getString(2));
			flowerContent.setXingtai(cursor.getString(3));
			flowerContent.setGongneng(cursor.getString(4));
			flowerContent.setXixing(cursor.getString(5));
			flowerContent.setZaipei(cursor.getString(6));
			flowerContent.setYanghu(cursor.getString(7));
			
			//listNewsMap.put(newsEntity.getId(),newsEntity);
		}
		
		return flowerContent;
	}

	

	
	public SQLiteDatabase getDatabase(){
		return db;
	}
	
	public void close(){
		db.close();
		
		}
}
