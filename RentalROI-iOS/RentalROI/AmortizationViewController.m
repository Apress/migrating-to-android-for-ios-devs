//
//  SlAmortizationViewController.m
//  RentalROI
//
//  Created by Sean on 2/23/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import "AmortizationViewController.h"
#import "MonthlyTermViewController.h"


@interface AmortizationViewController ()

@end

@implementation AmortizationViewController

- (id)initWithStyle:(UITableViewStyle)style {
   self = [super initWithStyle:style];
   if (self) {
      // Custom initialization
   }
   return self;
}

- (void)viewDidLoad {
   [super viewDidLoad];
}

-(void) viewDidAppear:(BOOL)animated {
   [super viewDidAppear:animated];
}

- (void)didReceiveMemoryWarning
{
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
   return [self.monthlyTerms count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
   static NSString *CellIdentifier = @"Cell";
   UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
   
   // Configure the cell...
   NSDictionary* monthlyTerm = [self.monthlyTerms objectAtIndex:indexPath.row];
   int pmtNo = [[monthlyTerm objectForKey:@"pmtNo"] intValue];
   double balance0 = [[monthlyTerm objectForKey:@"balance0"] doubleValue];
   cell.textLabel.text = [NSString stringWithFormat: @"%d\t$%.2f", pmtNo, balance0];
   double interest = [[monthlyTerm objectForKey:@"interest"] doubleValue];
   double principal = [[monthlyTerm objectForKey:@"principal"] doubleValue];
   int paymentPeriod = [[monthlyTerm objectForKey: @"pmtNo" ] intValue];
   double escrow = [monthlyTerm[@"escrow"] doubleValue];
   
   
   RentalProperty* property = [RentalProperty sharedInstance];
   double invested = property.purchasePrice - property.loanAmt + property.extra * paymentPeriod;
   double net = property.rent - escrow - interest - property.expenses;
   double roi = net * 12 / invested;
 
   NSString *text = [NSString stringWithFormat:@"%.2f%%", roi *100];
   
   cell.detailTextLabel.text = [NSString stringWithFormat:@"Interest: %.2f  Principle: %.2f\tROI: %@", interest, principal, text];
   
   return cell;
}

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
   NSDictionary* monthlyTerm = [self.monthlyTerms objectAtIndex:indexPath.row];
   [self performSegueWithIdentifier:@"toMonthlyTerm" sender:monthlyTerm];
}
#pragma mark - Navigation

// In a story board-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
   MonthlyTermViewController* destVc = segue.destinationViewController;
   destVc.monthlyTerm = sender;
}


@end
