package com.pdachoice.dialogs;

import com.pdachoice.dialogs.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ScreenOneFragment extends Fragment implements OnClickListener,
    DialogInterface.OnClickListener {
  View contentView;
  private View alertButton;
  private View buttonPopup;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenone_fragment, container,
        false);

    alertButton = contentView.findViewById(R.id.buttonAlert);
    alertButton.setOnClickListener(this); // Delegate button OnClick events
    
    buttonPopup = contentView.findViewById(R.id.buttonPopup);
    buttonPopup.setOnClickListener(this); // Delegate button OnClick events
    return contentView;
  }

  @Override
  public void onClick(View v) {
    // b. each button/view has different id.
    int btnViewId = v.getId();
    if (btnViewId == R.id.buttonAlert) {
      showAlert();
    } else if (btnViewId == R.id.buttonPopup) {
      showPopup();
    }
  }

  // c. same Dialog.show(...) API to show the dialog.
  private void showPopup() {
    ColorPickerPopupFragment dialog = new ColorPickerPopupFragment();
    dialog.show(getFragmentManager(), dialog.getClass().getSimpleName());
  }

  private void showAlert() {
    // a. create builder instance.
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // b. set alert dialog content view
    // b1: Title with an icon
    builder.setTitle("Attention").setIcon(android.R.drawable.ic_menu_help);

    // b2: content area message.
    builder.setMessage("Standard User Alert");

    // b3: max 3 buttons.
    builder.setPositiveButton("Ok", this);
    builder.setNegativeButton("Cancel", this);

    // c. make it a modal dialog
    builder.setCancelable(false);

    // d. create the dialog instance and show it.
    AlertDialog dialog = builder.create();
    dialog.show();
  }

  // e. DialogInterface.OnClickListener interface impl.
  @Override
  public void onClick(DialogInterface dialog, int id) {
    if (id == DialogInterface.BUTTON_POSITIVE)
      Log.d("", "AlertDialog.onClick: " + "OK'd");
    else
      Log.d("", "AlertDialog.onClick: " + "Canceled");
  }

}