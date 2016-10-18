//
//  SlMonthlyTermViewController.m
//  RentalROI
//
//  Created by Sean on 2/23/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import "MonthlyTermViewController.h"
#import "RentalProperty.h"

@interface MonthlyTermViewController ()

@end

@implementation MonthlyTermViewController

- (id)initWithStyle:(UITableViewStyle)style {
   self = [super initWithStyle:style];
   if (self) {
      // Custom initialization
   }
   return self;
}

- (void)viewDidLoad {
   [super viewDidLoad];
   
   double principle = [[self.monthlyTerm objectForKey: @"principal" ] doubleValue];
   double interest = [[self.monthlyTerm objectForKey: @"interest" ] doubleValue];
   double escrow = [[self.monthlyTerm objectForKey: @"escrow" ] doubleValue];
   double extra = [[self.monthlyTerm objectForKey: @"extra" ] doubleValue];
   double balance = [[self.monthlyTerm objectForKey: @"balance0" ] doubleValue] - principle;
   int paymentPeriod = [[self.monthlyTerm objectForKey: @"pmtNo" ] intValue];
   double totalPmt = principle + interest + escrow + extra;

   RentalProperty* property = [RentalProperty sharedInstance];
   double invested = property.purchasePrice - property.loanAmt + property.extra * paymentPeriod;
   double net = property.rent - escrow - interest - property.expenses;
   double roi = net * 12 / invested;

   self.lbTotalPayment.text = [NSString stringWithFormat: @"$%.2f", totalPmt];
   self.lbPaymentNo.text = [NSString stringWithFormat: @"No. %d", paymentPeriod];
   self.lbPrinciplePayment.text = [NSString stringWithFormat: @"$%.2f", principle];
   self.lbInterestPayment.text = [NSString stringWithFormat: @"$%.2f", interest];
   self.lbEscrowPayment.text = [NSString stringWithFormat: @"$%.2f", escrow];
   self.lbAddlPayment.text = [NSString stringWithFormat: @"$%.2f", extra];
   self.lbMorgageDebt.text = [NSString stringWithFormat: @"$%.2f", balance];
   self.lbEquityInvestment.text = [NSString stringWithFormat: @"$%.2f", property.purchasePrice - balance];
   self.lbCashInvested.text = [NSString stringWithFormat: @"$%.2f", invested];
   self.lbRoi.text = [NSString stringWithFormat:@"%.2f%% ($%.2f/mo)", roi *100, net];
}

- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}

@end
