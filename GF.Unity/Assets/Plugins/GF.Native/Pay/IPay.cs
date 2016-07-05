using UnityEngine;
using System.Collections;

public interface IPay
{
    //-------------------------------------------------------------------------
    void pay(string charge_data, int pay_type);
}

//-------------------------------------------------------------------------
public enum _ePayType
{
    Wx = 0,
    AliPay,
    AppStore,
}

//-------------------------------------------------------------------------
public enum _ePayOptionType
{
    QueryInventory,
    PurchaseProduct,
    ConsumeProduct,
}
