using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using UnityEngine;
#if UNITY_EDITOR
#elif UNITY_IPHONE
using LitJson;
#endif

public class DCTracking
{
#if UNITY_EDITOR
#elif UNITY_ANDROID
	private static string JAVA_CLASS = "com.dataeye.tracking.DCTracking";
	private static AndroidJavaObject task = new AndroidJavaClass(JAVA_CLASS);
#elif UNITY_IPHONE
	[DllImport("__Internal")]
	private static extern void dcSetEffectPoint(string pointId, string json);
#endif
	
	
	public static void setEffectPoint(string pointId, Dictionary<string, string> dictionary)
	{
		if(pointId == null)
		{
			return;
		}
#if UNITY_EDITOR
#elif UNITY_ANDROID
		using(AndroidJavaObject ajo = DCEvent.dicToMap(dictionary))
		{
			task.CallStatic("setEffectPoint", pointId, ajo);
		}
#elif UNITY_IPHONE
		string json = LitJson.JsonMapper.ToJson(dictionary);
		dcSetEffectPoint(pointId, json);
#elif UNITY_WP8
		DataEyeWP8.DCTask.fail(taskId, reason);
#endif
	}
}

