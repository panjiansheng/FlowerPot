package com.ucontrol.flowerpot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.AppContext;

import com.ucontrol.flowerpot.data.StaticSqliteControl;
import com.ucontrol.flowerpot.po.FlowerClassContent;
import com.ucontrol.flowerpot.widgets.XCRoundRectImageView;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class FlowerClassActivity extends Activity {

	private List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
	private List<FlowerClassContent> flowerClassContentList;
	private SimpleAdapter adapter;
	private ListView classContentListView;
	TextView tv0;
	TextView tv1;
	TextView tv2;
	TextView tv3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flower_class);
		classContentListView = (ListView) findViewById(R.id.classContentListview);
		StaticSqliteControl staticSqliteControl = new StaticSqliteControl();
		flowerClassContentList = staticSqliteControl.getFlowerClassContent();
		int i = 0;
		for (i = 0; i + 4 < flowerClassContentList.size(); i = i + 4) {

			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			for (int j = 0; j < 4; j++) {
				FlowerClassContent flowerClassContent = flowerClassContentList.get(i + j);

				hashMap.put("id" + j, flowerClassContent.getId());
				hashMap.put("name" + j, flowerClassContent.getName());
				hashMap.put("word" + j, flowerClassContent.getWord());
				hashMap.put("img" + j, flowerClassContent.getName() + ".jpg");
				hashMap.put("xingtai" + j, flowerClassContent.getXingtai());
				hashMap.put("gongneng" + j, flowerClassContent.getGongneng());
				hashMap.put("xiting" + j, flowerClassContent.getXixing());
				hashMap.put("zaipei" + j, flowerClassContent.getZaipei());
				hashMap.put("yanghu" + j, flowerClassContent.getYanghu());

			}
			mapList.add(hashMap);
		}

		HashMap<String, Object> hashMap2 = new HashMap<String, Object>();
		int j = 0;
		for (; i + j < flowerClassContentList.size(); j++) {
			FlowerClassContent flowerClassContent = flowerClassContentList.get(i + j);

			hashMap2.put("id" + j, flowerClassContent.getId());
			hashMap2.put("name" + j, flowerClassContent.getName());
			hashMap2.put("word" + j, flowerClassContent.getWord());
			hashMap2.put("img" + j, flowerClassContent.getName() + ".jpg");
			hashMap2.put("xingtai" + j, flowerClassContent.getXingtai());
			hashMap2.put("gongneng" + j, flowerClassContent.getGongneng());
			hashMap2.put("xiting" + j, flowerClassContent.getXixing());
			hashMap2.put("zaipei" + j, flowerClassContent.getZaipei());
			hashMap2.put("yanghu" + j, flowerClassContent.getYanghu());

		}

		for (; i + j < i + 4; j++) {

			hashMap2.put("id" + j, "null");
			hashMap2.put("name" + j, "null");
			hashMap2.put("word" + j, "null");
			hashMap2.put("img" + j, "null");
			hashMap2.put("xingtai" + j, "null");
			hashMap2.put("gongneng" + j, "null");
			hashMap2.put("xiting" + j, "null");
			hashMap2.put("zaipei" + j, "null");
			hashMap2.put("yanghu" + j, "null");

		}

		mapList.add(hashMap2);

		staticSqliteControl.close();

		// adapter=new SimpleAdapter(this, mapList, new
		// int[]{R.id.class_item_img0, R.id.class_item_name0], to)
		adapter = new SimpleAdapter(this, mapList, R.layout.flower_class_item, new String[] {
				"id0", "img0", "name0", "id1", "img1", "name1", "id2", "img2", "name2", "id3",
				"img3", "name3" }, new int[] { R.id.class_item_id0, R.id.class_item_img0,
				R.id.class_item_name0, R.id.class_item_id1, R.id.class_item_img1,
				R.id.class_item_name1, R.id.class_item_id2, R.id.class_item_img2,
				R.id.class_item_name2, R.id.class_item_id3, R.id.class_item_img3,
				R.id.class_item_name3 });

		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data, String textRepresentation) {
				// 判断是否为我们要处理的对象
				if (view instanceof XCRoundRectImageView && data instanceof Object) {
					if (!data.toString().equals("null")) {
						final XCRoundRectImageView iv = (XCRoundRectImageView) view;
						BitmapFactory.Options options = new BitmapFactory.Options();
						// options.inSampleSize = 2;
						Bitmap bm = BitmapFactory.decodeFile(AppContext.Local_static_IMG_PATH
								+ data.toString(), options);
						iv.setImageBitmap(bm);
						view.setOnClickListener(onClickListener);
					} else {
						LinearLayout listItem = (LinearLayout) view.getParent();
						listItem.setVisibility(View.INVISIBLE);
					}

					// iv.setImageBitmap((Bitmap) data);

					return true;
				} else
					return false;
			}

		}

		);

		classContentListView.setAdapter(adapter);
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			LinearLayout parent = (LinearLayout) v.getParent();
			tv0 = (TextView) parent.findViewById(R.id.class_item_id0);
			tv1 = (TextView) parent.findViewById(R.id.class_item_id1);
			tv2 = (TextView) parent.findViewById(R.id.class_item_id2);
			tv3 = (TextView) parent.findViewById(R.id.class_item_id3);
			int flowerId = 0;

			switch (v.getId()) {
			case R.id.class_item_img0:
				flowerId = Integer.parseInt(tv0.getText().toString());
				break;
			case R.id.class_item_img1:
				flowerId = Integer.parseInt(tv1.getText().toString());
				break;
			case R.id.class_item_img2:
				flowerId = Integer.parseInt(tv2.getText().toString());
				break;
			case R.id.class_item_img3:
				flowerId = Integer.parseInt(tv3.getText().toString());
				break;

			default:
				break;
			}

			Intent it = new Intent(FlowerClassActivity.this, FlowerClassShowActivity.class);
			it.putExtra("id", flowerId);
			startActivity(it);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flower_class, menu);
		return true;
	}

}
