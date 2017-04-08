package com.ucontrol.flowerpot;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

import util.AppContext;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ISayActivity extends Activity {

	
	
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private ImageView imageView = null;
    private Button btnPhone = null;
    private Button btnTakePicture = null;
    private String imgPath;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_isay);
		//imageView=new ImageView(this);
		Random rand=new Random(10000);
		//imageView=(ImageView)findViewById(R.id.imgShow);
		imgPath=AppContext.LOCAL_IMG_PATH+String.valueOf((int)(Math.random()*10000+1))+".jpg";
	}

	
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_action_back:
			finish();
			break;
		case R.id.btnAddImage:
			((View)findViewById(R.id.LyphotoMenu)).setVisibility(View.VISIBLE);
			
			break;
		case R.id.btnPhone:
	        intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
            startActivityForResult(intent, PHOTO_ZOOM);
			break;
		case R.id.btnTakePicture:
			
		     intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgPath)));
             startActivityForResult(intent, PHOTO_GRAPH);
			break;
		case R.id.btnCancelPhoto:
			((View)findViewById(R.id.LyphotoMenu)).setVisibility(View.INVISIBLE);
			
			break;
			
		case R.id.btn_share:
//		      intent = new Intent(Intent.ACTION_SEND);  
//		        if (imgPath == null || imgPath.equals("")) {  
//		            intent.setType("text/plain"); // 纯文本  
//		        } else {  
//		            File f = new File(imgPath);  
//		            if (f != null && f.exists() && f.isFile()) {  
//		                intent.setType("image/jpg");  
//		              Uri u = Uri.fromFile(f);  
//		                intent.putExtra(Intent.EXTRA_STREAM, u);  
//		            }  
//		        }  
//		        intent.putExtra(Intent.EXTRA_SUBJECT, "分享照片");  
//		        intent.putExtra(Intent.EXTRA_TEXT, "我最近的成果");  
//		        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//		        startActivity(Intent.createChooser(intent, "分享智能花盆"));
//			Intent it = new Intent(Intent.ACTION_SEND);
//			it.setType("text/plain");
//			it.putExtra(Intent.EXTRA_SUBJECT, "分享交大YOUTH");
//			it.putExtra(Intent.EXTRA_TEXT, "我下载了交大YOUTH客户端，感觉很好用哦，下载地址：http://youth.swjtu.edu.cn/develops/handsSwjtu/youth.apk");
//			startActivity(Intent.createChooser(it, "分享交大YOUTH"));
			 
			Intent  intent1 = new Intent(Intent.ACTION_SEND);  
		        if (imgPath == null || imgPath.equals("")) {  
		            intent1.setType("text/plain"); // 纯文本  
		        } else {  
		            File f = new File(imgPath);  
		            if (f != null && f.exists() && f.isFile()) {  
		                intent1.setType("image/jpg");  
		              Uri u = Uri.fromFile(f);  
		                intent1.putExtra(Intent.EXTRA_STREAM, u);  
		            }  
		        }  
		        intent1.putExtra(Intent.EXTRA_SUBJECT, "分享照片");  
		        intent1.putExtra(Intent.EXTRA_TEXT, "我最近的成果");  
		        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		        startActivity(Intent.createChooser(intent1, "分享智能花盆"));
			break;
		default:
			break;
		}
	}
	

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE)
            return;
        // 拍照
        if (requestCode == PHOTO_GRAPH) {
            // 设置文件保存路径
            File picture = new File(imgPath);
            startPhotoZoom(Uri.fromFile(picture));
        }

        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTO_RESOULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
//                Bitmap photo = extras.getParcelable("data");
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap photo = BitmapFactory.decodeFile(
						imgPath,
						options);
            	
            	ImageView imageView=new ImageView(this);
                imageView.setImageBitmap(photo); //把图片显示在ImageView控件上
				LayoutParams lp = new LinearLayout.LayoutParams(150,
						LayoutParams.MATCH_PARENT);
				lp.rightMargin = 5;
				lp.leftMargin = 20;
				imageView.setLayoutParams(lp);
				LinearLayout view=(LinearLayout)findViewById(R.id.lyImageShow);
				view.addView(imageView);
				((View)findViewById(R.id.LyphotoMenu)).setVisibility(View.INVISIBLE);
				
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 收缩图片
     * 
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgPath)));
     
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        
        startActivityForResult(intent, PHOTO_RESOULT);
    }


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.isay, menu);
		return true;
	}

}
