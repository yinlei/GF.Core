using UnityEngine;
using System.Collections;
using OnePF;
using System.Collections.Generic;

public class Pay
{
    //-------------------------------------------------------------------------
    static Pay mPay;
    static IPay mIPay;
    static Inventory mInventory = null;
    string mMsg = "";
    const string OPENIAB_EVENT_RECEIVER = "OpenIABEventManager";

    //-------------------------------------------------------------------------
    public Pay()
    {
        // Listen to all events for illustration purposes
        OpenIABEventManager.billingSupportedEvent += billingSupportedEvent;
        OpenIABEventManager.billingNotSupportedEvent += billingNotSupportedEvent;
        OpenIABEventManager.queryInventorySucceededEvent += queryInventorySucceededEvent;
        OpenIABEventManager.queryInventoryFailedEvent += queryInventoryFailedEvent;
        OpenIABEventManager.purchaseSucceededEvent += purchaseSucceededEvent;
        OpenIABEventManager.purchaseFailedEvent += purchaseFailedEvent;
        OpenIABEventManager.consumePurchaseSucceededEvent += consumePurchaseSucceededEvent;
        OpenIABEventManager.consumePurchaseFailedEvent += consumePurchaseFailedEvent;

#if UNITY_ANDROID
        mIPay = new AndroidPay();
        Debug.Log("AndroidPay::");
#elif UNITY_IOS
        mIPay = new IOSPay();
        Debug.Log("IOSPay::");
#else
        Debug.LogError("Do not supported on this platform. ");
#endif
    }

    //-------------------------------------------------------------------------
    public static Pay Instant()
    {
        if (mPay == null)
        {
            mPay = new Pay();
        }

        GameObject openiab_msg_receiver = GameObject.Find(OPENIAB_EVENT_RECEIVER);
        if (openiab_msg_receiver == null)
        {
            openiab_msg_receiver = new GameObject(OPENIAB_EVENT_RECEIVER);
            openiab_msg_receiver.AddComponent<OpenIABEventManager>();
            GameObject.DontDestroyOnLoad(openiab_msg_receiver);
        }

        return mPay;
    }

    //-------------------------------------------------------------------------
    public static void pay(string buy_item_sku, string charge_data, _ePayType pay_type)
    {
        if (pay_type == _ePayType.AppStore)
        {
            OpenIAB.purchaseProduct(buy_item_sku);
        }
        else
        {
            mIPay.pay(charge_data, (int)pay_type);
        }
    }

    //-------------------------------------------------------------------------
    public void initInventory(List<string> list_inventorysku)
    {
        Debug.LogError("Pay::initInventory");
        foreach (var i in list_inventorysku)
        {
            OpenIAB.mapSku(i, OpenIAB_iOS.STORE, i);
        }

        var options = new Options();
        OpenIAB.init(options);
    }

    //-------------------------------------------------------------------------
    private void billingSupportedEvent()
    {
        //_isInitialized = true;
        Debug.Log("billingSupportedEvent");
    }

    //-------------------------------------------------------------------------
    private void billingNotSupportedEvent(string error)
    {
        Debug.Log("billingNotSupportedEvent: " + error);
    }

    //-------------------------------------------------------------------------
    private void queryInventorySucceededEvent(Inventory inventory)
    {
        Debug.Log("queryInventorySucceededEvent: " + inventory);
        if (inventory != null)
        {
            mMsg = inventory.ToString();
            mInventory = inventory;
        }

        NativeAPIMsgReceiver.instance().PayResult(_ePayOptionType.QueryInventory, true, inventory);
    }

    //-------------------------------------------------------------------------
    private void queryInventoryFailedEvent(string error)
    {
        Debug.Log("queryInventoryFailedEvent Failed: " + error);
        mMsg = error;

        NativeAPIMsgReceiver.instance().PayResult(_ePayOptionType.QueryInventory, false, mMsg);
    }

    //-------------------------------------------------------------------------
    private void purchaseSucceededEvent(Purchase purchase)
    {
        Debug.Log("purchaseSucceededEvent: " + purchase);
        mMsg = "PURCHASED Succeeded:" + purchase.ToString();

        NativeAPIMsgReceiver.instance().PayResult(_ePayOptionType.PurchaseProduct, true, purchase);
    }

    //-------------------------------------------------------------------------
    private void purchaseFailedEvent(int errorCode, string errorMessage)
    {
        Debug.Log("purchaseFailedEvent: " + errorMessage);
        mMsg = "Purchase Failed: " + errorMessage;

        NativeAPIMsgReceiver.instance().PayResult(_ePayOptionType.PurchaseProduct, false, errorMessage);
    }

    //-------------------------------------------------------------------------
    private void consumePurchaseSucceededEvent(Purchase purchase)
    {
        Debug.Log("consumePurchaseSucceededEvent: " + purchase);
        mMsg = "CONSUMED Succeeded: " + purchase.ToString();

        NativeAPIMsgReceiver.instance().PayResult(_ePayOptionType.ConsumeProduct, true, purchase);
    }

    //-------------------------------------------------------------------------
    private void consumePurchaseFailedEvent(string error)
    {
        Debug.Log("consumePurchaseFailedEvent: " + error);
        mMsg = "Consume Failed: " + error;

        NativeAPIMsgReceiver.instance().PayResult(_ePayOptionType.ConsumeProduct, false, error);
    }
}
