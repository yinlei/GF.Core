package com.TakePhoto.TakePhoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.graphics.BitmapFactory;

public class TakePhotoActivity extends Activity {

	// -------------------------------------------------------------------------
	private static final String IMAGE_UNSPECIFIED = "image/*";
	Uri mImageUri;
	public int mPhotoWidth = 150;
	public int mPhotoHeight = 150;
	public String mPhotoName = "";
	boolean mAlreadyTakePhoto = false;

	// -------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.e("TakePhotoActivity", "onCreate");

		checkTakePhoto();
		mPhotoWidth = this.getIntent().getIntExtra("PhotoWidth", 150);
		mPhotoHeight = this.getIntent().getIntExtra("PhotoHeight", 150);
		mPhotoName = this.getIntent().getStringExtra("PhotoName");
		String take_phototype = this.getIntent()
				.getStringExtra("TakePhotoType");

		getSavedInstanceState(savedInstanceState);

		if (!this.mAlreadyTakePhoto) {
			File photo_file = new File(
					Environment.getExternalStorageDirectory(), mPhotoName);
			if (photo_file.exists()) {
				photo_file.delete();
				photo_file = new File(
						Environment.getExternalStorageDirectory(), mPhotoName);
			}
			mImageUri = Uri.fromFile(photo_file);
			if (take_phototype.equals(TakePhoto._ERESULT.getPicFromPicture
					.toString())) {
				this.mAlreadyTakePhoto = true;
				startImagePick();
			} else if (take_phototype
					.equals(TakePhoto._ERESULT.getPicFromCamera.toString())) {
				this.mAlreadyTakePhoto = true;
				startActionCamera();
			}
		}
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onStart() {
		super.onStart();
		Log.e("TakePhotoActivity", "onStart");
		checkTakePhoto();
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e("TakePhotoActivity", "onRestart");
		checkTakePhoto();
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onResume() {
		super.onResume();
		Log.e("TakePhotoActivity", "onResume");
		checkTakePhoto();
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onPause() {
		super.onPause();
		Log.e("TakePhotoActivity", "onPause");
		checkTakePhoto();
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onStop() {
		super.onStop();
		Log.e("TakePhotoActivity", "onStop");
		checkTakePhoto();
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onDestroy() {
		super.onDestroy();

		this.mAlreadyTakePhoto = false;
		Log.e("TakePhotoActivity", "onDestroy");
		checkTakePhoto();
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.e("TakePhotoActivity", "onSaveInstanceState");
		checkTakePhoto();
		outState.putBoolean("AlreadyTakePhoto", this.mAlreadyTakePhoto);
		outState.putParcelable("ImageUri", this.mImageUri);
		outState.putString("ResultReceiver", TakePhoto.mResultReceiver);
		outState.putString("SuccessMethodName", TakePhoto.mSuccessMethodName);
		outState.putString("FailMethodName", TakePhoto.mFailMethodName);
		outState.putString("PhotoName", mPhotoName);
		outState.putInt("PhotoWidth", mPhotoWidth);
		outState.putInt("PhotoHeight", mPhotoHeight);
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.e("TakePhotoActivity", "onRestoreInstanceState");
		checkTakePhoto();

		getSavedInstanceState(savedInstanceState);

		super.onRestoreInstanceState(savedInstanceState);
	}

	// -------------------------------------------------------------------------
	private void getSavedInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			this.mImageUri = savedInstanceState.getParcelable("ImageUri");
			this.mAlreadyTakePhoto = savedInstanceState
					.getBoolean("AlreadyTakePhoto");

			if (TakePhoto.mTakePhoto == null) {
				TakePhoto.Instantce(savedInstanceState.getInt("PhotoWidth"),
						savedInstanceState.getInt("PhotoHeight"),
						savedInstanceState.getString("SuccessMethodName"),
						savedInstanceState.getString("FailMethodName"),
						savedInstanceState.getString("PhotoName"),
						savedInstanceState.getString("ResultReceiver"));
			}
		}
	}

	// -------------------------------------------------------------------------
	private void checkTakePhoto() {
		if (TakePhoto.mTakePhoto == null) {
			Log.e("TakePhotoActivity", "TakePhoto::mTakePhoto::NULL");
		} else {
			Log.e("TakePhotoActivity", "TakePhoto::mTakePhoto::NotNULL");
		}
	}

	// -------------------------------------------------------------------------
	private void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_UNSPECIFIED);
		this.startActivityForResult(intent,
				TakePhoto._ERESULT.getPicFromPicture.ordinal());
	}

	// -------------------------------------------------------------------------
	private void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
		this.startActivityForResult(intent,
				TakePhoto._ERESULT.getPicFromCamera.ordinal());
	}

	// -------------------------------------------------------------------------
	/**
	 * 裁剪
	 * 
	 * @param uri
	 */
	private void __PictureScaleHandle(Uri uri, Boolean is_camera) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", mPhotoWidth);
		intent.putExtra("outputY", mPhotoHeight);
		TakePhoto._ERESULT request_code = TakePhoto._ERESULT.getPicFromCameraSuccess;
		if (is_camera) {
			intent.putExtra("return-data", false);
		} else {
			intent.putExtra("return-data", true);
			request_code = TakePhoto._ERESULT.getPicFromPictureSuccess;
		}
		Log.d("TakePhotoActivity",
				"__PictureScaleHandle::TakePhoto._ERESULT.getPicFromCameraSuccess.ordinal():: "
						+ TakePhoto._ERESULT.getPicFromCameraSuccess.ordinal()
						+ "  getPicFromPictureSuccess::"
						+ TakePhoto._ERESULT.getPicFromPictureSuccess.ordinal());
		this.startActivityForResult(intent, request_code.ordinal());
	}

	// -------------------------------------------------------------------------
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.e("TakePhotoActivity", "onConfigurationChanged");
		// 检测屏幕的方向：纵向或横向
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 当前为横屏， 在此处添加额外的处理代码
			Log.e("TakePhotoActivity", "onConfigurationChanged::横屏");
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			// 当前为竖屏， 在此处添加额外的处理代码
			Log.e("TakePhotoActivity", "onConfigurationChanged：：竖屏");
		}

		// 检测实体键盘的状态：推出或者合上
		if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
			// 实体键盘处于推出状态，在此处添加额外的处理代码
		} else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			// 实体键盘处于合上状态，在此处添加额外的处理代码
		}
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("TakePhotoActivity", "onActivityResult::resultCode:: "
				+ resultCode + "  requestCode::" + requestCode);
		// 取消哨兵
		Boolean bIsPhotoHandle = false;

		// 拍照缩放图片
		if (requestCode == TakePhoto._ERESULT.getPicFromCamera.ordinal()) {
			if (resultCode == 0) {
				bIsPhotoHandle = true;
			} else {
				File picture = new File(
						Environment.getExternalStorageDirectory() + "/"
								+ mPhotoName);
				__PictureScaleHandle(Uri.fromFile(picture), true);
			}

		}

		// 相册缩放图片
		if (requestCode == TakePhoto._ERESULT.getPicFromPicture.ordinal()) {
			if (null == data) {
				bIsPhotoHandle = true;
			} else {
				__PictureScaleHandle(data.getData(), false);
			}
		}

		if (bIsPhotoHandle) {
			Log.e("TakePhotoActivity", "getPicFailed");
			TakePhoto.sendToUnity(false,
					TakePhoto._ERESULT.getPicFailed.toString());
			finish();
			return;
		}

		// 保存头像图片
		if (requestCode == TakePhoto._ERESULT.getPicFromCameraSuccess.ordinal()) {
			Log.e("TakePhotoActivity", "getPicFromCameraSuccess");
			_decodeUriAsBitmap(this.mImageUri);
			// _sendThread(this.mImageUri);
			// Bitmap photo = _decodeUriAsBitmap(this.mImageUri);
			// Bundle extras = data.getExtras();
			// if (null != extras) {

			// Bitmap photo = extras.getParcelable("data");
			// String photo_str = "";
			// try {
			// // __SavePicture(photo);
			//
			// photo_str = _picToString(photo);
			// } catch (Exception e) {// catch (IOException e) {
			// e.printStackTrace();
			// }

			// Log.e("TakePhotoActivity", "photo_str:: " + photo_str);
			// TakePhoto.sendToUnity(TakePhoto._ERESULT.getPicSuccess
			// .toString());
			// TakePhoto.sendToUnity(true, photo_str);
			finish();
		} else if (requestCode == TakePhoto._ERESULT.getPicFromPictureSuccess
				.ordinal()) {
			Log.e("TakePhotoActivity", "getPicFromPictureSuccess");
			Bundle extras = data.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				_picToString(photo);
			}

			finish();
		}
		// }

		super.onActivityResult(requestCode, resultCode, data);
	}

	// -------------------------------------------------------------------------
	private void _decodeUriAsBitmap(Uri uri) {

		// Bitmap bitmap = null;
		Log.e("TakePhotoActivity", "_decodeUriAsBitmap");
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(uri.getPath());
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.e("TakePhotoActivity", "imagedata：：Length::" + data.length);
		// 对字节数组Base64编码
		String image_str = Base64Coder.encodeLines(data);
		TakePhoto.sendToUnity(true, image_str);
		Log.e("TakePhotoActivity", "image_str:: " + image_str);
		/*
		 * try { BitmapFactory.Options options = new BitmapFactory.Options();
		 * options.inPreferredConfig = Config.RGB_565; bitmap =
		 * BitmapFactory.decodeStream(getContentResolver()
		 * .openInputStream(uri), null, options); } catch (Exception e) {
		 * e.printStackTrace(); return; }
		 */

		// _picToString(bitmap);
	}

	// -------------------------------------------------------------------------
	private void _picToString(Bitmap photo) {
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

		TakePhoto.sendToUnity(true, icon);
		Log.e("TakePhotoActivity", "icon:: " + icon);
	}

	// -------------------------------------------------------------------------
	private void getPicStringThread(final Uri image_uri) {
		(new Thread(new Runnable() {
			public void run() {
				_decodeUriAsBitmap(image_uri);
			}
		})).start();
	}

	// String icon = "";
	// try {
	// // 创建一个字节数组输出流,流的大小为size
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
	// bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	// baos.flush();
	// baos.close();
	//
	// // 将字节数组输出流转化为字节数组byte[]
	// byte[] imagedata = baos.toByteArray();
	// Log.e("TakePhotoActivity", "imagedata：：Length::" + imagedata.length);
	// icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
	// // Log.e("TakePhotoActivity", "icon:: " + icon);
	// } catch (Exception e) {
	// e.printStackTrace();
	// return;
	// }
	//
	// TakePhoto.sendToUnity(true, icon);

	// ----------------------------------------------------------------------
	/**
	 * 保存图片
	 * 
	 * @param photo
	 * @throws IOException
	 */
	@SuppressLint("SdCardPath")
	private void __SavePicture(Bitmap photo) throws IOException {

		FileOutputStream fOut = null;
		try {
			String strPackgeName = getApplicationInfo().packageName;
			Log.e("TakePhotoActivity", "getPicSuccess::strPackgeName:: "
					+ strPackgeName);
			String path = "/mnt/sdcard/Android/data/" + strPackgeName
					+ "/files";
			Log.e("TakePhotoActivity", "getPicSuccess::path:: " + path);
			File destDir = new File(path);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}

			fOut = new FileOutputStream(path + "/" + mPhotoName);
		} catch (FileNotFoundException e) {
			Log.e("TakePhotoActivity::Error::", e.getMessage());
			e.printStackTrace();
		}

		// 将Bitmap对象写入本地路径中，Unity在去相同的路径来读取这个文件
		photo.compress(Bitmap.CompressFormat.PNG, 100, fOut);

		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
