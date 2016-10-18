package com.pdachoice.rentalroi;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;

public class AmortizationViewFragment extends ListFragment {
  
//  @interface AmortizationViewController : UITableViewController
//
//  @property (nonatomic, strong) NSArray* monthlyTerms;
//
//  @end
  public JSONArray monthlyTerms;
  private BaseAdapter mAdapter;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new BaseAdapter() {

      @Override
      public View getView(int pos, View view, ViewGroup parent) {
//      UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
//      // Configure the cell...
//      NSDictionary* monthlyTerm = [self.monthlyTerms objectAtIndex:indexPath.row];
//      int pmtNo = [[monthlyTerm objectForKey:@"pmtNo"] intValue];
//      double balance0 = [[monthlyTerm objectForKey:@"balance0"] doubleValue];
//      cell.textLabel.text = [NSString stringWithFormat: @"%d\t$%.2f", pmtNo, balance0];
//      double interest = [[monthlyTerm objectForKey:@"interest"] doubleValue];
//      double principal = [[monthlyTerm objectForKey:@"principal"] doubleValue];
//      cell.detailTextLabel.text = [NSString stringWithFormat:@"Interest: %.2f\tPrinciple: %.2f", interest, principal];
        
        if (view == null) { // check null first before recycled object.
          view = getActivity().getLayoutInflater().inflate(R.layout.monthlyterm_listitem, null);
        }
        TextView textLabel = (TextView) view.findViewById(R.id.textLabel);
        TextView detailTextLabel = (TextView) view.findViewById(R.id.detailTextLabel);

        JSONObject monthlyTerm = (JSONObject) monthlyTerms.opt(pos);       
        int pmtNo = monthlyTerm.optInt("pmtNo");
        double balance0 = monthlyTerm.optDouble("balance0");
        textLabel.setText(String.format("%d\t$%.2f", pmtNo, balance0));

        double interest = monthlyTerm.optDouble("interest");
        double principal = monthlyTerm.optDouble("principal");
        detailTextLabel.setText(String.format("Interest: %.2f\tPrinciple: %.2f", interest, principal)); 
        return view;        
      }

      @Override
      public int getCount() {
//      return [self.monthlyTerms count];
        return monthlyTerms.length();
      }

      @Override
      public Object getItem(int pos) {
        JSONObject monthlyTerm = (JSONObject) monthlyTerms.opt(pos);
        return monthlyTerm;
      }

      @Override
      public long getItemId(int pos) {
        return pos;
      }

    };
    this.setListAdapter(mAdapter);    
  }
  
  @Override
  public void onResume() {
      super.onResume();
      ((MainActivity) getActivity()).slideIn(getView(), MainActivity.SLIDE_LEFT); 

      getActivity().setTitle(getText(R.string.label_Amortization));     
      EasyTracker tracker =  EasyTracker.getInstance(getActivity());
      tracker.set(Fields.SCREEN_NAME, this.getClass().getSimpleName());
      tracker.send(MapBuilder.createAppView().build());
  }
  

//  #pragma mark - Table view data source

//  - (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
//     return [self.monthlyTerms count];
//  }
//
//  - (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
//     static NSString *CellIdentifier = @"Cell";
//     UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
//     
//     // Configure the cell...
//     NSDictionary* monthlyTerm = [self.monthlyTerms objectAtIndex:indexPath.row];
//     int pmtNo = [[monthlyTerm objectForKey:@"pmtNo"] intValue];
//     double balance0 = [[monthlyTerm objectForKey:@"balance0"] doubleValue];
//     cell.textLabel.text = [NSString stringWithFormat: @"%d\t$%.2f", pmtNo, balance0];
//     double interest = [[monthlyTerm objectForKey:@"interest"] doubleValue];
//     double principal = [[monthlyTerm objectForKey:@"principal"] doubleValue];
//     cell.detailTextLabel.text = [NSString stringWithFormat:@"Interest: %.2f\tPrinciple: %.2f", interest, principal];
//     
//     return cell;
//  }
//
//  -(void) tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
//     NSDictionary* monthlyTerm = [self.monthlyTerms objectAtIndex:indexPath.row];
//     [self performSegueWithIdentifier:@"toMonthlyTerm" sender:monthlyTerm];
//  }

//  #pragma mark - Navigation
//
//  // In a story board-based application, you will often want to do a little preparation before navigation
//  - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
//     MonthlyTermViewController* destVc = segue.destinationViewController;
//     destVc.monthlyTerm = sender;
//  }  
  
  public void onListItemClick(ListView l, View v, int position, long id) {
    MonthlyTermViewFragment toFrag = new MonthlyTermViewFragment();

    JSONObject jo = (JSONObject) mAdapter.getItem(position);
    toFrag.monthlyTerm = jo;

    ((MainActivity) getActivity()).pushViewController(toFrag, true);
  }
}