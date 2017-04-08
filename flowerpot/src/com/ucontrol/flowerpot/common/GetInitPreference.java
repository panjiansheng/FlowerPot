package com.ucontrol.flowerpot.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GetInitPreference {
	private Context context;
	private static String StartInitConfig="initConfig";//��ʼ�����ļ���
	private static String Startcount="";//��־�Ƿ��һ������
	/**
	 * Constructor
	 * */

	public GetInitPreference(){}
	public GetInitPreference(Context context){
		this.context=context;
	}
	
	//��ȡ������Ϣ
	public String getInitData(){
		SharedPreferences sPreferences = context.getSharedPreferences(StartInitConfig,0 );
		Startcount=sPreferences.getString("Startcount","first");
		if(Startcount.equals("first")){
			changeInitData();
		}
		return Startcount;
	}
	
	//����������������
	public void changeInitData(){
		SharedPreferences sPreferences = context.getSharedPreferences(StartInitConfig,0 );
		Editor editor = sPreferences.edit();
		editor.putString("Startcount","other");
		editor.commit();
	}
	
	

}
