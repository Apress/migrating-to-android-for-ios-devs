//
//  SlEditTextViewController.m
//  RentalROI
//
//  Created by Sean on 1/28/14.
//  Copyright (c) 2014 PdaChoice. All rights reserved.
//

#import "EditTextViewController.h"

@interface EditTextViewController ()
- (IBAction)doCancel:(id)sender;
- (IBAction)doSave:(id)sender;
@end

@implementation EditTextViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
	// Do any additional setup after loading the view.
   
   [[NSNotificationCenter defaultCenter] addObserver:self
                                            selector:@selector(keyboardAppeared:)
                                                name:UIKeyboardDidShowNotification
                                              object:nil];
   self.tfEditText.text = self.text;
   self.navBar.topItem.title = self.header;
}

-(void)viewDidAppear:(BOOL)animated {
   [super viewDidAppear:animated];
   [self.tfEditText becomeFirstResponder];
}

-(void) keyboardAppeared: (NSNotification*)notification {
//   SLLOG;
   NSDictionary* keyboardInfo = [notification userInfo];
   NSValue* keyboardFrameBegin = [keyboardInfo valueForKey:UIKeyboardFrameBeginUserInfoKey];
   CGRect keyboardFrameBeginRect = [keyboardFrameBegin CGRectValue];
   
   CGFloat keyboardH = MIN(keyboardFrameBeginRect.size.width, keyboardFrameBeginRect.size.height);
   
   CGRect screenRect = [[UIScreen mainScreen] bounds];
   
   UIInterfaceOrientation orientation = [UIApplication sharedApplication].statusBarOrientation;
   
   CGFloat screenHeight;
   if (UIInterfaceOrientationIsLandscape(orientation)) {
      screenHeight = screenRect.size.width;
   } else {
      screenHeight = screenRect.size.height;
   }
   
   CGFloat h = screenHeight - keyboardH;
   
   CGRect tfRect = self.tfEditText.frame;
   
   [UIView animateWithDuration:0.1 animations:^(void) {
      CGRect newRect = CGRectMake(tfRect.origin.x, h/2, tfRect.size.width, tfRect.size.height);
      self.tfEditText.frame = newRect;
      [self.tfEditText selectAll:self];
   }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)doCancel:(id)sender {
   [self.delegate textEditControllerDidCancel:self];
}

- (IBAction)doSave:(id)sender {
   [self.delegate textEditController:self didFinishEditText:self.tfEditText.text];
}
@end
