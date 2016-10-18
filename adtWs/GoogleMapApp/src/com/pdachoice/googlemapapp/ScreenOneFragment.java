package com.pdachoice.googlemapapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationClient.OnRemoveGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScreenOneFragment extends Fragment implements LocationListener,
    ConnectionCallbacks, OnConnectionFailedListener {

  static final LatLng MyPosition = new LatLng(33.6716998, -117.7998752);
  static final LatLng Newport = new LatLng(33.618910, -117.928947);
  static final LatLng Vancouver = new LatLng(49.261226, -123.113927);
  static final float RADIUS = 100; // in meters

  private View contentView;
  private GoogleMap googleMap;

  private LocationClient locationClient;
  private PendingIntent pendingIntent;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // check location service
    this.checkSystemPreferences();

    locationClient = new LocationClient(getActivity(), this, this);
    locationClient.connect();
  }

  @Override
  public void onStop() {
    super.onStop();
    locationClient.removeLocationUpdates(this);
    locationClient.removeGeofences(pendingIntent,
        new OnRemoveGeofencesResultListener() {

          @Override
          public void onRemoveGeofencesByRequestIdsResult(int arg0,
              String[] arg1) {
            // no-op
          }

          @Override
          public void onRemoveGeofencesByPendingIntentResult(int arg0,
              PendingIntent arg1) {
            // no-op
          }
        });

    locationClient.disconnect();
  }

  @Override
  public void onConnected(Bundle connectionHint) {
    Toast.makeText(getActivity(), "onConnected", Toast.LENGTH_SHORT).show();
    getLastLocation();
    requestLocationUpdates();
    monitorGeofences(MyPosition, Newport);
  }

  @Override
  public void onDisconnected() {
    Toast.makeText(getActivity(), "onDisconnected", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onConnectionFailed(ConnectionResult arg0) {
    Toast.makeText(getActivity(), "onConnectionFailed: ", Toast.LENGTH_SHORT)
        .show();
  }

  private void getLastLocation() {
    Location currentLocation = locationClient.getLastLocation();
    if (currentLocation != null) { // It can be null
      double lat = currentLocation.getLatitude();
      double lng = currentLocation.getLongitude();
      setLocation(new LatLng(lat, lng));
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.screenone_fragment, container,
        false);

    this.setHasOptionsMenu(true);

    // Make sure service is available
    int code = GooglePlayServicesUtil
        .isGooglePlayServicesAvailable(getActivity());
    if (code != ConnectionResult.SUCCESS) {
      Toast.makeText(getActivity(), "Google Play Service Error: " + code,
          Toast.LENGTH_LONG).show();
    }

    // Obtain the googleMap from the MapFragment.getMap().
    Fragment mapFragment = getFragmentManager().findFragmentById(R.id.map);
    googleMap = ((SupportMapFragment) mapFragment).getMap();

    // Null check to make sure the map is ready.
    if (googleMap != null) {
      googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
      // enable the My Location button on the top right corner.
      googleMap.setMyLocationEnabled(true);

      // map attributes
      googleMap.getUiSettings().setAllGesturesEnabled(true);
      googleMap.getUiSettings().setCompassEnabled(true);
    }

    return contentView;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    case R.id.action_location:
      setLocation(MyPosition);
      break;
    case R.id.action_marker:
      setMarker(MyPosition, Newport, Vancouver);

      break;
    case R.id.action_circle:
      setCircle(MyPosition, Newport, Vancouver);
      break;
    default:
      break;
    }

    return super.onOptionsItemSelected(item);
  }

  private void setLocation(LatLng latlng) {
    CameraUpdate update = CameraUpdateFactory.newLatLng(latlng);
    googleMap.animateCamera(update);
    this.geocoding(latlng);
  }

  private void setMarker(LatLng... latlngs) {
    MarkerOptions markerOptions;
    for (int i = 0; i < latlngs.length; i++) {
      markerOptions = new MarkerOptions();
      markerOptions.position(latlngs[i]);
      markerOptions.title("Marker Title").snippet("snippet: some text");
      markerOptions.visible(true).draggable(true);
      markerOptions.icon(BitmapDescriptorFactory
          .fromResource(android.R.drawable.ic_menu_myplaces));
      googleMap.addMarker(markerOptions);
    }
  }

  private void setCircle(LatLng... latlngs) {
    CircleOptions circleOptions;
    for (int i = 0; i < latlngs.length; i++) {
      circleOptions = new CircleOptions().center(latlngs[i]);
      circleOptions.radius(RADIUS); // meters
      circleOptions.fillColor(Color.argb(192, 255, 192, 192));
      circleOptions.strokeColor(Color.RED).strokeWidth(5);
      circleOptions.visible(true).zIndex(0);
      googleMap.addCircle(circleOptions);
    }
  }

  private void checkSystemPreferences() {
    // Check whether Location Access is enabled in System Settings
    ContentResolver resolver = getActivity().getContentResolver();
    String providers = Secure.getString(resolver,
        Secure.LOCATION_PROVIDERS_ALLOWED);

    // It is nice to let users know what is wrong
    if (providers == null || providers.length() <= 0) {
      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setTitle("Location Access Not Enabled?");
      builder.setMessage("Go to System Settings?");
      builder.setIcon(android.R.drawable.ic_dialog_alert);
      builder.setPositiveButton("YES", new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          // c. send users to System Settings app
          Intent intent = new Intent(
              android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          startActivity(intent);
        }
      });

      builder.setNegativeButton("NO", null).create().show();
    }
  }

  private void geocoding(LatLng loc) {
    AsyncTask<LatLng, Void, List<Address>> task = new AsyncTask<LatLng, Void, List<Address>>() {

      @Override
      protected void onPostExecute(List<Address> addresses) {
        String addressText;
        if (addresses != null && addresses.size() > 0) {
          // Get the first address
          addressText = addresses.get(0).toString();
        } else {
          addressText = "No address found";
        }
        Toast.makeText(getActivity(), addressText, Toast.LENGTH_SHORT).show();
      }

      @Override
      protected List<Address> doInBackground(LatLng... locs) {
        Geocoder coder = new Geocoder(getActivity(), Locale.getDefault());
        LatLng loc = locs[0];
        List<Address> addresses = null;
        try {
          // get one address
          addresses = coder.getFromLocation(loc.latitude, loc.longitude, 1);
        } catch (IOException e) {
          e.printStackTrace();
        } catch (IllegalArgumentException e) {
          e.printStackTrace();
        }

        return addresses;
      }
    };

    task.execute(loc);
  }

  private void requestLocationUpdates() {
    // create LocationRequest object to specify how to receive updates
    LocationRequest request = LocationRequest.create();
    request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    // request.setFastestInterval(1000);
    // request.setNumUpdates(1);
    request.setInterval(3000); // 3 secs
    request.setSmallestDisplacement(RADIUS / 10);

    locationClient.requestLocationUpdates(request, this);
  }

  @Override
  public void onLocationChanged(Location loc) {
    Toast.makeText(getActivity(), "onLocationChanged", Toast.LENGTH_SHORT)
        .show();
    if (loc != null) {
      setLocation(new LatLng(loc.getLatitude(), loc.getLongitude()));
    }
  }

  private void monitorGeofences(LatLng... locations) {
    List<Geofence> fences = new ArrayList<Geofence>();

    Geofence.Builder builder = new Geofence.Builder();
    for (int i = 0; i < locations.length; i++) {
      // use Geofence.Builder to create Geofence object
      Geofence fence = builder
          .setRequestId("RequestId:" + i)
          .setTransitionTypes(
              Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
          .setCircularRegion(locations[i].latitude, locations[i].longitude,
              RADIUS).setExpirationDuration(Geofence.NEVER_EXPIRE).build();
      fences.add(fence);
    }

    Intent intent = new Intent(getActivity(), MonitorGeofenceService.class);
    pendingIntent = PendingIntent.getService(getActivity(), 0, intent, 0);

    locationClient.addGeofences(fences, pendingIntent,
        new OnAddGeofencesResultListener() {

          public void onAddGeofencesResult(int statusCode,
              String[] geofenceRequestIds) {
            if (LocationStatusCodes.SUCCESS == statusCode) {
              Toast.makeText(getActivity(),
                  Arrays.deepToString(geofenceRequestIds), Toast.LENGTH_SHORT)
                  .show();
            } else {
              // If adding the geofences failed
            }
          }
        });
  }

}