using UnityEngine;
using System.Collections;
using System.Runtime.InteropServices;

#if UNITY_IPHONE
public class IOSPay : IPay
{
    //-------------------------------------------------------------------------
    public IOSPay()
    {
        
    }

    //-------------------------------------------------------------------------
    public void pay(string charge_data, int pay_type)
    {
        pay_ios(charge_data,pay_type);
    }

#region DllImport
    //-------------------------------------------------------------------------
    [DllImport("__Internal")]
    private static extern void pay_ios(string charge_data, int pay_type);
#endregion
}
#endif