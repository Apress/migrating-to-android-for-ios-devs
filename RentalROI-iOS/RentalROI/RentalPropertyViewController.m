//
//  SlPropertyViewController.m
//  RentalROI
//
//  Created by Sean on 2/22/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import "RentalPropertyViewController.h"
#import "EditTextViewController.h"
#import "AmortizationViewController.h"
#import "RentalProperty.h"

static NSString* URL_service_tmpl = @"http://www.pdachoice.com/ras/service/amortization?loan=%.2f&rate=%.2f&terms=%d&extra=%.2f";

@interface RentalPropertyViewController () <EditTextViewControllerDelegate> {
   RentalProperty* _property;
}

@end

@implementation RentalPropertyViewController

- (id)initWithStyle:(UITableViewStyle)style
{
   self = [super initWithStyle:style];
   if (self) {
      // Custom initialization
   }
   return self;
}

- (void)viewDidLoad
{
   [super viewDidLoad];
   _property = [RentalProperty sharedInstance];
   [_property load];
   self.lbPurchasePrice.text = [NSString stringWithFormat:@"%.0f",  _property.purchasePrice];
   self.lbLoanAmt.text = [NSString stringWithFormat:@"%.0f",  _property.loanAmt];
   
   double down = (1 - _property.loanAmt / _property.purchasePrice) * 100.0f;
   if (_property.purchasePrice > 0 ) {
      self.lbDownPayment.text = [NSString stringWithFormat:@"%.0f",  down];

      if (_property.loanAmt == 0 && down > 0) {
         _property.loanAmt = _property.purchasePrice * (1 - down / 100.0f);
         self.lbLoanAmt.text = [NSString stringWithFormat:@"%.2f", _property.loanAmt];
      }
   } else {
      self.lbDownPayment.text = @"0";
   }
   
   self.lbInterestRate.text = [NSString stringWithFormat:@"%.2f",  _property.interestRate];
   self.lbNumOfTerms.text = [NSString stringWithFormat:@"%d",  _property.numOfTerms];
   self.lbEscrow.text = [NSString stringWithFormat:@"%.0f",  _property.escrow];
   self.lbExtra.text = [NSString stringWithFormat:@"%.0f",  _property.extra];
   self.lbExpenses.text = [NSString stringWithFormat:@"%.0f",  _property.expenses];
   self.lbRent.text = [NSString stringWithFormat:@"%.0f",  _property.rent];
}

- (void)didReceiveMemoryWarning {
   [super didReceiveMemoryWarning];
   // Dispose of any resources that can be recreated.
}

#pragma mark - Table view data source

-(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
   NSLog(@">> didSelectRowAtIndexPath");
   [self performSegueWithIdentifier:@"toEditText" sender: indexPath];
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
   NSString* segueId = segue.identifier;
   if ([segueId isEqualToString:@"toAmortization"]) {
      AmortizationViewController* amoVc = segue.destinationViewController;
      amoVc.monthlyTerms = sender;
      return;
   }
   
   NSIndexPath*  indexPath = sender;
   NSInteger section = indexPath.section;
   NSInteger row = indexPath.row;
   
   UILabel* src;
   NSString* header;
   if (section == 0) {
      switch (row) {
         case 0:
            src = self.lbPurchasePrice;
            header = @"Purchase Price";
            break;
            
         case 1:
            src = self.lbDownPayment;
            header = @"Down Payment (%)";
            break;
            
         case 2:
            src = self.lbLoanAmt;
            header = @"Loan Amount";
            break;
            
         case 3:
            src = self.lbInterestRate;
            header = @"Interest Rate (%)";
            break;
            
         case 4:
            src = self.lbNumOfTerms;
            header = @"Mortgage Terms (Yr.)";
            break;
            
         case 5:
            src = self.lbEscrow;
            header = @"Escrow";
            break;
            
         case 6:
            src = self.lbExtra;
            header = @"Extra Payment";
            break;
            
         default:
            break;
      }
   } else {
      switch (row) {
         case 0:
            src = self.lbExpenses;
            header = @"Expense";
            break;
            
         case 1:
            src = self.lbRent;
            header = @"Rent";
            break;
            
         default:
            break;
      }
   }
   
   EditTextViewController* destVc = segue.destinationViewController;
   
   destVc.tag = section == 0 ? row : 7 + row;
   destVc.text = src.text;
   destVc.header = header;
   destVc.delegate = self;
}

- (void)textEditController:(EditTextViewController *)controller didFinishEditText:(NSString *)text {
   [self dismissViewControllerAnimated:YES completion:nil];
   
   NSInteger tag = controller.tag;
   switch (tag) {
      case 0:
         self.lbPurchasePrice.text = text;
         _property.purchasePrice = [text doubleValue];
         
         double down = [self.lbDownPayment.text doubleValue];
         if (_property.purchasePrice > 0 && _property.loanAmt == 0 && down > 0) {
            _property.loanAmt = _property.purchasePrice * (1 - down / 100.0f);
            self.lbLoanAmt.text = [NSString stringWithFormat:@"%.2f", _property.loanAmt];
         } else {
            down = 1.0f - (_property.loanAmt / _property.purchasePrice);
            self.lbDownPayment.text = [NSString stringWithFormat:@"%.2f", down * 100];
         }
         
         break;
      case 1:
         self.lbDownPayment.text = text;
         float percentage = [text floatValue] / 100.0;
         _property.loanAmt = _property.purchasePrice * (1 - percentage);
         self.lbLoanAmt.text = [NSString stringWithFormat:@"%.2f", _property.loanAmt];
         
         break;
      case 2:
         self.lbLoanAmt.text = text;
         _property.loanAmt = [text doubleValue];
         double downpayment = 1.0f - ([text doubleValue] / _property.purchasePrice);
         self.lbDownPayment.text = [NSString stringWithFormat:@"%.2f", downpayment * 100];
         
         break;
      case 3:
         self.lbInterestRate.text = text;
         _property.interestRate = [text doubleValue];
         
         break;
      case 4:
         self.lbNumOfTerms.text = text;
         _property.numOfTerms = [text intValue];
         break;
      case 5:
         self.lbEscrow.text = text;
         _property.escrow = [text doubleValue];
         
         break;
      case 6:
         self.lbExtra.text = text;
         _property.extra = [text doubleValue];
         
         break;
      case 7:
         self.lbExpenses.text = text;
         _property.expenses = [text doubleValue];

         break;
      case 8:
         self.lbRent.text = text;
         _property.rent = [text doubleValue];
         
         break;
         
      default:
         break;
   }
   
   [_property save];
}

- (void)textEditControllerDidCancel:(EditTextViewController *)controller {
   [self dismissViewControllerAnimated:YES completion:nil];
}

- (IBAction)doAmortization:(id)sender {
   
   NSArray* savedAmortization = [_property getSavedAmortization];
   
   if (savedAmortization != nil) {
      [self performSegueWithIdentifier:@"toAmortization" sender:savedAmortization];
   } else {
      NSString* url = [NSString stringWithFormat: URL_service_tmpl,
                       _property.loanAmt,
                       _property.interestRate,
                       _property.numOfTerms,
                       _property.extra];
      
      [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
      NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString: url]];
      [NSURLConnection sendAsynchronousRequest:request queue:[NSOperationQueue currentQueue]
                             completionHandler:^(NSURLResponse *response, NSData *data, NSError *error) {
                                id jsonObject = nil;
                                [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
                                
                                if(error == nil) {
                                   jsonObject = [NSJSONSerialization
                                                 JSONObjectWithData:data
                                                 options:NSJSONReadingAllowFragments
                                                 error:&error];
                                   
                                   NSLog(@"jsonObject: %@", jsonObject);
                                   [_property saveAmortization:jsonObject];
                                   [self performSegueWithIdentifier:@"toAmortization" sender:jsonObject];
                                } else {
                                   [[[UIAlertView alloc] initWithTitle: error.domain message: error.localizedDescription delegate:nil cancelButtonTitle:@"Close" otherButtonTitles: nil] show];
                                }
                             }];
   }
}

@end
