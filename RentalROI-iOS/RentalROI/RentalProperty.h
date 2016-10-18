//
//  SlProperty.h
//  RentalROI
//
//  Created by Sean on 2/23/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RentalProperty : NSObject

@property (nonatomic) double purchasePrice;
@property (nonatomic) double loanAmt;
@property (nonatomic) double interestRate;
@property (nonatomic) int numOfTerms;
@property (nonatomic) double escrow;
@property (nonatomic) double extra;
@property (nonatomic) double expenses;
@property (nonatomic) double rent;

+(RentalProperty*) sharedInstance;

-(BOOL) load;
-(void) save;

-(void) saveAmortization: (NSArray*) jsonOjb;
-(NSArray*) getSavedAmortization;

@end
