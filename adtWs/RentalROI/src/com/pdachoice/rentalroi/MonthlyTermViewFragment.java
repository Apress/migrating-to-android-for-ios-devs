package com.pdachoice.rentalroi;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.pdachoice.rentalroi.model.RentalProperty;

public class MonthlyTermViewFragment extends Fragment {
//  @property (nonatomic, strong) NSDictionary* monthlyTerm;
//  
//  @property (weak, nonatomic) IBOutlet TextView lbPaymentNo;
//  @property (weak, nonatomic) IBOutlet TextView lbTotalPayment;
//  @property (weak, nonatomic) IBOutlet TextView lbPrinciplePayment;
//  @property (weak, nonatomic) IBOutlet TextView lbInterestPayment;
//  @property (weak, nonatomic) IBOutlet TextView lbEscrowPayment;
//  @property (weak, nonatomic) IBOutlet TextView lbAddlPayment;
//  @property (weak, nonatomic) IBOutlet TextView lbMorgageDebt;
//  @property (weak, nonatomic) IBOutlet TextView lbEquityInvestment;
//  @property (weak, nonatomic) IBOutlet TextView lbCashInvested;
//  @property (weak, nonatomic) IBOutlet TextView lbRoi;

  JSONObject monthlyTerm;

  TextView lbPaymentNo;
  TextView lbTotalPayment;
  TextView lbPrinciplePayment;
  TextView lbInterestPayment;
  TextView lbEscrowPayment;
  TextView lbAddlPayment;
  TextView lbMorgageDebt;
  TextView lbEquityInvestment;
  TextView lbCashInvested;
  TextView lbRoi;
  
  private View contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.monthlytermview_fragment, container, false);
    
    lbPaymentNo = (TextView)contentView.findViewById(R.id.lbPaymentNo);
    lbTotalPayment = (TextView)contentView.findViewById(R.id.lbTotalPayment);
    lbPrinciplePayment = (TextView)contentView.findViewById(R.id.lbPrinciplePayment);
    lbInterestPayment = (TextView)contentView.findViewById(R.id.lbInterestPayment);
    lbEscrowPayment = (TextView)contentView.findViewById(R.id.lbEscrowPayment);
    lbAddlPayment = (TextView)contentView.findViewById(R.id.lbAddlPayment);
    lbMorgageDebt = (TextView)contentView.findViewById(R.id.lbMorgageDebt);
    lbEquityInvestment = (TextView)contentView.findViewById(R.id.lbEquityInvestment);
    lbCashInvested = (TextView)contentView.findViewById(R.id.lbCashInvested);
    lbRoi = (TextView)contentView.findViewById(R.id.lbRoi);
    
    viewDidLoad();
    return contentView;
  }
  
  @Override
  public void onResume() {
      super.onResume();
      ((MainActivity) getActivity()).slideIn(contentView, MainActivity.SLIDE_LEFT);

      getActivity().setTitle(getText(R.string.label_monthlydetails));

      EasyTracker tracker =  EasyTracker.getInstance(getActivity());
      tracker.set(Fields.SCREEN_NAME, this.getClass().getSimpleName());
      tracker.send(MapBuilder.createAppView().build());
  }

  
  // copy from iOS counterpart impl file

  private void viewDidLoad() {
//  double principle = [[self.monthlyTerm objectForKey: @"principal" ] doubleValue];
//  double interest = [[self.monthlyTerm objectForKey: @"interest" ] doubleValue];
//  double escrow = [[self.monthlyTerm objectForKey: @"escrow" ] doubleValue];
//  double extra = [[self.monthlyTerm objectForKey: @"extra" ] doubleValue];
//  double balance = [[self.monthlyTerm objectForKey: @"balance0" ] doubleValue] - principle;
//  int paymentPeriod = [[self.monthlyTerm objectForKey: @"pmtNo" ] intValue];
//  double totalPmt = principle + interest + escrow + extra;
//
//  RentalProperty* property = [RentalProperty sharedInstance];
//  double invested = property.purchasePrice - property.loanAmt + property.extra * paymentPeriod;
//  double net = property.rent - escrow - interest - property.expenses;
//  double roi = net * 12 / invested;
//
//  self.lbTotalPayment.text = [NSString stringWithFormat: @"$%.2f", totalPmt];
//  self.lbPaymentNo.text = [NSString stringWithFormat: @"No. %d", paymentPeriod];
//  self.lbPrinciplePayment.text = [NSString stringWithFormat: @"$%.2f", principle];
//  self.lbInterestPayment.text = [NSString stringWithFormat: @"$%.2f", interest];
//  self.lbEscrowPayment.text = [NSString stringWithFormat: @"$%.2f", escrow];
//  self.lbAddlPayment.text = [NSString stringWithFormat: @"$%.2f", extra];
//  self.lbMorgageDebt.text = [NSString stringWithFormat: @"$%.2f", balance];
//  self.lbEquityInvestment.text = [NSString stringWithFormat: @"$%.2f", property.purchasePrice - balance];
//  self.lbCashInvested.text = [NSString stringWithFormat: @"$%.2f", invested];
//  self.lbRoi.text = [NSString stringWithFormat:@"%.2f%% ($%.2f/mo)", roi *100, net];
    
    double principle = this.monthlyTerm.optDouble("principal");
    double interest = this.monthlyTerm.optDouble("interest");
    double escrow = this.monthlyTerm.optDouble("escrow");
    double extra = this.monthlyTerm.optDouble("extra");
    double balance = this.monthlyTerm.optDouble("balance0") - principle;
    int paymentPeriod = this.monthlyTerm.optInt("pmtNo");

    double totalPmt = principle + interest + escrow + extra;

    this.lbTotalPayment.setText(String.format("$%.2f", totalPmt));
    this.lbPaymentNo.setText(String.format("No. %d", paymentPeriod));
    this.lbPrinciplePayment.setText(String.format("$%.2f", principle));
    this.lbInterestPayment.setText(String.format("$%.2f", interest));
    this.lbEscrowPayment.setText(String.format("$%.2f", escrow));
    this.lbAddlPayment.setText(String.format("$%.2f", extra));

    this.lbMorgageDebt.setText(String.format("$%.2f", balance));

    RentalProperty property = RentalProperty.sharedInstance();
    double invested = property.purchasePrice - property.loanAmt + property.extra * paymentPeriod;
    double net = property.rent - escrow - interest - property.expenses;
    double roi = net * 12 / invested;

    this.lbEquityInvestment.setText(String.format("$%.2f", property.purchasePrice - balance));
    this.lbCashInvested.setText(String.format("$%.2f", invested));
    this.lbRoi.setText(String.format("%.2f%% ($%.2f/mo)", roi * 100, net));
  }  
}