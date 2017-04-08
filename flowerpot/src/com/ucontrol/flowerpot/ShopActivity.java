package com.ucontrol.flowerpot;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ShopActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop, menu);
		return true;
	}
	
	public void onClick(View view) {
		Intent it=new Intent(this,TaobaoActivity.class);
		it.putExtra("url", "http://shop115752229.taobao.com");
		startActivity(it);
	}

}
