package com.ucontrol.flowerpot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.AppContext;
import cn.jpush.android.api.m;

import com.ucontrol.flowerpot.common.DeleteFile;
import com.ucontrol.flowerpot.common.SharePreferenceHelper;
import com.ucontrol.flowerpot.common.ZipHelper;
import com.ucontrol.flowerpot.data.SqliteControl;
import com.ucontrol.flowerpot.data.UpdateSqliteDataFromServer;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.widgets.CommonOnTouchListener;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.R.layout;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.util.EventLog.Event;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TabHost;

public class MainActivity extends ActivityGroup {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		View gardenTab = null, friendTab = null, shopTab = null, classTab = null;
		View[] tabs = new View[] { gardenTab, friendTab, shopTab, classTab };
		int[] tabIds = new int[] { R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4 };
		int[] tabImgs = new int[] { R.drawable.tab_garden, R.drawable.tab_friend,
				R.drawable.tab_shop, R.drawable.tab_class };
		int[] tabLayouts = new int[] { R.layout.tab_garden, R.layout.tab_friend, R.layout.tab_shop,
				R.layout.tab_class };

		String[] title = new String[] { "tab1", "tab2", "tab3", "tab4" };
		TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup(this.getLocalActivityManager()); // Call setup() before
														// adding tabs if
														// loading TabHost using
														// findViewById().
		for (int i = 0; i < tabs.length; i++) {
			tabs[i] = (View) LayoutInflater.from(this).inflate(tabLayouts[i], null);

			// tabHost.addTab(tabHost.newTabSpec(title[i]).setIndicator(tabs[i]).setContent(new
			// Intent(this,GardenActivity.class)));
		}
		tabHost.addTab(tabHost.newTabSpec(title[0]).setIndicator(tabs[0])
				.setContent(new Intent(this, GardenActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(title[1]).setIndicator(tabs[1])
				.setContent(new Intent(this, FriendActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(title[2]).setIndicator(tabs[2])
				.setContent(new Intent(this, ShopActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(title[3]).setIndicator(tabs[3])
				.setContent(new Intent(this, FlowerClassActivity.class)));

		// initFriendTab();

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.logout:
			SharePreferenceHelper sharePreferenceHelper = new SharePreferenceHelper();
			sharePreferenceHelper.clear();
			String filename = AppContext.LOCAL_ALLDATA_PATH;
			File databaseFile = new File(filename);
			if (databaseFile.exists()) {
				DeleteFile.delete(databaseFile);
			}

			createDatabase();
			// SystemClock.sleep(1000);
			createStaticData();
			Intent it=new Intent(MainActivity.this,LoginActivity.class);
			startActivity(it);
			finish();
			break;

		default:
			break;
		}
	}

	public void createStaticData() {
		File staticPath = new File(AppContext.Local_static_DATA_PATH);
		File staticImgPath = new File(AppContext.Local_static_IMG_PATH);
		if (!staticPath.exists()) {
			staticPath.mkdirs();
			staticImgPath.mkdirs();
		}

		try {
			File dataBaseStatic = new File(AppContext.Local_static_DATABASE_PATH);
			if (!dataBaseStatic.exists()) {
				InputStream is = getResources().openRawResource(R.raw.flowerpot_static);
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
				InputStream is = getResources().openRawResource(R.raw.flower_img);
				FileOutputStream fos;
				fos = new FileOutputStream(imgStatic);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();

				ZipHelper.unzipFile(AppContext.Local_static_IMGZIP_PATH,
						AppContext.Local_static_IMG_PATH);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createDatabase() {
		deleteDatabase(android.os.Environment.getExternalStorageDirectory() + "/FlowerPot");
		SqliteControl sqliteControl = new SqliteControl();
		sqliteControl.FirstStart();
		sqliteControl.close();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
