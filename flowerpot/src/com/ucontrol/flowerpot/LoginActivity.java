package com.ucontrol.flowerpot;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.ucontrol.flowerpot.common.SharePreferenceHelper;
import com.ucontrol.flowerpot.data.SqliteControl;
import com.ucontrol.flowerpot.data.UpdateSqliteDataFromServer;
import com.ucontrol.flowerpot.httpconnection.HttpConnect;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.po.ServerResponse;
import com.ucontrol.flowerpot.widgets.XCRoundRectImageView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText userIdEditText;
	private EditText userPasswordEditText;
	private XCRoundRectImageView userIconImageView;
	private Button loginButton;
	private String userId;
	private String userPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userIdEditText = (EditText) findViewById(R.id.userId);
		userPasswordEditText = (EditText) findViewById(R.id.userPassword);
		loginButton = (Button) findViewById(R.id.btnLogin);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	private class AsycLoader extends AsyncTask<String, Integer, Object> {

		Gson gson = new Gson();
		SharePreferenceHelper sharePreferenceHelper = new SharePreferenceHelper();
		ServerResponse serverResponse;
		String result;

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			result = HttpConnect.login(userId, userPassword);
			if (result==null) {
				return false;
			}
			serverResponse = gson.fromJson(result, ServerResponse.class);
			return true;
		}

		protected void onPostExecute(Object object) {
			if ((Boolean)object) {
				if (!serverResponse.getResult().equals("0")) {
					sharePreferenceHelper.setUserId(userId);
					sharePreferenceHelper.setUserPassword(userPassword);
					
					sharePreferenceHelper.setUserServerId(serverResponse.getResult());
					HttpConnect httpConnect=new HttpConnect();
					((LinearLayout)findViewById(R.id.showLoading)).setVisibility(View.GONE);
					Intent it = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(it);
				} else {
					Toast.makeText(LoginActivity.this, "µÇÂ¼Ê§°Ü", Toast.LENGTH_LONG).show();
				}
			}else {
				Toast.makeText(LoginActivity.this, "ÁªÍøÊ§°Ü", Toast.LENGTH_LONG).show();
			}
			((LinearLayout)findViewById(R.id.showLoading)).setVisibility(View.GONE);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin:
			userId = userIdEditText.getText().toString();
			userPassword = userPasswordEditText.getText().toString();
			AsycLoader asycLoader = new AsycLoader();
			asycLoader.execute("null");
			((LinearLayout)findViewById(R.id.showLoading)).setVisibility(View.VISIBLE);
			break;
		case R.id.btnRegister1:
			Intent it=new Intent(this,RegisterActivity.class);
			startActivity(it);
			
			break;
		default:
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (((LinearLayout)findViewById(R.id.showLoading)).getVisibility()==View.VISIBLE) {
				((LinearLayout)findViewById(R.id.showLoading)).setVisibility(View.GONE);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);

	}
}
