//
//  SlMonthlyTermViewController.h
//  RentalROI
//
//  Created by Sean on 2/23/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RentalProperty.h"

@interface MonthlyTermViewController : UITableViewController

@property (nonatomic, strong) NSDictionary* monthlyTerm;

@property (weak, nonatomic) IBOutlet UILabel *lbPaymentNo;
@property (weak, nonatomic) IBOutlet UILabel *lbTotalPayment;
@property (weak, nonatomic) IBOutlet UILabel *lbPrinciplePayment;
@property (weak, nonatomic) IBOutlet UILabel *lbInterestPayment;
@property (weak, nonatomic) IBOutlet UILabel *lbEscrowPayment;
@property (weak, nonatomic) IBOutlet UILabel *lbAddlPayment;
@property (weak, nonatomic) IBOutlet UILabel *lbMorgageDebt;

@property (weak, nonatomic) IBOutlet UILabel *lbEquityInvestment;
@property (weak, nonatomic) IBOutlet UILabel *lbCashInvested;
@property (weak, nonatomic) IBOutlet UILabel *lbRoi;

@end
