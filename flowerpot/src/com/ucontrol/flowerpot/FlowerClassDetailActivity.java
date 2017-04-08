package com.ucontrol.flowerpot;

import util.AppContext;

import com.ucontrol.flowerpot.data.StaticSqliteControl;
import com.ucontrol.flowerpot.po.FlowerClassContent;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FlowerClassDetailActivity extends Activity {

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
		setContentView(R.layout.activity_flower_class_detail);
		staticSqliteControl=new StaticSqliteControl();
	flowerClassContent=staticSqliteControl.getFlowerClassContentById(getIntent().getIntExtra("id", 0));
	staticSqliteControl.close();
	((TextView)findViewById(R.id.flower_name)).setText(flowerClassContent.getName());
	((TextView)findViewById(R.id.flower_word)).setText(flowerClassContent.getWord());
	ImageView iv=(ImageView)findViewById(R.id.flower_img);
	BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inSampleSize = 2;
	Bitmap bm = BitmapFactory.decodeFile(
			AppContext.Local_static_IMG_PATH + flowerClassContent.getName()+".jpg",
			options);
	iv.setImageBitmap(bm);
	switch (getIntent().getIntExtra("type", 0)) {
	case 1:
		((TextView)findViewById(R.id.titleWord)).setText("植物形态");
		((TextView)findViewById(R.id.flower_info)).setText("     "+flowerClassContent.getXingtai());
		break;
	case 2:
		((TextView)findViewById(R.id.titleWord)).setText("主治功能");
		((TextView)findViewById(R.id.flower_info)).setText("     "+flowerClassContent.getGongneng());
		break;
	case 3:
		((TextView)findViewById(R.id.titleWord)).setText("生态习性");
		((TextView)findViewById(R.id.flower_info)).setText("     "+flowerClassContent.getXixing());
		break;
	case 4:
		((TextView)findViewById(R.id.titleWord)).setText("栽培细节");
		((TextView)findViewById(R.id.flower_info)).setText("     "+flowerClassContent.getZaipei());
		break;
	case 5:
		((TextView)findViewById(R.id.titleWord)).setText("养护方法");
		((TextView)findViewById(R.id.flower_info)).setText("     "+flowerClassContent.getYanghu());
		break;

	default:
		break;
	}
	
	}

	public void onClick(View view){
		
		finish();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flower_class_detail, menu);
		return true;
	}

}
