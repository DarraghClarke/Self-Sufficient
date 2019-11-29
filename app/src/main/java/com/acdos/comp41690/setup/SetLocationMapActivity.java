package com.acdos.comp41690.setup;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.acdos.comp41690.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Activity used to let the user select their location
 * A standalone activity was selected instead of directly accessing the user's location, because
 * this provides the chance for the user to set their location without giving away their
 * location data from sensors. Some users prefer this method, as it only gives the app the bare
 * information that it needs.
 *
 * Code based off https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
 * (Some comments are from that tutorial code; I didn't remove them because they help explain the
 * more complex features of the Google Maps API)
 */
public class SetLocationMapActivity extends MapActivity {
    // This is the marker placed on the map by the user - this is their location
    private Marker placedMarker = null;

    /**
     * Method where we can manipulate the map once it has been created
     * This contains the logic of pin placement and movement
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            // adds a marker to the map and deletes any old marker when the map is clicked
            @Override
            public void onMapClick(LatLng latLng) {
                if (placedMarker != null) {
                    placedMarker.setVisible(false);
                    mMap.clear();
                }

                MarkerOptions createdMarker = new MarkerOptions().position(latLng).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                placedMarker = mMap.addMarker(createdMarker);
            }
        });

        // Changes the marker location when the marker is dragged
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {}

            @Override
            public void onMarkerDragEnd(Marker marker) {
                placedMarker = marker;
            }

            @Override
            public void onMarkerDrag(Marker marker) {}
        });

        // Removes a marker when tapped
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();
                placedMarker = null;

                return true;
            }
        });
    }

    // Removes the marker when the "reset" button is clicked
    public void resetSelection(View view) {
        placedMarker = null;
        mMap.clear();
    }

    // Submits the user's marker, returning the intent to the previous activity
    public void submitSelection(View view) {
        if (placedMarker != null) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constants.SharedPrefKeys.LONGITUDE, placedMarker.getPosition().longitude);
            returnIntent.putExtra(Constants.SharedPrefKeys.LATITUDE, placedMarker.getPosition().latitude);
            setResult(3, returnIntent);
            finish();
        } else {
            Toast.makeText(this, "You must place a marker to select your location!", Toast.LENGTH_LONG).show();
        }

    }
}
