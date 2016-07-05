package com.XFSpeech.XFSpeech;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.AndroidToUnityMsgBridge.AndroidToUnityMsgBridge.AndroidToUnityMsgBridge;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

public class XFSpeech {
	// -------------------------------------------------------------------------
	public static XFSpeech mXFSpeech;
	private static AndroidToUnityMsgBridge mAndroidToUnityMsgBridge;
	private Activity mUnityActivity;
	static final String mSpeechResultMsgName = "speechResult";

	public SpeechRecognizer mspeech;
	int ret = 0; // 函数调用返回值

	// -------------------------------------------------------------------------
	public static XFSpeech Instantce(String resultreceiver, String app_id,
			String language) {
		if (mXFSpeech == null) {
			mAndroidToUnityMsgBridge = AndroidToUnityMsgBridge
					.Instance(resultreceiver);
			mXFSpeech = new XFSpeech(app_id, language);
		}

		return mXFSpeech;
	}

	// -------------------------------------------------------------------------
	private XFSpeech(String app_id, String language) {
		mUnityActivity = mAndroidToUnityMsgBridge.getActivity();
		SpeechUtility.createUtility(mUnityActivity, SpeechConstant.APPID + "="
				+ app_id + "," + SpeechConstant.FORCE_LOGIN + "=true");

		Log.e("XFSpeech", "createRecognizer");
		mspeech = SpeechRecognizer.createRecognizer(mUnityActivity,
				mInitListener);
		mspeech.setParameter(SpeechConstant.DOMAIN, "iat");
		mspeech.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		mspeech.setParameter(SpeechConstant.LANGUAGE, language); // "" 573a9399
		mspeech.setParameter(SpeechConstant.ACCENT, "mandarin");
	}

	// -------------------------------------------------------------------------
	public void startSpeech() {
		Log.e("XFSpeech", "startSpeech::");

		ret = mspeech.startListening(recognizerListener);
		if (ret != ErrorCode.SUCCESS) {
			Log.e("XFSpeech", "识别失败,错误码：" + ret);
			sendToUnity(_ERESULT.Error, "ErrorCode: " + ret);
		}
	}
	
	// -------------------------------------------------------------------------
	public void cancelSpeech() {
		Log.e("XFSpeech", "cancelSpeech::");

		mspeech.cancel();
	}

	// -------------------------------------------------------------------------
	private void printResult(RecognizerResult results, boolean is_final) {
		String text = JsonParser.parseIatResult(results.getResultString());

		Log.e("XFSpeech", "识别结果：" + text);

		if (!is_final) {
			sendToUnity(_ERESULT.FinalResults, text);
		}

		// String sn = null;
		// 读取json结果中的sn字段
		// try {
		// JSONObject resultJson = new JSONObject(results.getResultString());
		// sn = resultJson.optString("sn");
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }

		// mIatResults.put(sn, text);

		// StringBuffer resultBuffer = new StringBuffer();
		// for (String key : mIatResults.keySet()) {
		// resultBuffer.append(mIatResults.get(key));
		// }

		// mResultText.setText(resultBuffer.toString());
		// mResultText.setSelection(mResultText.length());
	}

	public RecognizerListener recognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// TODO Auto-generated method stub
			Log.e("XFSpeech", "onBeginOfSpeech:");
		}

		@Override
		public void onEndOfSpeech() {
			// TODO Auto-generated method stub
			Log.e("XFSpeech", "onEndOfSpeech:");
		}

		@Override
		public void onError(SpeechError arg0) {
			// TODO Auto-generated method stub
			Log.e("XFSpeech", "error:" + arg0.getErrorCode());

			sendToUnity(_ERESULT.Error, "ErrorCode: " + arg0.getErrorCode());
		}

		@Override
		public void onResult(RecognizerResult arg0, boolean is_final) {
			// TODO Auto-generated method stub
			Log.e("XFSpeech", "result:" + arg0.getResultString());

			printResult(arg0, is_final);
			// if(is_final)
			// {
			// Log.e("XFSpeech", "arg1::result:"+arg0.getResultString());
			// }
			// Toast.makeText(mContext, "result:"+arg0.getResultString(),
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onVolumeChanged(int arg0, byte[] arg1) {
			// TODO Auto-generated method stub
			// Log.e("XFSpeech", "onVolumeChanged:"+arg0);
		}

	};

	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			if (code == 0) {
				Log.e("XFSpeech", "login");
				// Toast.makeText(mContext, "login", Toast.LENGTH_SHORT).show();

			} else {
				Log.e("XFSpeech", "login error" + code);
				// Toast.makeText(mContext, "login error"+code,
				// Toast.LENGTH_SHORT).show();
			}
		}
	};

	// -------------------------------------------------------------------------
	public static void sendToUnity(_ERESULT result, String most_possibleresult) {

		Log.e("XFSpeech", "sendToUnity::result::" + result
				+ "  most_possibleresult:: " + most_possibleresult);
		mAndroidToUnityMsgBridge.sendMsgToUnity(mSpeechResultMsgName,
				result.ordinal() + ":" + most_possibleresult);
	}

	// ----------------------------------------------------------------------
	// 返回消息类型
	public enum _ERESULT {
		None, ReadyForSpeech, BeginningOfSpeech, EndOfSpeech, FinalResults, PartialResults, Error
	}
}
