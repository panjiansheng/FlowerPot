package com.ucontrol.flowerpot.httpconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ucontrol.flowerpot.common.SharePreferenceHelper;
import com.ucontrol.flowerpot.po.FlowerState;
import com.ucontrol.flowerpot.po.FriendReply;
import com.ucontrol.flowerpot.po.FriendSay;
import com.ucontrol.flowerpot.po.ServerResponse;

import util.AppContext;
import android.util.Log;

/**
 * @author pan,jiansheng
 * @Date 2013-1-31
 * @version v1.0
 * @Description Connect to server to fetch data via HTTP
 * 
 */
public class HttpConnect {

	private static int WapFlag;
	private static CookieStore cookieStore;
	public static final String BASE_PATH = "http://120.27.39.233/balcony/";
	private static final String IMAGE_PATH = "http://www.panjiansheng.info:8080/FlowerPot/images/";

	private static String COMMON_STRING;

	/*
	 * 构造函数
	 */
	public HttpConnect() {
		SharePreferenceHelper sharePreferenceHelper = new SharePreferenceHelper();
		this.COMMON_STRING = "?user_id=" + sharePreferenceHelper.getUserServerId()
				+ "&user_password=" + sharePreferenceHelper.getUserPassword() + "&";
	}

	/*
	 * 获取所有新闻分类
	 */

	public static String getFlowerStates() {
		// COMMON_STRING="?user_id="+sharePreferenceHelper.getUserServerId()+"&user_password="+sharePreferenceHelper.getUserPassword()+"&";
		String urlString = BASE_PATH + "balcony_listdataforad" + COMMON_STRING;
		String result =null;
		JSONObject jsonObject;
		try {
			 result = getData(urlString);
			jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			result =jsonArray.toString();
			result = result.replace("plantId", "flowerid").replace("plantname", "name").replace("imagePath", "imagepath").replace("images\\/index\\/", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	

	
	public static String getFriendRelies(String serverid) {
		String urlString=IMAGE_PATH+"getFlowerStates.php";
		//return getData(urlString);
		Gson gson = new Gson();
		List<FriendReply> friendReplies = new ArrayList<FriendReply>();
		for (int i = 0; i < 10; i++) {
		     FriendReply p = new FriendReply( "2344", "sq", "head.png","界面滚动式空间谈出",
						"2013-14-6 12:36:45");

		     friendReplies.add(p);
		}
		String str = gson.toJson(friendReplies);
		return str;
	}
	

	
	
	public static String getFlowerStateDetail() {
		String urlString = IMAGE_PATH + "getFlowerStates.php";
		// return getData(urlString);
		Gson gson = new Gson();
		List<FlowerState> flowerStates = new ArrayList<FlowerState>();
		for (int i = 0; i < 2; i++) {
			FlowerState p = new FlowerState(1, "2344", "1.jpg", "1.png", "sagsd", "sagsd", "sagasg"
					+ i);

			flowerStates.add(p);
		}
		String str = gson.toJson(flowerStates);
		return str;
	}

	public static String getFriendSays(String type,String pageno) {
		String urlString = IMAGE_PATH + "getFlowerSays.php";
		// return getData(urlString);
		Gson gson = new Gson();
		List<FriendSay> friendSays = new ArrayList<FriendSay>();
		for (int i = 0; i < 5; i++) {
			FriendSay p = new FriendSay("2344", "pjs", "head.png", "head.png", Arrays.asList(new String[]{}) ,"235",
					"2013-12-14 22:05:05" ,type);

			friendSays.add(p);
		}
		String str = gson.toJson(friendSays);
		return str;
	}

	public static String login(String userId, String UserPassword) {
		String urlString = BASE_PATH + "login_loginforad?user_name=" + userId + "&user_password="
				+ UserPassword;
		return getData(urlString);
	}

	public static String register(String userId, String UserPassword) {
		String urlString = BASE_PATH + "login_registerforad?user_name=" + userId
				+ "&user_password=" + UserPassword;
		return getData(urlString);
	}

	public static String water(String serverid) {
		String urlString = BASE_PATH + "balcony_adsenddata" + COMMON_STRING + "plantId=" + serverid
				+ "&water=10";
		return getData(urlString);
	}

	public static String furtilize(String serverid) {
		String urlString = BASE_PATH + "balcony_adsenddata" + COMMON_STRING + "plantId=" + serverid
				+ "&fertilizer=10";
		return getData(urlString);
	}

	public static String postData(String url, List<NameValuePair> params) {
		String result = null;
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		WapFlag = AppContext.getInstance().getNetworkType();
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		if (WapFlag == 2) {
			HttpHost proxy = new HttpHost("10.0.0.172", 80);// 设置cmwap代理
			defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		} else if (WapFlag == 3) {
			HttpHost proxy = new HttpHost("10.0.0.200", 80);// 设置cmwap代理
			defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		if (cookieStore != null) {
			defaultHttpClient.setCookieStore(cookieStore);
		}

		try {
			request.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = defaultHttpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				// Toast.makeText(ContextUtil.getInstance(), result,
				// Toast.LENGTH_SHORT).show();
				cookieStore = ((AbstractHttpClient) defaultHttpClient).getCookieStore();
				Log.i("result", result);
			} else {
				result = null;
			}
		} catch (Exception e) {
			Log.i("result", e.toString());
			result = null;
		} finally {
			return result;
		}

	}

	public static String getData(String url) {
		String result = null;
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		WapFlag = AppContext.getInstance().getNetworkType();
		// request.setHeader("Host", "youth.swjtu.edu.cn");
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		if (WapFlag == 2) {
			HttpHost proxy = new HttpHost("10.0.0.172", 80);// 设置cmwap代理
			defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		} else if (WapFlag == 3) {
			HttpHost proxy = new HttpHost("10.0.0.200", 80);// 设置cmwap代理
			defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		if (cookieStore != null) {
			defaultHttpClient.setCookieStore(cookieStore);
		}
		try {
			HttpResponse response = defaultHttpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity());
				// Toast.makeText(ContextUtil.getInstance(), result,
				// Toast.LENGTH_SHORT).show();
				cookieStore = ((AbstractHttpClient) defaultHttpClient).getCookieStore();
				Log.i("result", result);
			} else {
				result = null;
			}
		} catch (Exception e) {
			Log.i("result", e.toString());
			result = null;
		} finally {
			return result;
		}

	}

	public static InputStream getHttpFile(String urlWaitToGet) {
		String url = BASE_PATH + urlWaitToGet;
		InputStream result = null;
		DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		WapFlag = AppContext.getInstance().getNetworkType();
		// request.setHeader("Host", "youth.swjtu.edu.cn");
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		if (WapFlag == 2) {
			HttpHost proxy = new HttpHost("10.0.0.172", 80);// 设置cmwap代理
			defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		} else if (WapFlag == 3) {
			HttpHost proxy = new HttpHost("10.0.0.200", 80);// 设置cmwap代理
			defaultHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		if (cookieStore != null) {
			defaultHttpClient.setCookieStore(cookieStore);
		}
		try {
			HttpResponse response = defaultHttpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = response.getEntity().getContent();
				cookieStore = ((AbstractHttpClient) defaultHttpClient).getCookieStore();

			} else {
				result = null;
			}
		} catch (Exception e) {
			Log.i("result", e.toString());
			result = null;
		} finally {
			return result;
		}

	}

}