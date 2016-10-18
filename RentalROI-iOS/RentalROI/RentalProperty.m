//
//  SlProperty.m
//  RentalROI
//
//  Created by Sean on 2/23/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import "RentalProperty.h"

static NSString* KEY_AMO_SAVED = @"KEY_AMO_SAVED";
static NSString* KEY_PROPERTY = @"KEY_PROPERTY";
static RentalProperty* _sharedInstance = nil;

@implementation RentalProperty

- (id)init {
   self = [super init];
   if (self) {
      // Custom initialization
      self.interestRate = 5.0f;
      self.numOfTerms = 30;
   }
   return self;
}

+(RentalProperty*) sharedInstance {
   if (_sharedInstance == nil) {
      _sharedInstance = [[RentalProperty alloc] init];
   }
   return _sharedInstance;
}

-(NSString*) getAmortizationPersistentKey {
   NSString* amortizationPersistentKey = [NSString stringWithFormat:@"%.2f-%.2f-%d-%.2f", self.loanAmt,
                                          self.interestRate,
                                          self.numOfTerms,
                                          self.extra];
   return amortizationPersistentKey;
}

-(NSArray*) getSavedAmortization {
   NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
   NSString* savedKey =[userDefaults stringForKey: KEY_AMO_SAVED];
   NSString* amortizationPersistentKey = [self getAmortizationPersistentKey];
   if (savedKey.length > 0 && [savedKey isEqualToString:amortizationPersistentKey]) {
      return [userDefaults arrayForKey:savedKey];
   } else {
      return nil;
   }
}

-(void) saveAmortization: (NSArray*) jsonOjb {
   // only save one, remove prev first
   NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
//   NSString* savedKey =[userDefaults stringForKey: KEY_AMO_SAVED];
//   if (savedKey != nil) {
//      [userDefaults removeObjectForKey:savedKey];
//   }
   NSString* amortizationPersistentKey = [self getAmortizationPersistentKey];
   [userDefaults setObject:amortizationPersistentKey forKey:KEY_AMO_SAVED];
   [userDefaults setObject:jsonOjb forKey:amortizationPersistentKey];
   [userDefaults synchronize];
}

-(BOOL) load {
   NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
   NSDictionary* jo = [userDefaults objectForKey:KEY_PROPERTY];
   if (jo == nil) {
      return NO;
   }
   
   self.purchasePrice = [[jo objectForKey:@"purchasePrice"] doubleValue];
   self.loanAmt = [[jo objectForKey:@"loanAmt"] doubleValue];
   self.interestRate = [[jo objectForKey:@"interestRate"] doubleValue];
   self.numOfTerms = [[jo objectForKey:@"numOfTerms"] intValue];
   self.escrow = [[jo objectForKey:@"escrow"] doubleValue];
   self.extra = [[jo objectForKey:@"extra"] doubleValue];
   self.expenses = [[jo objectForKey:@"expenses"] doubleValue];
   self.rent = [[jo objectForKey:@"rent"] doubleValue];
   return YES;
}

-(void) save {
   NSDictionary* jo = @{@"purchasePrice" : [NSNumber numberWithDouble:self.purchasePrice],
                        @"loanAmt" : [NSNumber numberWithDouble:self.loanAmt],
                        @"interestRate" : [NSNumber numberWithDouble:self.interestRate],
                        @"numOfTerms" : [NSNumber numberWithDouble:self.numOfTerms],
                        @"escrow" : [NSNumber numberWithDouble:self.escrow],
                        @"extra" : [NSNumber numberWithDouble:self.extra],
                        @"expenses" : [NSNumber numberWithDouble:self.expenses],
                        @"rent" : [NSNumber numberWithDouble:self.rent]
                        };
   
   NSUserDefaults* userDefaults = [NSUserDefaults standardUserDefaults];
   [userDefaults setObject:jo forKey:KEY_PROPERTY];
   [userDefaults synchronize];
}


@end
