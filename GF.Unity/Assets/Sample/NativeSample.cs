using UnityEngine;
using System.Collections;
using System;

public class NativeSample : MonoBehaviour,
    ITakePhotoReceiverListener, IPayReceiverListener, IAudioControlListener, ISpeechListener
{  
    //-------------------------------------------------------------------------
    public void audioChanged(string chang)
    {
        Debug.Log("audioChanged");
    }

    //-------------------------------------------------------------------------
    public void getPicFail(string fail)
    {
        //加载图片失败
        Debug.Log("加载图片失败");

    }

    //-------------------------------------------------------------------------
    public void getPicSuccess(string getpic_result)
    {
        //加载图片成功
        Debug.Log("加载图片成功");

    }

    //-------------------------------------------------------------------------
    public void PayResult(_ePayOptionType option_type, bool is_success, object result)
    {
        Debug.Log("PayResult::" + result);
    }

    //-------------------------------------------------------------------------
	public void speechResult(_eSpeechResult result_code, string most_possibleresult)
    {
        Debug.Log("speechResult::result_code:: " + (_eSpeechResult)result_code + "   most_possibleresult::" + most_possibleresult);
    }

    //-------------------------------------------------------------------------
    void Start()
    {
        //初始化各个Listener
        _initNativeMsgReceiverListener();
    }

    //-------------------------------------------------------------------------
    void Update()
    {
        
    }

    //-------------------------------------------------------------------------
    void _initNativeMsgReceiverListener()
    {
        NativeAPIMsgReceiver nativeAPI_msgreceiver = NativeAPIMsgReceiver.instance();
        nativeAPI_msgreceiver.TakePhotoReceiverListener = this;
        nativeAPI_msgreceiver.PayReceiverListener = this;
        nativeAPI_msgreceiver.AudioControlListener = this;
        //nativeAPI_msgreceiver.SpeechListener = this;
    }   
}
