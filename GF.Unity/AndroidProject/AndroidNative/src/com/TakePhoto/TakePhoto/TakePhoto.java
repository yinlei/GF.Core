package com.TakePhoto.TakePhoto;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.AndroidToUnityMsgBridge.AndroidToUnityMsgBridge.AndroidToUnityMsgBridge;

public class TakePhoto {
	// -------------------------------------------------------------------------
	public static TakePhoto mTakePhoto;
	private static AndroidToUnityMsgBridge mAndroidToUnityMsgBridge;	
	private Activity mUnityActivity;
	public static String mResultReceiver = "";
	public int mPhotoWidth = 150;
	public int mPhotoHeight = 150;
	public static String mSuccessMethodName = "";
	public static String mFailMethodName = "";
	public String mPhotoName = "";

	// public int QUALITY = 60;

	// -------------------------------------------------------------------------
	public static TakePhoto Instantce(int photo_width, int photo_height,
			String success_methodname, String fail_methodname,
			String photo_name, String pay_resultreceiver) {
		mResultReceiver = pay_resultreceiver;
		mSuccessMethodName = success_methodname;
		mFailMethodName = fail_methodname;
		if (mTakePhoto == null) {
			mAndroidToUnityMsgBridge = AndroidToUnityMsgBridge
					.Instance(mResultReceiver);
			mTakePhoto = new TakePhoto(photo_width, photo_height,
					success_methodname, fail_methodname, photo_name,
					pay_resultreceiver);
		}

		return mTakePhoto;
	}

	// -------------------------------------------------------------------------
	private TakePhoto(int photo_width, int photo_height,
			String success_methodname, String fail_methodname,
			String photo_name, String pay_resultreceiver) {
		this.mPhotoWidth = photo_width;
		this.mPhotoHeight = photo_height;
		this.mPhotoName = photo_name;
		mUnityActivity = mAndroidToUnityMsgBridge.getActivity();
	}

	// -------------------------------------------------------------------------
	public void takeExistPhoto() {
		if (mTakePhoto == null) {
			Log.e("TakePhoto", "takeExistPhoto::mTakePhoto::NUll");
		} else {
			Log.e("TakePhoto", "takeExistPhoto::mTakePhoto::NotNUll");
		}
		Intent intent = new Intent(mUnityActivity, TakePhotoActivity.class);
		intent.putExtra("PhotoWidth", mPhotoWidth);
		intent.putExtra("PhotoHeight", mPhotoHeight);
		intent.putExtra("PhotoName", mPhotoName);
		intent.putExtra("TakePhotoType", _ERESULT.getPicFromPicture.toString());		
		mUnityActivity.startActivity(intent);
	}

	// -------------------------------------------------------------------------
	public void takeNewPhoto() {
		if (mTakePhoto == null) {
			Log.e("TakePhoto", "takeNewPhoto::mTakePhoto::NUll");
		} else {
			Log.e("TakePhoto", "takeNewPhoto::mTakePhoto::NotNUll");
		}
		Intent intent = new Intent(mUnityActivity, TakePhotoActivity.class);
		intent.putExtra("PhotoWidth", mPhotoWidth);
		intent.putExtra("PhotoHeight", mPhotoHeight);
		intent.putExtra("PhotoName", mPhotoName);
		intent.putExtra("TakePhotoType", _ERESULT.getPicFromCamera.toString());
		mUnityActivity.startActivity(intent);
	}

	// -------------------------------------------------------------------------
	public static void sendToUnity(Boolean is_success, String info) {
		if (mTakePhoto == null) {
			Log.e("TakePhoto", "sendToUnity::mTakePhoto::NUll");
		} else {
			Log.e("TakePhoto", "sendToUnity::mTakePhoto::NotNUll");
		}

		if (mAndroidToUnityMsgBridge == null) {
			Log.e("TakePhoto", "sendToUnity::mAndroidToUnityMsgBridge::NUll");
			mAndroidToUnityMsgBridge = AndroidToUnityMsgBridge
					.Instance(mResultReceiver);
		}
		if (is_success) {
			Log.e("TakePhoto", "sendToUnity::is_success::" + info);
			mAndroidToUnityMsgBridge.sendMsgToUnity(mSuccessMethodName, info);
		} else {
			Log.e("TakePhoto", "sendToUnity::is_fail::");
			mAndroidToUnityMsgBridge.sendMsgToUnity(mFailMethodName, "");
		}
	}

	// -------------------------------------------------------------------------
	public static void sendToUnityPic(Uri pic_uri) {
		Log.e("TakePhoto", "sendToUnityPic");

		getPicStringThread(pic_uri);
		// _decodeUriAsBitmap(pic_uri);
	}

	// //
	// -------------------------------------------------------------------------
	// private static Bitmap _decodeUriAsBitmap(Uri uri) {
	//
	// Bitmap bitmap = null;
	//
	// try {
	// Log.e("TakePhoto", "_decodeUriAsBitmap");
	// bitmap = BitmapFactory.decodeFile(uri.getPath());
	// Log.e("TakePhoto", "_decodeUriAsBitmap111");
	// // .decodeStream(mAndroidToUnityMsgBridge.mUnityPlayerActivity
	// // .getContentResolver().openInputStream(uri));
	// // 创建一个字节数组输出流,流的大小为size
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
	// bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	// // 将字节数组输出流转化为字节数组byte[]
	// byte[] imagedata = baos.toByteArray();
	//
	// String icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
	// Log.e("TakePhotoActivity", "icon:: " + icon);
	// sendToUnity(true, icon);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	//
	// return bitmap;
	// }

	// -------------------------------------------------------------------------
	private static void _decodeUriAsBitmap(Uri uri) {

		Bitmap bitmap = null;

		try {
			Log.e("TakePhotoActivity", "_decodeUriAsBitmap");
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;
			bitmap = BitmapFactory.decodeStream(
					mAndroidToUnityMsgBridge.mUnityPlayerActivity
							.getContentResolver().openInputStream(uri), null,
					options);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		_picToString(bitmap);
	}

	// -------------------------------------------------------------------------
	private static void _picToString(Bitmap photo) {
		String icon = "";
		try {
			// File file = new File(photo.);

			// 创建一个字节数组输出流,流的大小为size
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
			photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
			// 将字节数组输出流转化为字节数组byte[]
			byte[] imagedata = baos.toByteArray();
			Log.e("TakePhotoActivity", "imagedata：：Length::" + imagedata.length);
			icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		sendToUnity(true, icon);
		Log.e("TakePhotoActivity", "icon:: " + icon);
	}

	// -------------------------------------------------------------------------
	private static void getPicStringThread(final Uri image_uri) {
		(new Thread(new Runnable() {
			public void run() {
				_decodeUriAsBitmap(image_uri);
			}
		})).start();
	}

	// ----------------------------------------------------------------------
	// 返回消息类型
	public enum _ERESULT {
		getPicFailed, getPicFromCamera, getPicFromPicture, getPicFromCameraSuccess, getPicFromPictureSuccess
	}
}
