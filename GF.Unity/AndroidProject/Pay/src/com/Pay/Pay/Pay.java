package com.Pay.Pay;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.app.Activity;

import com.AndroidToUnityMsgBridge.AndroidToUnityMsgBridge.AndroidToUnityMsgBridge;

public class Pay {
	// -------------------------------------------------------------------------
	private static Pay mPay;
	private static AndroidToUnityMsgBridge mAndroidToUnityMsgBridge;
	private Activity mUnityActivity;
	private static final int REQUEST_CODE_PAYMENT = 1;
	private static String UnityPayResultMethord = "PayResult";

	// -------------------------------------------------------------------------
	public static Pay Instance(String pay_resultreceiver) {
		if (mPay == null) {
			mPay = new Pay(pay_resultreceiver);
		}

		return mPay;
	}

	// -------------------------------------------------------------------------
	private Pay(String pay_resultreceiver) {
		mAndroidToUnityMsgBridge = AndroidToUnityMsgBridge
				.Instance(pay_resultreceiver);
		this.mUnityActivity = mAndroidToUnityMsgBridge.getActivity();
	}

	// -------------------------------------------------------------------------
	public void pay(String pay_chargedata) {
		Intent intent = new Intent(this.mUnityActivity,
				com.Pay.Pay.PayActivity.class);
		Log.e("Pay", "pay1::" + pay_chargedata);
		intent.putExtra("Pay", pay_chargedata);
		Log.e("Pay", "pay2::" + pay_chargedata);
		this.mUnityActivity.startActivity(intent);
		Log.e("Pay", "pay3::" + pay_chargedata);
	}

	// -------------------------------------------------------------------------
	public static void sendToUnityPic(Boolean is_success) {
		Log.e("Pay", "sendToUnityPic");

		mAndroidToUnityMsgBridge.sendMsgToUnity(UnityPayResultMethord,
				is_success ? "1" : "0");
	}
}
