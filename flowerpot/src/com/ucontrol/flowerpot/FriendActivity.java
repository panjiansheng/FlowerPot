package com.ucontrol.flowerpot;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.AppContext;

import com.google.gson.Gson;
import com.ucontrol.flowerpot.common.DeleteFile;
import com.ucontrol.flowerpot.common.GetInitPreference;
import com.ucontrol.flowerpot.data.FetchDataFromServer;
import com.ucontrol.flowerpot.data.SqliteControl;
import com.ucontrol.flowerpot.data.UpdateSqliteDataFromServer;
import com.ucontrol.flowerpot.httpconnection.HttpConnect;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.po.FriendSay;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnGenericMotionListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TabHost.OnTabChangeListener;

public class FriendActivity extends Activity {

	private List<FriendSay> myFriendSays = new ArrayList<FriendSay>();
	private List<FriendSay> aroundFriendSays = new ArrayList<FriendSay>();
	private ListView mListView;
	private List<Map<String, Object>> myFriendSayMaps = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> aroundFriendSayMaps = new ArrayList<Map<String, Object>>();
	private int myFriendListNum;
	private int aroundFriendListNum;
	private ListView myFriendListView;
	private ListView aroundFriendListView;
	private SimpleAdapter myAdapter;
	private SimpleAdapter aroundAdapter;
	private SwipeRefreshLayout swipeMyFriend;
	private SwipeRefreshLayout swipeAroundFriend;
	private LinearLayout showLoadingLy;
	private boolean isLoadingA = false;
	private boolean isLoadingB = false;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		initFriendTab();
		myFriendListView = (ListView) findViewById(R.id.myFriendList);
		aroundFriendListView = (ListView) findViewById(R.id.aroundFriendList);
		swipeMyFriend = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly_myfriend);
		swipeAroundFriend = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly_aroundfriend);
		LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		showLoadingLy = (LinearLayout) mInflater.inflate(R.layout.load_more_footer, null);
		swipeMyFriend.setOnRefreshListener(myOnRefreshListener);
		swipeAroundFriend.setOnRefreshListener(aroundOnRefreshListener);
		swipeAroundFriend.setColorScheme(R.color.holo_blue_bright, R.color.holo_green_light,
				R.color.holo_orange_light, R.color.holo_red_light);
		swipeMyFriend.setColorScheme(R.color.holo_blue_bright, R.color.holo_green_light,
				R.color.holo_orange_light, R.color.holo_red_light);
		myAdapter = new SimpleAdapter(this, myFriendSayMaps, R.layout.friend_list_item,
				new String[] { "name", "content", "iconpath", "imagepath", "time", "replynum" },
				new int[] { R.id.friend_list_item_name, R.id.friend_list_item_content,
						R.id.friend_list_item_icon, R.id.friend_list_item_imgly,
						R.id.friend_list_item_time, R.id.friend_list_item_reply_num });
		myAdapter.setViewBinder(viewBinder);

		aroundAdapter = new SimpleAdapter(this, aroundFriendSayMaps, R.layout.friend_list_item,
				new String[] { "name", "content", "iconpath", "imagepath", "time", "replynum" },
				new int[] { R.id.friend_list_item_name, R.id.friend_list_item_content,
						R.id.friend_list_item_icon, R.id.friend_list_item_imgly,
						R.id.friend_list_item_time, R.id.friend_list_item_reply_num });
		myFriendListView.addFooterView(showLoadingLy);
		aroundFriendListView.addFooterView(showLoadingLy);
		aroundAdapter.setViewBinder(viewBinder);
		myFriendListView.setAdapter(myAdapter);
		aroundFriendListView.setAdapter(aroundAdapter);
		myFriendListView.setOnScrollListener(onScrollListener);
		aroundFriendListView.setOnScrollListener(onScrollListener);
		myFriendListView.setOnItemClickListener(myOnItemClickListener);
		aroundFriendListView.setOnItemClickListener(aroundOnItemClickListener);
		swipeMyFriend.setRefreshing(true);
		myOnRefreshListener.onRefresh();
		// aroundOnRefreshListener.onRefresh();
	

	}

	OnTabChangeListener onTabChangeListener = new OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			if (aroundFriendSayMaps.size() == 0 && tabId.equals("ftab2")) {
				swipeAroundFriend.setRefreshing(true);
				aroundOnRefreshListener.onRefresh();
			}
		}
	};

	public void initFriendTab() {

		View myFriendTab = null, aroundTab = null;
		View[] tabs = new View[] { myFriendTab, aroundTab };
		int[] tabIds = new int[] { R.id.ftab1, R.id.ftab2 };
		int[] tabImgs = new int[] { R.drawable.tab_garden, R.drawable.tab_friend,
				R.drawable.tab_shop, R.drawable.tab_class };
		int[] tabLayouts = new int[] { R.layout.tab_friend_child_left,
				R.layout.tab_friend_child_right };

		String[] title = new String[] { "ftab1", "ftab2" };
		TabHost tabHost = (TabHost) findViewById(R.id.friendTabHost);
		tabHost.setup(); // Call setup() before adding tabs if loading TabHost
							// using findViewById().
		tabHost.setOnTabChangedListener(onTabChangeListener);
		for (int i = 0; i < tabs.length; i++) {
			tabs[i] = (View) LayoutInflater.from(this).inflate(tabLayouts[i], null);
			tabHost.addTab(tabHost.newTabSpec(title[i]).setIndicator(tabs[i]).setContent(tabIds[i]));
		}

	}

	ViewBinder viewBinder = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Object data, String textRepresentation) {
			// TODO Auto-generated method stub
			if (view instanceof ImageView && data instanceof Object) {
				ImageView iv = (ImageView) view;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(AppContext.LOCAL_IMG_PATH + data.toString(),
						options);
				iv.setImageBitmap(bm);
				return true;
			}
			else if (view instanceof LinearLayout && data instanceof Object) {
				List<String> imageList = (List<String>) data;
				LinearLayout ly = (LinearLayout) view;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bm0;
				Bitmap bm1;
				switch (imageList.size()) {
				case 0:
					ly.setVisibility(View.GONE);
					break;
				case 1:
					if (imageList.get(0).equals("")) {
						ly.setVisibility(View.GONE);
					} else {
						bm0 = BitmapFactory.decodeFile(
								AppContext.LOCAL_IMG_PATH + imageList.get(0), options);
						((ImageView) ly.findViewById(R.id.imageView3)).setImageBitmap(bm0);
						((ImageView) ly.findViewById(R.id.imageView4)).setVisibility(View.GONE);
						// ((ImageView)
						// ly.findViewById(R.id.imageView4)).setImageURI(Uri.parse("http://www.panjiansheng.info:8080/FlowerPot/images/1.png"));

					}
					break;
				default:
					bm0 = BitmapFactory.decodeFile(AppContext.LOCAL_IMG_PATH + imageList.get(0),
							options);
					((ImageView) ly.findViewById(R.id.imageView3)).setImageBitmap(bm0);
					bm1 = BitmapFactory.decodeFile(AppContext.LOCAL_IMG_PATH + imageList.get(1),
							options);
					((ImageView) ly.findViewById(R.id.imageView4)).setImageBitmap(bm1);
					break;

				}
				// ImageView iv = new ImageView(FriendActivity.this);

				// iv.setImageBitmap(bm);
				// LinearLayout.LayoutParams lp = new
				// LinearLayout.LayoutParams(150, 150);
				// lp.setMargins(0, 0, 10, 0);
				// iv.setLayoutParams(lp);
				// iv.setScaleType(ScaleType.CENTER_CROP);

				// ly.setVisibility(View.INVISIBLE);
				// iv.setImageBitmap((Bitmap) data);
				return true;
			} else
				return false;
		}
	};

	private OnScrollListener onScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& view.getLastVisiblePosition() == (view.getCount() - 1) && !isLoadingA&&!isLoadingB) {

				AsycLoadMore asycLoadMore = new AsycLoadMore();
				switch (view.getId()) {
				case R.id.myFriendList:
					if (myFriendSayMaps.size() < myFriendListNum) {
						asycLoadMore.execute("0",
								String.valueOf((int) (myFriendSayMaps.size() / 10 + 1)));

					}
					break;
				case R.id.aroundFriendList:
					if (aroundFriendSayMaps.size() < aroundFriendListNum) {
						asycLoadMore.execute("1",
								String.valueOf((int) (aroundFriendSayMaps.size() / 10 + 1)));
					}
					break;
				default:
					break;
				}
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
				int totalItemCount) {
			// TODO Auto-generated method stub
		}
	};

	OnItemClickListener myOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Gson gson = new Gson();
			Intent it = new Intent(FriendActivity.this, FriendSayDetailActivity.class);
			it.putExtra("data", gson.toJson(myFriendSays.get(arg2)));
			startActivity(it);
		}
	};

	OnItemClickListener aroundOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Gson gson = new Gson();
			Intent it = new Intent(FriendActivity.this, FriendSayDetailActivity.class);
			it.putExtra("data", gson.toJson(aroundFriendSays.get(arg2)));
			startActivity(it);
		}
	};

	private class AsycLoadMore extends AsyncTask<String, Integer, Object> {

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			isLoadingB = true;
			List<FriendSay> friendSaysTemp = new ArrayList<FriendSay>();
			FetchDataFromServer fetchDataFromServer = new FetchDataFromServer();
			friendSaysTemp = fetchDataFromServer.getFriendSays(params[0], params[1]);
			switch (Integer.parseInt(params[0])) {
			case 0:
				for (FriendSay friendSay : friendSaysTemp) {
					myFriendSayMaps.add(friendSay.getMap());
					myFriendSays.add(friendSay);
				}
				break;
			case 1:
				for (FriendSay friendSay : friendSaysTemp) {
					aroundFriendSayMaps.add(friendSay.getMap());
					aroundFriendSays.add(friendSay);
				}
				break;
			default:
				break;
			}
			return null;
		}

		protected void onPostExecute(Object object) {

			if (myFriendSayMaps.size() >= myFriendListNum) {
				myFriendListView.removeFooterView(showLoadingLy);
			}
			if (aroundFriendSayMaps.size() >= myFriendListNum) {
				aroundFriendListView.removeFooterView(showLoadingLy);
			}
			myAdapter.notifyDataSetChanged();
			aroundAdapter.notifyDataSetChanged();
			isLoadingB = false;
		}
	}

	private class AsycLoader extends AsyncTask<String, Integer, Object> {
		List<FriendSay> friendSaysTemp;
		List<Map<String, Object>> friendSaysMapTemp = new ArrayList<Map<String, Object>>();

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			isLoadingA = true;
			UpdateSqliteDataFromServer updateSqliteDataFromServer = new UpdateSqliteDataFromServer();
			SqliteControl sqliteControl = new SqliteControl();
			switch (Integer.parseInt(params[0])) {
			case 0:
				myFriendListNum = updateSqliteDataFromServer.UpdateFriendSays(params[0]);
				friendSaysTemp = sqliteControl.getFriendSay(params[0]);

				myFriendSays.clear();
				for (FriendSay friendSay : friendSaysTemp) {
					friendSaysMapTemp.add(friendSay.getMap());
					myFriendSays.add(friendSay);
				}
				sqliteControl.close();
				break;
			case 1:
				aroundFriendListNum = updateSqliteDataFromServer.UpdateFriendSays(params[0]);
				friendSaysTemp = sqliteControl.getFriendSay(params[0]);
				aroundFriendSays.clear();
				for (FriendSay friendSay : friendSaysTemp) {
					friendSaysMapTemp.add(friendSay.getMap());
					aroundFriendSays.add(friendSay);
				}
				sqliteControl.close();
				break;
			default:
				break;
			}
			// SystemClock.sleep(3000);
			return params[0];
		}

		protected void onPostExecute(Object object) {
			switch (Integer.parseInt((String) object)) {
			case 0:
				myFriendSayMaps.clear();
				myFriendSayMaps.addAll(friendSaysMapTemp);
				myAdapter.notifyDataSetChanged();
				if (myFriendSayMaps.size() < myFriendListNum) {
					myFriendListView.addFooterView(showLoadingLy);
				}

				swipeMyFriend.setRefreshing(false);
				break;
			case 1:

				if (aroundFriendSayMaps.size() < aroundFriendListNum) {
					aroundFriendListView.addFooterView(showLoadingLy);
				}
				aroundFriendSayMaps = friendSaysMapTemp;
				aroundAdapter.notifyDataSetChanged();

				swipeAroundFriend.setRefreshing(false);
				break;
			default:
				break;
			}
			isLoadingA = false;

		}
	}

	public OnRefreshListener myOnRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			if (!isLoadingA&&!isLoadingB) {

				myFriendListView.removeFooterView(showLoadingLy);

				AsycLoader asycLoader = new AsycLoader();
				asycLoader.execute("0");
				

			}else {
				swipeMyFriend.setRefreshing(true);
			}
		}
	};

	public OnRefreshListener aroundOnRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			if (!isLoadingA&&!isLoadingB) {
				aroundFriendListView.removeFooterView(showLoadingLy);
				AsycLoader asycLoader = new AsycLoader();
				asycLoader.execute("1");
			}else {
				swipeAroundFriend.setRefreshing(false);
			}

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend, menu);
		return true;
	}

}
