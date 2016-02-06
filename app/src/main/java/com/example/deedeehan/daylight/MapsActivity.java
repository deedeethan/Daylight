package com.example.deedeehan.daylight;

import android.app.DialogFragment;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements LocationProvider.LocationCallback,
        GoogleMap.OnMapLongClickListener,
LocationDialogFragment.LocationDialogListener{

    public static final String TAG = MapsActivity.class.getSimpleName();

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private LocationProvider mLocationProvider;

    private LatLng mostRecentLocation;
    private String type;
    private String comment;
    private int numStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mLocationProvider = new LocationProvider(this, this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mLocationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location));
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //createNewComment(latLng);

        LocationDialogFragment dialog = new LocationDialogFragment();
        dialog.show(getFragmentManager(), "create_comment_dialog");

        mostRecentLocation = latLng;
    }

//    private void createNewComment(LatLng latLng) {
//        Intent intent = new Intent(this, CommentActivity.class);
//        intent.putExtra("latitude", latLng.latitude);
//        intent.putExtra("longitude", latLng.longitude);
//        startActivity(intent);
//    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // Set new marker, but only if the person clicked ok and not cancel
        MarkerOptions options = new MarkerOptions()
                .position(mostRecentLocation)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(options);

        doSMS newMessage = new doSMS();
        EditText mTypeText = (EditText)findViewById(R.id.mType);
        EditText mCommentText = (EditText)findViewById(R.id.comment);
        RatingBar mRatingBar = (RatingBar)findViewById(R.id.ratingBar);
        type = mTypeText.getText().toString();
        comment = mCommentText.getText().toString();
        numStars = mRatingBar.getNumStars();
        newMessage.compose(mostRecentLocation.latitude, mostRecentLocation.longitude, type, numStars, comment);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}