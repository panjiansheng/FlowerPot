package com.ucontrol.flowerpot;

import util.AppContext;

import com.google.gson.Gson;
import com.ucontrol.flowerpot.common.SharePreferenceHelper;
import com.ucontrol.flowerpot.httpconnection.HttpConnect;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.po.ServerResponse;
import com.ucontrol.flowerpot.widgets.MyAlertDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FlowerDetailActivity extends Activity {

	private float rh;
	private float ph;
	private float temp;
	private ProgressBar tempBar;
	private ProgressBar rhBar;
	private ProgressBar phBar;
	private FlowerState flowerState;
	private TextView tempTv;
	private TextView rhTv;
	private TextView phTv;
	private ImageView flowerIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flower_detail);
		tempBar = (ProgressBar) findViewById(R.id.tempBar);
		rhBar = (ProgressBar) findViewById(R.id.rhBar);
		phBar = (ProgressBar) findViewById(R.id.phBar);
		flowerIv=(ImageView)findViewById(R.id.image);
		Gson gson = new Gson();
		flowerState = gson.fromJson(getIntent().getStringExtra("flower_detail"), FlowerState.class);
		((TextView) findViewById(R.id.name)).setText(flowerState.getName());
		temp = Float.parseFloat(flowerState.getTemperature());
		rh = Float.parseFloat(flowerState.getHumidity());
		ph = Float.parseFloat(flowerState.getPh());
		tempBar.setProgress((int) (temp));
		rhBar.setProgress((int) (rh));
		phBar.setProgress((int) (ph * 10));
		tempTv = (TextView) findViewById(R.id.temp);
		rhTv = (TextView) findViewById(R.id.rh);
		phTv = (TextView) findViewById(R.id.ph);
		tempTv.setText(flowerState.getTemperature());
		rhTv.setText(flowerState.getHumidity() + "%");
		phTv.setText(flowerState.getPh());
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		Bitmap bm = BitmapFactory.decodeFile(
				AppContext.LOCAL_IMG_PATH + flowerState.getImagepath(),
				options);
		flowerIv.setImageBitmap(bm);
		

	}

	public void onClick(View view) {
		final MyAlertDialog ad = new MyAlertDialog(this);
		final AsycLoader asycLoader = new AsycLoader();

		switch (view.getId()) {
		case R.id.btnWater:

			ad.setTitle("确认");
			ad.setMessage("确定要给" + flowerState.getName() + "浇水吗？");
			ad.setPositiveButton("确定", new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ad.dismiss();
					asycLoader.execute("0");

				}
			});

			ad.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ad.dismiss();

				}
			});
			break;
		case R.id.btnFurtilize:

			ad.setTitle("确认");
			ad.setMessage("确定要给" + flowerState.getName() + "施肥吗？");
			ad.setPositiveButton("确定", new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ad.dismiss();
					asycLoader.execute("1");

				}
			});

			ad.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ad.dismiss();

				}
			});
			break;
		default:
			break;
		}
	}

	private class AsycLoader extends AsyncTask<String, Integer, Object> {

		Gson gson = new Gson();
		ServerResponse serverResponse;
		String result;

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			switch (Integer.parseInt(params[0])) {
			case 0:
				result = HttpConnect.water(flowerState.getFlowerid());
				if (result == null) {
					return false;
				}
				serverResponse = gson.fromJson(result, ServerResponse.class);

				break;
			case 1:
				result = HttpConnect.furtilize(flowerState.getFlowerid());
				if (result == null) {
					return false;
				}
				serverResponse = gson.fromJson(result, ServerResponse.class);

				break;
			default:
				break;
			}

			return true;

		}

		protected void onPostExecute(Object object) {
			if ((Boolean)object) {
				if (serverResponse.getResult().equals("1")) {
					Toast.makeText(FlowerDetailActivity.this, "操作成功", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(FlowerDetailActivity.this, "操作失败", Toast.LENGTH_LONG).show();
				}
			}else {
				Toast.makeText(FlowerDetailActivity.this, "联网失败", Toast.LENGTH_LONG).show();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flower_detail, menu);
		return true;
	}

}
