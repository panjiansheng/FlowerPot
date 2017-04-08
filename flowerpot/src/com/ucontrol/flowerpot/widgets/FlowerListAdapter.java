package com.ucontrol.flowerpot.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.AppContext;

import com.ucontrol.flowerpot.FlowerClassActivity;
import com.ucontrol.flowerpot.FlowerClassShowActivity;
import com.ucontrol.flowerpot.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FlowerListAdapter extends BaseAdapter{

	Context mContext;
	List<Map<String, Object>> mapList;
	  private LayoutInflater mInflater;
	
	public FlowerListAdapter(Activity mContext, List<Map<String, Object>>  mapList) {
		this.mContext = mContext;
		this.mapList= mapList;
		this.mInflater=mContext.getLayoutInflater();

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mapList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {

			holder = new ViewHolder();

			// 可以理解为从vlist获取view 之后把view返回给ListView
			convertView = mInflater.inflate(R.layout.flower_class_item, null);
			holder.class_item_img0 = (ImageView) convertView
					.findViewById(R.id.class_item_img0);
			holder.class_item_id0 = (TextView) convertView
					.findViewById(R.id.class_item_id0);
			holder.class_item_name0 = (TextView) convertView
					.findViewById(R.id.class_item_name0);
			holder.class_item_img1 = (ImageView) convertView
					.findViewById(R.id.class_item_img1);
			holder.class_item_id1 = (TextView) convertView
					.findViewById(R.id.class_item_id1);
			holder.class_item_name1 = (TextView) convertView
					.findViewById(R.id.class_item_name1);
			holder.class_item_img2 = (ImageView) convertView
					.findViewById(R.id.class_item_img2);
			holder.class_item_id2 = (TextView) convertView
					.findViewById(R.id.class_item_id2);
			holder.class_item_name2 = (TextView) convertView
					.findViewById(R.id.class_item_name2);
			holder.class_item_img3 = (ImageView) convertView
					.findViewById(R.id.class_item_img3);
			holder.class_item_id3 = (TextView) convertView
					.findViewById(R.id.class_item_id3);
			holder.class_item_name3 = (TextView) convertView
					.findViewById(R.id.class_item_name3);
			holder.class_item_ly0 = (LinearLayout) convertView
					.findViewById(R.id.class_item_ly0);
			holder.class_item_ly1 = (LinearLayout) convertView
					.findViewById(R.id.class_item_ly1);
			holder.class_item_ly2 = (LinearLayout) convertView
					.findViewById(R.id.class_item_ly2);
			holder.class_item_ly3 = (LinearLayout) convertView
					.findViewById(R.id.class_item_ly3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.class_item_id0.setText(mapList.get(position).get("id0").toString());
		holder.class_item_id1.setText(mapList.get(position).get("id1").toString());
		holder.class_item_id2.setText(mapList.get(position).get("id2").toString());
		holder.class_item_id3.setText(mapList.get(position).get("id3").toString());
		holder.class_item_name0.setText(mapList.get(position).get("name0").toString());
		holder.class_item_name1.setText(mapList.get(position).get("name1").toString());
		holder.class_item_name2.setText(mapList.get(position).get("name2").toString());
		holder.class_item_name3.setText(mapList.get(position).get("name3").toString());

		
		
		Bitmap bm;
		BitmapFactory.Options options = null;

		if (!mapList.get(position).get("img0").toString().equals("null")) {
			options = new BitmapFactory.Options();
			bm = BitmapFactory.decodeFile(
					AppContext.Local_static_IMG_PATH
							+ mapList.get(position).get("img0").toString(), options);
			holder.class_item_img0.setImageBitmap(bm);
			options = new BitmapFactory.Options();
		} else {
			//holder.class_item_ly0.setVisibility(View.INVISIBLE);
		}
		if (!mapList.get(position).get("img1").toString().equals("null")) {

			bm = BitmapFactory.decodeFile(
					AppContext.Local_static_IMG_PATH
							+ mapList.get(position).get("img1").toString(), options);
			holder.class_item_img1.setImageBitmap(bm);
		} else {
			//holder.class_item_ly1.setVisibility(View.INVISIBLE);
		}
		if (!mapList.get(position).get("img2").toString().equals("null")) {
			options = new BitmapFactory.Options();

			bm = BitmapFactory.decodeFile(
					AppContext.Local_static_IMG_PATH
							+ mapList.get(position).get("img2").toString(), options);
			holder.class_item_img2.setImageBitmap(bm);
		} else {
			//holder.class_item_ly2.setVisibility(View.INVISIBLE);
		}
		if (!mapList.get(position).get("img3").toString().equals("null")) {
			options = new BitmapFactory.Options();
			bm = BitmapFactory.decodeFile(
					AppContext.Local_static_IMG_PATH
							+ mapList.get(position).get("img3").toString(), options);
			holder.class_item_img3.setImageBitmap(bm);
		} else {
			holder.class_item_ly3.setVisibility(View.INVISIBLE);
		}
		
		if (mapList.get(position).get("name0").toString().equals("铜线草")) {
			holder.class_item_ly0.setVisibility(View.INVISIBLE);
		}

		return convertView;

	}




	}
final class ViewHolder {

	public ImageView class_item_img0;
	public TextView class_item_name0;
	public TextView class_item_id0;
	public ImageView class_item_img1;
	public TextView class_item_name1;
	public TextView class_item_id1;
	public ImageView class_item_img2;
	public TextView class_item_name2;
	public TextView class_item_id2;
	public ImageView class_item_img3;
	public TextView class_item_name3;
	public TextView class_item_id3;
	public LinearLayout class_item_ly1;
	public LinearLayout class_item_ly2;
	public LinearLayout class_item_ly3;
	public LinearLayout class_item_ly0;
}


