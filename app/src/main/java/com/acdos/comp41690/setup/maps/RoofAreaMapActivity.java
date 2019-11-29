package com.acdos.comp41690.setup.maps;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity used to calculate the user's roof size
 *
 * Code based off https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
 * (Some comments are from that tutorial code; I didn't remove them because they help explain the
 * more complex features of the Google Maps API)
 */
public class RoofAreaMapActivity extends MapActivity {

    // This is the shape created by the user, outlining their roof
    Polygon polygon;
    // This contains all of the user's placed markers
    private List<Marker> markers = new ArrayList<>();

    /**
     * Method where we can manipulate the map once it has been created
     * This contains the logic of roof-area calculation
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        RoofAreaDialogFragment roofAreaDialogFragment = new RoofAreaDialogFragment();
        roofAreaDialogFragment.show(getSupportFragmentManager(), "roof_area_alert");

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            // adds a marker to the map when the map is clicked
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions createdMarker = new MarkerOptions().position(latLng).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                markers.add(mMap.addMarker(createdMarker));

                drawArea();
            }
        });

        // These methods update the polygon whenever a pin is dragged by the user
        // I was wary about updating the polygon every time the marker is dragged,
        // but I didn't notice any performance issues with this, so I added it in
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            int index = -1;

            @Override
            public void onMarkerDragStart(Marker marker) {
                index = markers.indexOf(marker);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                markers.add(index, marker);
                drawArea();
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                markers.add(index, marker);
                drawArea();

            }
        });

        // Removes a marker whenever clicked and redraws the polygon
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markers.remove(marker);
                marker.remove();

                if (markers.size() != 0) {
                    drawArea();
                }
                return true;
            }
        });
    }

    // Draws the polygon for the user
    private void drawArea() {
        // If there's already a polygon, this hides it from the map
        if (polygon != null) {
            polygon.setVisible(false);
        }

        List<LatLng> locations = new ArrayList<>();

        for (Marker marker : markers) {
            locations.add(marker.getPosition());
        }

        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .fillColor(Color.argb(128, 0, 0, 128))
                .addAll(locations));
    }

    // Resets the polygon from the map and the markers from memory
    public void resetSelection(View view) {
        mMap.clear();
        markers.clear();
    }

    // Calculates the size of the polygon, returns the value to the previous activity
    public void submitSelection(View view) {
        if (markers.size() >= 3) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("area", SphericalUtil.computeArea(polygon.getPoints()));
            setResult(1,returnIntent);
            finish();
        } else {
            Toast.makeText(this, "You must outline your roof with markers. Tap the map to add a marker.", Toast.LENGTH_LONG).show();
        }

    }
}
