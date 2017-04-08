package util;



import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

/**
 * 全局应用程序类：用于保存和调用全局应用配置
 * 
 * @author zhibinzeng
 * @version 1.0
 * @created 2013-1-25
 */
public class AppContext extends Application {

	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CTWAP = 0x03;
	public static final int NETTYPE_CMNET = 0x04;
	private static final String TAG = "JPush";
	private static AppContext instance;
	public static final String SERVER_IMG_PATH="/images/index/";
	public static final String LOCAL_ALLDATA_PATH=android.os.Environment
			.getExternalStorageDirectory() + "/FlowerPot";
	public static final String LOCAL_IMG_PATH=LOCAL_ALLDATA_PATH+ "/images/";
	public static final String LOCAL_DATABASE_PATH=LOCAL_ALLDATA_PATH+ "/FlowerPotApp.db";
	public static final String Local_static_DATABASE_PATH=LOCAL_ALLDATA_PATH+ "/static/FlowerPotStatic.db";
	public static final String Local_static_DATA_PATH=LOCAL_ALLDATA_PATH+ "/static/";
	public static final String Local_static_IMG_PATH=LOCAL_ALLDATA_PATH+ "/static/images/";
	public static final String Local_static_IMGZIP_PATH=LOCAL_ALLDATA_PATH+ "/static/images/images.zip";
    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();
         
         JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
         JPushInterface.init(this);     		// 初始化 JPush
         instance = this;
    }
	
	public static AppContext getInstance() {
		return instance;
	}
    
	/**
	 * 检查SD卡状态
	 * 
	 * @return
	 */
	public static boolean isSDCardAlive() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：移动WAP网络 3：电信WAP网络 4：NET网络
	 */
	public int getNetworkType() {
		int netType = 0;
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = networkInfo.getExtraInfo();
			if (!StringUtil.isEmpty(extraInfo)) {
				if (extraInfo.toLowerCase().equals("cmnet")) {
					netType = NETTYPE_CMNET;
				} else if (extraInfo.toLowerCase().equals("ctwap")) {
					netType = NETTYPE_CTWAP;
				} else {
					netType = NETTYPE_CMWAP;
				}
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETTYPE_WIFI;
		}
		return netType;
	}

}
