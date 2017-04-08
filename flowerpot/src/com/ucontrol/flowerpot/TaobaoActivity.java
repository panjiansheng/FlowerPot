package com.ucontrol.flowerpot;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class TaobaoActivity extends Activity {

	private WebView mWebView;
	private ProgressBar loadingProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_taobao);
	
		mWebView = (WebView) findViewById(R.id.webView);
		loadingProgressBar=(ProgressBar)findViewById(R.id.progressBar);
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);
		mWebView.requestFocus();
		//mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.loadUrl(getIntent().getStringExtra("url"));
		WebViewClient client = new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

		};
		mWebView.setWebChromeClient(new WebChromeClient(){
			
			public void onProgressChanged(WebView view, int progress) {
				loadingProgressBar.setProgress(progress);
				if (progress == 100) {
					//loadingProgressBar.setVisibility(View.INVISIBLE);
				}
			}
			
		});
		mWebView.setWebViewClient(client);
		//点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)  
        mWebView.setOnKeyListener(new View.OnKeyListener() {  


			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
                if (event.getAction() == KeyEvent.ACTION_DOWN) {  
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键


                        mWebView.goBack();   //后退  

                        //webview.goForward();//前进
                        return true;    //已处理  
                    }  
                }  
                
				return false;
			}  
        });

	}
	
	public void onClick(View v) {

		switch (v.getId()) {

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
		getMenuInflater().inflate(R.menu.taobao, menu);
		return true;
	}

}
