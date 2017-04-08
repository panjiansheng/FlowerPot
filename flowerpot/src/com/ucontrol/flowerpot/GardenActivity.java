package com.ucontrol.flowerpot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.AppContext;

import com.google.gson.Gson;
import com.ucontrol.flowerpot.data.SqliteControl;
import com.ucontrol.flowerpot.data.UpdateSqliteDataFromServer;
import com.ucontrol.flowerpot.po.FlowerState;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

public class GardenActivity extends Activity implements
SwipeRefreshLayout.OnRefreshListener{

	
	private ListView gardenList;
	private List<Map<String, Object>> flowerStateMaps = new ArrayList<Map<String, Object>>();
	private List<FlowerState> flowerStates = new ArrayList<FlowerState>();
	private SwipeRefreshLayout mSwipeLayout;
	private ImageButton iSayBtn;
	private Intent it;

	private SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_garden);
		gardenList = (ListView) findViewById(R.id.gardenList);
		mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
		mSwipeLayout.setOnRefreshListener(this);
		mSwipeLayout.setColorScheme(R.color.holo_blue_bright,
				R.color.holo_green_light,
				R.color.holo_orange_light,
				R.color.holo_red_light);
		adapter = new SimpleAdapter(this, flowerStateMaps,
				R.layout.flower_state_item, new String[] { "name", "imagepath",
						"temperature", "humidity", "ph" }, new int[] {
						R.id.floweritem_name, R.id.floweritem_img,
						R.id.floweritem_t, R.id.floweritem_rh,
						R.id.floweritem_ph });
		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// 判断是否为我们要处理的对象
				if (view instanceof ImageView && data instanceof Object) {
					ImageView iv = (ImageView) view;
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap bm = BitmapFactory.decodeFile(
							AppContext.LOCAL_IMG_PATH + data.toString(),
							options);
					iv.setImageBitmap(bm);

					// iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}
		});
		gardenList.setAdapter(adapter);
		gardenList.setOnItemClickListener(onItemClickListener);
		mSwipeLayout.setRefreshing(true);
		AsycLoader asycLoader = new AsycLoader();
		asycLoader.execute("null");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.garden, menu);
		return true;
	}


	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent it = new Intent(GardenActivity.this,
					FlowerDetailActivity.class);
			Map<String, Object> child = flowerStateMaps.get(arg2);
			Gson gson=new Gson();
			it.putExtra("flower_detail", gson.toJson(flowerStates.get(arg2)));
			startActivity(it);

		}
	};

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.iSayBtn:
			it = new Intent(this, ISayActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
		

	}



	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		AsycLoader asycLoader = new AsycLoader();
		asycLoader.execute("null");
	}

	private class AsycLoader extends AsyncTask<String, Integer, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			// SystemClock.sleep(3000);
			try{
				UpdateSqliteDataFromServer updateSqliteDataFromServer = new UpdateSqliteDataFromServer();
				updateSqliteDataFromServer.UpdateFlowerStates();
				SqliteControl sqliteControl = new SqliteControl();
				flowerStates = sqliteControl.GetFlowerState();
				flowerStateMaps.clear();
				for (FlowerState flowerState : flowerStates) {
					flowerStateMaps.add(flowerState.getMap());
				}
				sqliteControl.close();
				return true;
			}catch(Exception e){
				return false;
				
			}
		}

		protected void onPostExecute(Object object) {


			
			adapter.notifyDataSetChanged();
			mSwipeLayout.setRefreshing(false);

		}

	}


}
