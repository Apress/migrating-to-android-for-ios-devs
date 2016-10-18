//
//  HaEditTextViewController.h
//  SupTester
//
//  Created by Sean on 1/28/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import <UIKit/UIKit.h>

@protocol EditTextViewControllerDelegate;

@interface EditTextViewController : UIViewController <UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UINavigationBar *navBar;
@property (weak, nonatomic) IBOutlet UITextField *tfEditText;

@property(nonatomic,assign) id <EditTextViewControllerDelegate> delegate;

@property (nonatomic) NSInteger tag;
@property (nonatomic, strong) NSString* header;
@property (nonatomic, strong) NSString* text;

@end

@protocol EditTextViewControllerDelegate<NSObject>
- (void)textEditController:(EditTextViewController *)controller didFinishEditText:(NSString *)text;
- (void)textEditControllerDidCancel:(EditTextViewController *)controller;
@end

