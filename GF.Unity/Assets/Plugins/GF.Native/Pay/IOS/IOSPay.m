//
//  IOSPay.m
//  	
//
//  Created by lion on 16/3/16.
//
//

#import "IOSPay.h"
#import "Pingpp.h"

#define kUrlSchemeWX @""
#define kUrlSchemeAliPay @""

@implementation IOSPay

+ (void)pay_ios:(NSString*)pay_data with_pay_type: (int) pay_type
{
    NSLog(@"IOSPay::pay_data:: %@",pay_data);
    NSLog(@"IOSPay::pay_type:: %@",[NSString stringWithFormat:@"%d",pay_type]);
    NSString* pay_url =@"";
    if (pay_type == 0) {
        pay_url = kUrlSchemeWX;
    }
    else if (pay_type == 1)
    {
        pay_url = kUrlSchemeAliPay;
    }
    
    
    [Pingpp createPayment:pay_data appURLScheme:pay_url withCompletion:^(NSString *result, PingppError *error) {
        NSLog(@"completion block: %@", result);
        if (error == nil) {
            NSLog(@"PingppError is nil");
        } else {
            NSLog(@"PingppError: code=%lu msg=%@", (unsigned  long)error.code, [error getMsg]);
        }
    }];
}

@end