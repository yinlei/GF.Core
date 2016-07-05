//
//  IOSPayBridge.m
//  	
//
//  Created by lion on 16/3/16.
//
//

#import "IOSPay.h"

enum PayType{Wx=0,AliPay=1,AppStore=2};

NSString* ToString(char* c_string)
{
    return c_string==NULL?[NSString stringWithUTF8String:""]:[NSString stringWithUTF8String:c_string];
}

extern "C"
{
    void pay_ios(char* pay_data,int pay_type)
    {
        NSLog(@"IOSPayBridge::pay_data:: %@",ToString(pay_data));
         NSLog(@"IOSPayBridge::pay_type:: %@",[NSString stringWithFormat:@"%d",pay_type]);
        [IOSPay pay_ios:ToString(pay_data) with_pay_type:pay_type];
    }
}
