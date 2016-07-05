package com.XFSpeech.XFSpeech;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

/**
 * Json缁撴灉瑙ｆ瀽绫�
 */
public class JsonParser {

	public static String parseIatResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				// 杞啓缁撴灉璇嶏紝榛樿浣跨敤绗竴涓粨鏋�
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				JSONObject obj = items.getJSONObject(0);
				ret.append(obj.getString("w"));
//				濡傛灉闇�瑕佸鍊欓�夌粨鏋滐紝瑙ｆ瀽鏁扮粍鍏朵粬瀛楁
//				for(int j = 0; j < items.length(); j++)
//				{
//					JSONObject obj = items.getJSONObject(j);
//					ret.append(obj.getString("w"));
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret.toString();
	}
	
	public static String parseGrammarResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				for(int j = 0; j < items.length(); j++)
				{
					JSONObject obj = items.getJSONObject(j);
					if(obj.getString("w").contains("nomatch"))
					{
						ret.append("娌℃湁鍖归厤缁撴灉.");
						return ret.toString();
					}
					ret.append("銆愮粨鏋溿��" + obj.getString("w"));
					ret.append("銆愮疆淇″害銆�" + obj.getInt("sc"));
					ret.append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.append("娌℃湁鍖归厤缁撴灉.");
		} 
		return ret.toString();
	}
	
	public static String parseLocalGrammarResult(String json) {
		StringBuffer ret = new StringBuffer();
		try {
			JSONTokener tokener = new JSONTokener(json);
			JSONObject joResult = new JSONObject(tokener);

			JSONArray words = joResult.getJSONArray("ws");
			for (int i = 0; i < words.length(); i++) {
				JSONArray items = words.getJSONObject(i).getJSONArray("cw");
				for(int j = 0; j < items.length(); j++)
				{
					JSONObject obj = items.getJSONObject(j);
					if(obj.getString("w").contains("nomatch"))
					{
						ret.append("娌℃湁鍖归厤缁撴灉.");
						return ret.toString();
					}
					ret.append("銆愮粨鏋溿��" + obj.getString("w"));
					ret.append("\n");
				}
			}
			ret.append("銆愮疆淇″害銆�" + joResult.optInt("sc"));

		} catch (Exception e) {
			e.printStackTrace();
			ret.append("娌℃湁鍖归厤缁撴灉.");
		} 
		return ret.toString();
	}
}
