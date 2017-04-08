package com.ucontrol.flowerpot;

import util.AppContext;

import com.ucontrol.flowerpot.data.StaticSqliteControl;
import com.ucontrol.flowerpot.po.FlowerClassContent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FlowerClassShowActivity extends Activity {

	private static int XINGTAI=1;
	private static int GONGNENG=2;
	private static int XIXING=3;
	private static int ZAIPEI=4;
	private static int YANGHU=5;
	private FlowerClassContent flowerClassContent;
	private StaticSqliteControl staticSqliteControl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flower_class_show);
		staticSqliteControl=new StaticSqliteControl();
	flowerClassContent=staticSqliteControl.getFlowerClassContentById(getIntent().getIntExtra("id", 0));
	((TextView)findViewById(R.id.flower_name)).setText(flowerClassContent.getName());
	((TextView)findViewById(R.id.flower_word)).setText(flowerClassContent.getWord());
	((TextView)findViewById(R.id.titleWord)).setText(flowerClassContent.getName());
	ImageView iv=(ImageView)findViewById(R.id.flower_img);
	BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inSampleSize = 2;
	Bitmap bm = BitmapFactory.decodeFile(
			AppContext.Local_static_IMG_PATH + flowerClassContent.getName()+".jpg",
			options);
	iv.setImageBitmap(bm);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flower_class_show, menu);
		return true;
	}

	
	public void onClick(View v) {
		Intent it=new Intent(this,FlowerClassDetailActivity.class);
		it.putExtra("id", getIntent().getIntExtra("id", 0));
		switch (v.getId()) {
		case R.id.xingtai:
			it.putExtra("type",XINGTAI);
			startActivity(it);
			break;
		case R.id.gongneng:
			it.putExtra("type",GONGNENG);
			startActivity(it);
			break;
		case R.id.xixing:
			it.putExtra("type",XIXING);
			startActivity(it);
			break;
		case R.id.zaipei:
			it.putExtra("type",ZAIPEI);
			startActivity(it);
			break;
		case R.id.yanghu:
			it.putExtra("type",YANGHU);
			startActivity(it);
			break;
		case R.id.btn_action_back:
			finish();
			break;
		default:
			break;
		}
		
		
	}
}
