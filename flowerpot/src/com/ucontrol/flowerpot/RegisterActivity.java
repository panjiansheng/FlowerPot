package com.ucontrol.flowerpot;

import com.google.gson.Gson;
import com.ucontrol.flowerpot.common.SharePreferenceHelper;
import com.ucontrol.flowerpot.httpconnection.HttpConnect;
import com.ucontrol.flowerpot.po.ServerResponse;
import com.ucontrol.flowerpot.widgets.XCRoundRectImageView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText userIdEditText;
	private EditText userPasswordEditText;
	private EditText userPasswordSecondEditText;
	private XCRoundRectImageView userIconImageView;
	private Button loginButton;
	private String userId;
	private String userPassword;
	private String userPasswordSecond;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		userIdEditText = (EditText) findViewById(R.id.userId);
		userPasswordEditText = (EditText) findViewById(R.id.userPassword);
		userPasswordSecondEditText=(EditText)findViewById(R.id.userPasswordSecond);
		loginButton = (Button) findViewById(R.id.btnLogin);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_action_back:
			finish();
			break;
		case R.id.btnRegister:
			userId=userIdEditText.getText().toString();
			userPassword=userPasswordEditText.getText().toString();
			userPasswordSecond=userPasswordSecondEditText.getText().toString();
			if (userPassword.equals(userPasswordSecond)) {
				AsycLoader asycLoader=new AsycLoader();
				asycLoader.execute("null");
			}else {
				Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}
	
	
	private class AsycLoader extends AsyncTask<String, Integer, Object> {

		Gson gson = new Gson();
		SharePreferenceHelper sharePreferenceHelper = new SharePreferenceHelper();
		ServerResponse serverResponse;
		String result;

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			result = HttpConnect.register(userId, userPassword);
			if (result==null) {
				return false;
			}
			serverResponse = gson.fromJson(result, ServerResponse.class);
			return true;
		}

		protected void onPostExecute(Object object) {
			if ((Boolean)object) {
				if (serverResponse.getResult().equals("1")) {
					sharePreferenceHelper.setUserId(userId);
					sharePreferenceHelper.setUserPassword(userPassword);
					
					sharePreferenceHelper.setUserServerId(serverResponse.getResult());
					HttpConnect httpConnect=new HttpConnect();
					Intent it = new Intent(RegisterActivity.this, MainActivity.class);
					startActivity(it);
				} else {
					Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
				}
			}else {
				Toast.makeText(RegisterActivity.this, "联网失败", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
