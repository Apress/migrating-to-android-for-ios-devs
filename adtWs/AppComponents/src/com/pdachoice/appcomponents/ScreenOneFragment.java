package com.pdachoice.appcomponents;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ScreenOneFragment extends Fragment implements OnClickListener {
  private View contentView;
  private Button btnExplicit;
  private Button btnImplicit;
  private Button btnUseService;
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenone_fragment, container,
        false);

    btnExplicit = (Button) contentView.findViewById(R.id.btnExplicit);
    btnImplicit = (Button) contentView.findViewById(R.id.btnImplicit);
    btnUseService = (Button) contentView.findViewById(R.id.btnUseService);
    
    btnExplicit.setOnClickListener(this);
    btnImplicit.setOnClickListener(this);
    btnUseService.setOnClickListener(this);

    return contentView;
  }

  @Override
  public void onClick(View v) {
    Log.v("SceenOneFragment", "onClick");
    if (v == btnExplicit) {
      useExplicitIntent();
    } else if (v == btnImplicit) {
      useImplicitIntent();
    } else if (v == btnUseService) {
      useHelloService();
    } else {

    }
  }

  private void useImplicitIntent() {
    String action ="com.pdachoice.restclient.SOME_NAME";
    String category = "android.intent.category.DEFAULT";
    
    Intent intent = new Intent(action);
    intent.addCategory(category);
    
    intent.putExtra("name", "Hi, from AppComponent");

    try {
      getActivity().startActivity(intent);
    } catch (ActivityNotFoundException e) {
      // TODO: you may direct to Google Play to install the app
      Log.d("", e.getLocalizedMessage());
    }
  }

  private void useExplicitIntent() {
    String pkg = "com.pdachoice.restclient";
    String cls = "com.pdachoice.restclient.MainActivity";
    ComponentName component = new ComponentName(pkg, cls);
    Intent intent = new Intent();
    intent.setComponent(component);

    try {
      getActivity().startActivity(intent);
    } catch (ActivityNotFoundException e) {
      // TODO: you may direct to Google Play to install the app
      Log.d("", e.getLocalizedMessage());
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  void useHelloService() {
    ComponentName component = new ComponentName("com.pdachoice.appcomponents",
        "com.pdachoice.appcomponents.HelloService");

    Intent intent = new Intent();
    intent.setComponent(component);

    intent.putExtra("fname", "Mobile");
    intent.putExtra("lname", "Developer");

    // getActivity().startService(intent);
    // Create PendingIntent to wrap the original intent with extra parms
    try {
      PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0,
          intent, PendingIntent.FLAG_UPDATE_CURRENT);

      pendingIntent.send();
    } catch (CanceledException e) {
      e.printStackTrace();
    }
  }

}