package com.queenievatcha.otoro1;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

       // mMap.setMyLocationEnabled(true);

        LatLng siamDis = new LatLng(13.7487581, 100.5237954);
        mMap.addMarker(new MarkerOptions().position(siamDis).title("Siam Discovery").snippet("      4th floor"));

        LatLng pattaya = new LatLng(12.934668, 100.8812342);
        mMap.addMarker(new MarkerOptions().position(pattaya).title("Central Festival Pattaya").snippet("         3rd floor"));

        LatLng samyan = new LatLng(13.7434877, 100.5233661);
        mMap.addMarker(new MarkerOptions().position(samyan).title("SuanLuang Square").snippet("     Chula soi 6"));

        CameraPosition bangkok = CameraPosition.builder().target(new LatLng(13.7245599, 100.4926818)).zoom(8).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(bangkok));
    }

}