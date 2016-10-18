package com.pdachoice.rentalroi;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.MapBuilder;
import com.pdachoice.rentalroi.EditTextViewFragment.EditTextViewControllerDelegate;
import com.pdachoice.rentalroi.model.RentalProperty;

public class RentalPropertyViewFragment extends ListFragment implements EditTextViewControllerDelegate {

  // #import <UIKit/UIKit.h>
  //
  // @interface RentalPropertyViewController : UITableViewController
  //
  // @property (weak, nonatomic) IBOutlet UILabel *lbPurchasePrice;
  // @property (weak, nonatomic) IBOutlet UILabel *lbDownPayment;
  // @property (weak, nonatomic) IBOutlet UILabel *lbLoanAmt;
  // @property (weak, nonatomic) IBOutlet UILabel *lbInterestRate;
  // @property (weak, nonatomic) IBOutlet UILabel *lbNumOfTerms;
  // @property (weak, nonatomic) IBOutlet UILabel *lbEscrow;
  // @property (weak, nonatomic) IBOutlet UILabel *lbExtra;
  // @property (weak, nonatomic) IBOutlet UILabel *lbExpenses;
  // @property (weak, nonatomic) IBOutlet UILabel *lbRent;
  //
  // - (IBAction)doAmortization:(id)sender;
  // @end

  // @Override
  // public void onSaveInstanceState(Bundle outState) {
  // // TODO Auto-generated method stub
  // super.onSaveInstanceState(outState);
  // outState.putSerializable("_property", _property);
  //
  // }

  private final static String tag = RentalPropertyViewFragment.class.getSimpleName();

  private BaseAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewDidLoad();

    setHasOptionsMenu(true); // enable Option Menu.

    mAdapter = new BaseAdapter() {
      
      @Override
      public int getItemViewType(int pos) {
        if(pos == 0 || pos == 8) {
          return 0;
        } else {
          return 1;
        }
      }

      @Override
      public int getViewTypeCount() {
        return 2;
      }

      @Override
      public View getView(int pos, View view, ViewGroup parent) {

        if (view == null) { // check null first before recycled object.
          LayoutInflater inflater = getActivity().getLayoutInflater();
          if (pos == 0 || pos == 8) {
            // header list item
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);
          } else {
            // right detail list item
            view = inflater.inflate(R.layout.rightdetail_listitem, null);
          }
        }

        if (pos == 0 || pos == 8) {
          // header list item
          view.setBackgroundColor(Color.argb(32, 0, 128, 128));
          TextView text1 = (TextView) view.findViewById(android.R.id.text1);

          if (pos == 0) {
            text1.setText(getResources().getString(R.string.morgtage));
          } else {
            text1.setText(getResources().getString(R.string.operations));
          }
        } else {
          // right detail list item
          view.setBackgroundColor(Color.argb(0, 0, 0, 0));
          TextView textLabel = (TextView) view.findViewById(R.id.textLabel);
          TextView detailTextLabel = (TextView) view.findViewById(R.id.detailTextLabel);

          switch (pos) {
          case 1:
            textLabel.setText(R.string.purchasePrice);
            detailTextLabel.setText(String.format("%.0f", _property.purchasePrice));
            break;
          case 2:
            textLabel.setText(R.string.downPayment);
            double down = (1 - _property.loanAmt / _property.purchasePrice) * 100.0f;
            if (_property.purchasePrice > 0) {
              detailTextLabel.setText(String.format("%.0f", down));

              if (_property.loanAmt == 0 && down > 0) {
                _property.loanAmt = _property.purchasePrice * (1 - down / 100.0f);
              }
            } else {
              detailTextLabel.setText("0");
            }
            break;
          case 3:
            textLabel.setText(R.string.loanAmount);
            detailTextLabel.setText(String.format("%.2f", _property.loanAmt));
            break;
          case 4:
            textLabel.setText(R.string.interestRate);
            detailTextLabel.setText(String.format("%.2f", _property.interestRate));
            break;
          case 5:
            textLabel.setText(R.string.mortgageTerm);
            detailTextLabel.setText(String.format("%d", _property.numOfTerms));
            break;
          case 6:
            textLabel.setText(R.string.escrowAmount);
            detailTextLabel.setText(String.format("%.0f", _property.escrow));
            break;
          case 7:
            textLabel.setText(R.string.extraPayment);
            detailTextLabel.setText(String.format("%.0f", _property.extra));
            break;
          case 9:
            textLabel.setText(R.string.expenses);
            detailTextLabel.setText(String.format("%.0f", _property.expenses));
            break;
          case 10:
            textLabel.setText(R.string.rent);
            detailTextLabel.setText(String.format("%.0f", _property.rent));
            break;

          default:
            break;
          }
        }

        return view;
      } 

      @Override
      public int getCount() {
        return 11; // 2 section + 9 fields
      }

      @Override
      public long getItemId(int pos) {
        return pos; // not used
      }

      @Override
      public Object getItem(int pos) {
        TextView textLabel = (TextView) getView(pos, null, null).findViewById(R.id.textLabel);
        if (textLabel == null) {
          return null;
        } else {
          TextView detailTextLabel = (TextView) getView(pos, null, null).findViewById(R.id.detailTextLabel);
          NameValuePair nvp = new BasicNameValuePair(textLabel.getText().toString(), detailTextLabel.getText().toString());
          return nvp;
        }
      }

    };
    this.setListAdapter(mAdapter);
  }

  @Override
  public void onResume() {
    super.onResume();
    getActivity().setTitle(getText(R.string.label_property));

    EasyTracker tracker = EasyTracker.getInstance(getActivity());
    tracker.set(Fields.SCREEN_NAME, this.getClass().getSimpleName());
    tracker.send(MapBuilder.createAppView().build());
  }

  // callback method when list item is selected.
  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // position 0 and 8 are header
    if (position == 0 || position == 8) {
      return;
    }

    EditTextViewFragment toFrag = new EditTextViewFragment();

    NameValuePair data = (NameValuePair) mAdapter.getItem(position);
    
    toFrag.tag = position;
    toFrag.header = data.getName();
    toFrag.text = data.getValue();

    toFrag.delegate = this;

    ((MainActivity) getActivity()).pushViewController(toFrag, true);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Log.d(tag, "onOptionsItemSelected: " + item.getItemId());
    doAmortization(null);
    // ((MainActivity)getActivity()).pushViewController(new
    // AmortizationViewFragment(), true);
    return true;
  }

  // /// RentalPropertyViewController.m
  private static final String URL_service_tmpl = "http://www.pdachoice.com/ras/service/amortization?loan=%.2f&rate=%.2f&terms=%d&extra=%.2f";

  // @interface RentalPropertyViewController () <EditTextViewControllerDelegate>
  // {
  // RentalProperty* _property;
  // }
  // @end

  private RentalProperty _property;

  private JSONArray savedAmortization;

  private void viewDidLoad() {
    // [super viewDidLoad);
    _property = RentalProperty.sharedInstance();
    _property.load(getActivity());

  }

  private void didSelectRowAtIndexPath(int indexPath) {
    // NSLog(">> didSelectRowAtIndexPath");
    // [this.performSegueWithIdentifier:"toEditText" sender: indexPath);
  }

  //
  private void prepareForSegue(/* UIStoryboardSegue segue Object sender */) {
    // String segueId = segue.identifier;
    // if ([segueId isEqualToString:"toAmortization"]) {
    // AmortizationViewController amoVc = segue.destinationViewController;
    // amoVc.monthlyTerms = sender;
    // return;
    // }
    //
    // NSIndexPath indexPath = sender;
    // NSInteger section = indexPath.section;
    // NSInteger row = indexPath.row;
    //
    // id src;
    // String header;
    // if (section == 0) {
    // switch (row) {
    // case 0:
    // src = this.lbPurchasePrice;
    // header = "Purchase Price";
    // break;
    //
    // case 1:
    // src = this.lbDownPayment;
    // header = "Down Payment (%)";
    // break;
    //
    // case 2:
    // src = this.lbLoanAmt;
    // header = "Loan Amount";
    // break;
    //
    // case 3:
    // src = this.lbInterestRate;
    // header = "Interest Rate (%)";
    // break;
    //
    // case 4:
    // src = this.lbNumOfTerms;
    // header = "Mortgage Terms (Yr.)";
    // break;
    //
    // case 5:
    // src = this.lbEscrow;
    // header = "Escrow";
    // break;
    //
    // case 6:
    // src = this.lbExtra;
    // header = "Extra Payment";
    // break;
    //
    // default:
    // break;
    // }
    // } else {
    // switch (row) {
    // case 0:
    // src = this.lbExpenses;
    // header = "Expense";
    // break;
    //
    // case 1:
    // src = this.lbRent;
    // header = "Rent";
    // break;
    //
    // default:
    // break;
    // }
    // }
    //
    // EditTextViewController destVc = segue.destinationViewController;
    // destVc.src = src;
    // destVc.header = header;
    // destVc.delegate = self;
  }

  public void textEditControllerDidFinishEditText(EditTextViewFragment controller, String text) {
    ((MainActivity) getActivity()).popViewController();
    
    int tag = controller.tag;
    switch (tag) {
    case 1:
      _property.purchasePrice = Double.parseDouble(text); // [text doubleValue];

      String percent = ((NameValuePair) mAdapter.getItem(2)).getValue();
      double down = Double.parseDouble(percent); // [self.lbDownPayment.text
                                                 // doubleValue];
      if (_property.purchasePrice > 0 && _property.loanAmt == 0 && down > 0) {
        _property.loanAmt = _property.purchasePrice * (1 - down / 100.0f);
      } else {
        // down = 1.0f - (_property.loanAmt / _property.purchasePrice);
      }

      break;
    case 2:
      float percentage = Float.parseFloat(text) / 100.0f; // [text floatValue] /
                                                          // 100.0;
      _property.loanAmt = _property.purchasePrice * (1 - percentage);

      break;
    case 3:
      _property.loanAmt = Double.parseDouble(text); // [text doubleValue];

      break;
    case 4:
      _property.interestRate = Double.parseDouble(text); // [text doubleValue];

      break;
    case 5:
      _property.numOfTerms = Integer.parseInt(text); // [text intValue];
      break;
    case 6:
      _property.escrow = Double.parseDouble(text);

      break;
    case 7:
      _property.extra = Double.parseDouble(text);

      break;
    case 9:
      _property.expenses = Double.parseDouble(text);

      break;
    case 10:
      _property.rent = Double.parseDouble(text);

      break;

    default:
      break;
    }
    mAdapter.notifyDataSetChanged();
    _property.save(getActivity());
  }

  public void textEditControllerDidCancel(EditTextViewFragment controller) {
    // [self dismissViewControllerAnimated:YES completion:nil];
    ((MainActivity)getActivity()).popViewController();
  }

  private static final String KEY_DATA = "data";
  private static final String KEY_RC = "rc";
  private static final String KEY_ERROR = "error";

  public void doAmortization(Object sender) {
    // NSArray* savedAmortization = [_property getSavedAmortization];
    //
    // if (savedAmortization != nil) {
    // [self performSegueWithIdentifier:@"toAmortization"
    // sender:savedAmortization];
    // } else {
    // NSString* url = [NSString stringWithFormat: URL_service_tmpl,
    // _property.loanAmt,
    // _property.interestRate,
    // _property.numOfTerms,
    // _property.extra];
    //
    // [UIApplication sharedApplication].networkActivityIndicatorVisible = YES;
    // NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL
    // URLWithString: url]];
    // [NSURLConnection sendAsynchronousRequest:request queue:[NSOperationQueue
    // currentQueue]
    // completionHandler:^(NSURLResponse *response, NSData *data, NSError
    // *error) {
    // id jsonObject = nil;
    // [UIApplication sharedApplication].networkActivityIndicatorVisible = NO;
    //
    // if(error == nil) {
    // jsonObject = [NSJSONSerialization
    // JSONObjectWithData:data
    // options:NSJSONReadingAllowFragments
    // error:&error];
    //
    // NSLog(@"jsonObject: %@", jsonObject);
    // [_property saveAmortization:jsonObject];
    // [self performSegueWithIdentifier:@"toAmortization" sender:jsonObject];
    // } else {
    // [[[UIAlertView alloc] initWithTitle: error.domain message:
    // error.localizedDescription delegate:nil cancelButtonTitle:@"Close"
    // otherButtonTitles: nil] show];
    // }
    // }];
    // }

    savedAmortization = _property.getSavedAmortization(getActivity());
    if (savedAmortization != null) {
      AmortizationViewFragment toFrag = new AmortizationViewFragment();      
      toFrag.monthlyTerms = savedAmortization;
      
      ((MainActivity) getActivity()).pushViewController(toFrag, true);
    } else {

      String url = String.format(URL_service_tmpl, _property.loanAmt, _property.interestRate, _property.numOfTerms, _property.extra);

      getActivity().setProgressBarIndeterminate(true);
      getActivity().setProgressBarVisibility(true);

      AsyncTask<String, Float, JSONObject> task = new AsyncTask<String, Float, JSONObject>() {
        @Override
        protected JSONObject doInBackground(String... parms) {
          Log.d("AsyncTask", ">>>doInBackground");
          String getUrl = parms[0];
          JSONObject jo = httpGet(getUrl);
          Log.d("KEY_DATA", jo.optString(KEY_DATA));
          return jo;
        }

        @Override
        protected void onPostExecute(JSONObject jo) {
          getActivity().setProgressBarVisibility(false);
          Exception error = (Exception) jo.opt(KEY_ERROR);
          if (error == null) {
            AmortizationViewFragment toFrag = new AmortizationViewFragment();
            String data = jo.optString(KEY_DATA);
            _property.saveAmortization(data, getActivity());

            try {
              toFrag.monthlyTerms = new JSONArray(data);
              ((MainActivity) getActivity()).pushViewController(toFrag, true);
            } catch (JSONException e) {
              e.printStackTrace();
            }
          } else {
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
          }
        }
      };
      task.execute(url);
    }
  }

  // GET data from url
  private JSONObject httpGet(String myurl) {
    InputStream in = null;
    HttpURLConnection conn = null;

    JSONObject jo = new JSONObject();
    try {
      URL url = new URL(myurl);
      // create an HttpURLConnection by openConnection
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("accept", "application/json");

      int rc = conn.getResponseCode(); // HTTP status code
      String rm = conn.getResponseMessage(); // HTTP response message.
      Log.d("d", String.format("HTTP GET: %d %s", rc, rm));

      // read message body from connection InputStream
      in = conn.getInputStream(); // get inputStream to read data.
      String httpBody = readStream(in);
      in.close();

      jo.put(KEY_RC, rc);
      jo.put(KEY_DATA, httpBody);

    } catch (Exception e) {
      e.printStackTrace();
      try {
        jo.putOpt(KEY_ERROR, e);
      } catch (JSONException e1) {
        e1.printStackTrace();
      }
    } finally {
      conn.disconnect();
    }

    return jo;
  }

  // a simple util method that converts InputStream to a String.
  String readStream(InputStream stream) {
    java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

}