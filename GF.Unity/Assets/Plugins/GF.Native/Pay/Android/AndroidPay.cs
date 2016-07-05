using UnityEngine;
using System.Collections;

public class AndroidPay : IPay
{
    //-------------------------------------------------------------------------
    public AndroidJavaClass mAndroidJavaClassPay;
    public AndroidJavaObject mAndroidJavaObjectPay;

    //-------------------------------------------------------------------------
    public AndroidPay()
    {
        mAndroidJavaClassPay = new AndroidJavaClass("com.Pay.Pay.Pay");
        if (mAndroidJavaObjectPay == null)
        {
            mAndroidJavaObjectPay = mAndroidJavaClassPay.CallStatic<AndroidJavaObject>("Instantce", "NativeAPIMsgReceiver");
        }
    }

    //-------------------------------------------------------------------------
    public void pay(string charge_data, int pay_type)
    {
        if (mAndroidJavaObjectPay != null)
        {
            mAndroidJavaObjectPay.Call(_eAndroidPay.pay.ToString(), charge_data);
        }
        else
        {
            Debug.LogError("AndroidJavaObject Is Null");
        }
    }
}

//-------------------------------------------------------------------------
public enum _eAndroidPay
{
    pay
}
