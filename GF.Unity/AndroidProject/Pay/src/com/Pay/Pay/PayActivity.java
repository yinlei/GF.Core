package com.Pay.Pay;

import com.pingplusplus.android.PaymentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PayActivity extends Activity {

	private Context mContext = null;
	private static final int REQUEST_CODE_PAYMENT = 1;
	boolean mAlreadyPay = false;

	// -------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("PayActivity", "onCreate");
		mContext = this;

		_getSavedInstanceState(savedInstanceState);

		if (mAlreadyPay) {
			String pay_data = this.getIntent().getStringExtra("Pay");
			pay(pay_data);
		}
	}

	// -------------------------------------------------------------------------
	public void pay(String pay_data) {
		Log.e("PayActivity", "pay::" + pay_data);

		Intent intent = new Intent(mContext, PaymentActivity.class);
		Log.e("PayActivity", "pay1::" + pay_data);
		intent.putExtra(PaymentActivity.EXTRA_CHARGE, pay_data);
		Log.e("PayActivity", "pay2::" + pay_data);
		startActivityForResult(intent, REQUEST_CODE_PAYMENT);
		Log.e("PayActivity", "pay3::" + pay_data);
	}

	// -------------------------------------------------------------------------
	/**
	 * onActivityResult 获得支付结果，如果支付成功，服务器会收到ping++ 服务器发送的异步通知。 最终支付成功根据异步通知为准
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 支付页面返回处理
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				Boolean is_success = false;
				String result = data.getExtras().getString("pay_result");
				if (result == "success") {
					is_success = true;
				}
				/*
				 * 处理返回值 "success" - payment succeed "fail" - payment failed
				 * "cancel" - user canceld "invalid" - payment plugin not
				 * installed
				 */
				String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
				String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
				// showMsg(result, errorMsg, extraMsg);

				Log.e("PayActivity", "errorMsg:: " + errorMsg);
				Pay.sendToUnityPic(is_success);
			}
		}
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		Log.e("PayActivity", "onSaveInstanceState");

		outState.putBoolean("AlreadyPay", this.mAlreadyPay);
	}

	// -------------------------------------------------------------------------
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.e("PayActivity", "onRestoreInstanceState");

		_getSavedInstanceState(savedInstanceState);
	}

	// -------------------------------------------------------------------------
	void _getSavedInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			this.mAlreadyPay = savedInstanceState.getBoolean("AlreadyPay");
		}
	}
}
