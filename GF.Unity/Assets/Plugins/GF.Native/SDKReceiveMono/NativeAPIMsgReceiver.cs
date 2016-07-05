using System;
using System.Collections.Generic;
using UnityEngine;

public class NativeAPIMsgReceiver : MonoBehaviour
{
    //-------------------------------------------------------------------------
    static string mNativeAPIMsgReceiverName;
    static NativeAPIMsgReceiver mNativeAPIMsgReceiver;
    public ITakePhotoReceiverListener TakePhotoReceiverListener { get; set; }
    public IPayReceiverListener PayReceiverListener { get; set; }
    public IAudioControlListener AudioControlListener { get; set; }

    //-------------------------------------------------------------------------
    public static NativeAPIMsgReceiver instance()
    {
        mNativeAPIMsgReceiverName = (typeof(NativeAPIMsgReceiver)).Name;
        GameObject msg_receiver = GameObject.Find(mNativeAPIMsgReceiverName);
        if (msg_receiver == null)
        {
            msg_receiver = new GameObject(mNativeAPIMsgReceiverName);
            mNativeAPIMsgReceiver = msg_receiver.AddComponent<NativeAPIMsgReceiver>();
            GameObject.DontDestroyOnLoad(msg_receiver);
        }
        else
        {
            mNativeAPIMsgReceiver = msg_receiver.GetComponent<NativeAPIMsgReceiver>();
        }

        return mNativeAPIMsgReceiver;
    }

    //-------------------------------------------------------------------------
    public void PayResult(_ePayOptionType option_type, bool is_success, object result)
    {
        Debug.Log("PayResult::" + result);
        if (PayReceiverListener != null)
        {
            PayReceiverListener.PayResult(option_type, is_success, result);
        }
    }

    //-------------------------------------------------------------------------
    public void ReceiveError(string error)
    {
        Debug.Log("ReceiveError::" + error);
    }

    //-------------------------------------------------------------------------
    public void getPicSuccess(string getpic_result)
    {
        if (TakePhotoReceiverListener != null)
        {
            TakePhotoReceiverListener.getPicSuccess(getpic_result);
        }
    }

    //-------------------------------------------------------------------------
    public void getPicFail(string fail)
    {
        if (TakePhotoReceiverListener != null)
        {
            TakePhotoReceiverListener.getPicFail(fail);
        }
    }
}