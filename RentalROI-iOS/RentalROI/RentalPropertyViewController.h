//
//  SlPropertyViewController.h
//  RentalROI
//
//  Created by Sean on 2/22/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RentalPropertyViewController : UITableViewController

@property (weak, nonatomic) IBOutlet UILabel *lbPurchasePrice;
@property (weak, nonatomic) IBOutlet UILabel *lbDownPayment;
@property (weak, nonatomic) IBOutlet UILabel *lbLoanAmt;
@property (weak, nonatomic) IBOutlet UILabel *lbInterestRate;
@property (weak, nonatomic) IBOutlet UILabel *lbNumOfTerms;
@property (weak, nonatomic) IBOutlet UILabel *lbEscrow;
@property (weak, nonatomic) IBOutlet UILabel *lbExtra;
@property (weak, nonatomic) IBOutlet UILabel *lbExpenses;
@property (weak, nonatomic) IBOutlet UILabel *lbRent;

- (IBAction)doAmortization:(id)sender;
@end
