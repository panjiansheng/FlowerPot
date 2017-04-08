package com.ucontrol.flowerpot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import util.AppContext;

import com.google.gson.Gson;
import com.ucontrol.flowerpot.common.DeleteFile;
import com.ucontrol.flowerpot.common.GetInitPreference;
import com.ucontrol.flowerpot.common.SharePreferenceHelper;
import com.ucontrol.flowerpot.common.ZipHelper;
import com.ucontrol.flowerpot.data.SqliteControl;
import com.ucontrol.flowerpot.httpconnection.HttpConnect;
import com.ucontrol.flowerpot.po.ServerResponse;

import cn.jpush.android.api.InstrumentedActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.Window;

public class WelcomeActivity extends InstrumentedActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);

		AsycLoader asycLoader = new AsycLoader();
		asycLoader.execute("null");
	}

	private class AsycLoader extends AsyncTask<String, Integer, Object> {

		Gson gson=new Gson();
		SharePreferenceHelper sharePreferenceHelper=new SharePreferenceHelper();
		ServerResponse serverResponse=new ServerResponse();

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			GetInitPreference initPreference = new GetInitPreference(
					WelcomeActivity.this);
			String string = initPreference.getInitData();
			serverResponse=gson.fromJson(HttpConnect.login(sharePreferenceHelper.getUserId(), sharePreferenceHelper.getUserPassword()), ServerResponse.class);
			// if (string.equals("first")) { // whether it is the first time to
			// launch app
			if (true) {
				SharePreferenceHelper sharePreferenceHelper=new SharePreferenceHelper();
				sharePreferenceHelper.clear();
				String filename = AppContext.LOCAL_ALLDATA_PATH;
				File databaseFile = new File(filename);
				if (databaseFile.exists()) {
					DeleteFile.delete(databaseFile);
				}

				createDatabase();
				// SystemClock.sleep(1000);
				createStaticData();
			}
			return null;
		}

		protected void onPostExecute(Object object) {


			if (!sharePreferenceHelper.getUserId().equals("")&&serverResponse!=null) {
				if (!serverResponse.getResult().equals("1")) {
					HttpConnect httpConnect=new HttpConnect();
					Intent it = new Intent(WelcomeActivity.this, MainActivity.class);
					startActivity(it);
					finish();
				}else {
					Intent it = new Intent(WelcomeActivity.this, LoginActivity.class);
					startActivity(it);
					finish();
				}
				
			}else {
				Intent it = new Intent(WelcomeActivity.this, LoginActivity.class);
				startActivity(it);
				finish();
			}
			
			


		}

	}

	public  void createStaticData() {
		File staticPath = new File(AppContext.Local_static_DATA_PATH);
		File staticImgPath = new File(AppContext.Local_static_IMG_PATH);
		if (!staticPath.exists()) {
			staticPath.mkdirs();
			staticImgPath.mkdirs();
		}

		try {
			File dataBaseStatic = new File(AppContext.Local_static_DATABASE_PATH);
			if (!dataBaseStatic.exists()) {
				InputStream is = getResources().openRawResource(
						R.raw.flowerpot_static);
				FileOutputStream fos;
				fos = new FileOutputStream(dataBaseStatic);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			File imgStatic = new File(AppContext.Local_static_IMGZIP_PATH);
			if (!imgStatic.exists()) {
				InputStream is = getResources().openRawResource(
						R.raw.flower_img);
				FileOutputStream fos;
				fos = new FileOutputStream(imgStatic);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				
				ZipHelper.unzipFile(AppContext.Local_static_IMGZIP_PATH,AppContext.Local_static_IMG_PATH);
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createDatabase() {
		deleteDatabase(android.os.Environment.getExternalStorageDirectory()
				+ "/FlowerPot");
		SqliteControl sqliteControl = new SqliteControl();
		sqliteControl.FirstStart();
		sqliteControl.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
		return true;
	}

}
