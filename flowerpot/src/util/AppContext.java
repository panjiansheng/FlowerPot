package util;



import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

/**
 * ȫ��Ӧ�ó����ࣺ���ڱ���͵���ȫ��Ӧ������
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
         
         JPushInterface.setDebugMode(true); 	// ���ÿ�����־,����ʱ��ر���־
         JPushInterface.init(this);     		// ��ʼ�� JPush
         instance = this;
    }
	
	public static AppContext getInstance() {
		return instance;
	}
    
	/**
	 * ���SD��״̬
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
	 * ��������Ƿ����
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * ��ȡ��ǰ��������
	 * 
	 * @return 0��û������ 1��WIFI���� 2���ƶ�WAP���� 3������WAP���� 4��NET����
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
