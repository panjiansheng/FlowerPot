package com.ucontrol.flowerpot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.AppContext;

import com.google.gson.Gson;
import com.ucontrol.flowerpot.common.SharePreferenceHelper;
import com.ucontrol.flowerpot.data.FetchDataFromServer;
import com.ucontrol.flowerpot.httpconnection.HttpConnect;
import com.ucontrol.flowerpot.po.FriendReply;
import com.ucontrol.flowerpot.po.FriendSay;
import com.ucontrol.flowerpot.po.ServerResponse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;

public class FriendSayDetailActivity extends Activity {

	private ListView friendReplyListView;
	private List<FriendReply> fps=new ArrayList<FriendReply>();
	private List<Map<String, Object>> fpMaps=new ArrayList<Map<String,Object>>();
	private SimpleAdapter adapter;
	private LinearLayout headerLy;
	private FriendSay friendSay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_say_detail);
		friendSay=(new Gson()).fromJson(getIntent().getStringExtra("data"), FriendSay.class);
		friendReplyListView=(ListView)findViewById(R.id.friend_reply_list);
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		headerLy = (LinearLayout) mInflater.inflate(R.layout.friend_say_header, null);
		adapter = new SimpleAdapter(this,fpMaps,
				R.layout.friend_reply_item, new String[] { "serverid","name", "iconpath","content",
						"time" }, new int[] {
						R.id.friend_list_item_serverid, R.id.friend_list_item_name,
						R.id.friend_list_item_icon, R.id.friend_list_item_content,
						R.id.friend_list_item_time });
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
		
		((TextView)headerLy.findViewById(R.id.friend_list_item_content)).setText(friendSay.getContent());
		((TextView)headerLy.findViewById(R.id.friend_list_item_name)).setText(friendSay.getName());
		((TextView)headerLy.findViewById(R.id.friend_list_item_time)).setText(friendSay.getTime());
		((TextView)headerLy.findViewById(R.id.friend_list_item_reply_num)).setText(friendSay.getReplynum());
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bm = BitmapFactory.decodeFile(AppContext.LOCAL_IMG_PATH + friendSay.getIconpath(),
				options);
		((ImageView)headerLy.findViewById(R.id.friend_list_item_icon)).setImageBitmap(bm);
		LinearLayout ly=(LinearLayout)headerLy.findViewById(R.id.friend_list_item_imgly);
		ly.removeAllViews();
		try {
			for(int i=0;i<friendSay.getImagepath().size();i++){
				
				 ImageView iv = new ImageView(FriendSayDetailActivity.this);
				 bm = BitmapFactory.decodeFile(AppContext.LOCAL_IMG_PATH + friendSay.getImagepath().get(i),
							options);
				 iv.setImageBitmap(bm);
				 LinearLayout.LayoutParams lp = new
				 LinearLayout.LayoutParams(70,70);
				 lp.setMargins(0, 0, 10, 0);
				 iv.setLayoutParams(lp);
				 iv.setScaleType(ScaleType.CENTER_CROP);

				 ly.addView(iv);
				 
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		friendReplyListView.addHeaderView(headerLy);
		friendReplyListView.setAdapter(adapter);
		AsycLoader asycLoader=new AsycLoader();
		asycLoader.execute(friendSay.getServerid());
		
	}

	
	private class AsycLoader extends AsyncTask<String, Integer, Object> {

		Gson gson = new Gson();
		SharePreferenceHelper sharePreferenceHelper = new SharePreferenceHelper();


		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			FetchDataFromServer fetchDataFromServer=new FetchDataFromServer();
			
			fps =fetchDataFromServer.getFriendReplies(params[0]);
			if (fps==null) {
				return false;
			}
//			if (result==null) {
//				return false;
//			}
//			serverResponse = gson.fromJson(result, ServerResponse.class);
			return true;
		}

		protected void onPostExecute(Object object) {
			if ((Boolean)object) {

				for(FriendReply friendReply:fps){
					fpMaps.add(friendReply.getMap());
				}
				adapter.notifyDataSetChanged();
			
			}else {
				Toast.makeText(FriendSayDetailActivity.this, "联网失败", Toast.LENGTH_LONG).show();
			}
			//((LinearLayout)findViewById(R.id.showLoading)).setVisibility(View.GONE);
		}
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_action_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_say_detail, menu);
		return true;
	}

}
