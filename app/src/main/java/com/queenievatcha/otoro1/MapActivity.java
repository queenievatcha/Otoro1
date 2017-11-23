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
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng siamDis = new LatLng(13.7487581, 100.5237954);
        mMap.addMarker(new MarkerOptions().position(siamDis).title("Siam Discovery").snippet("4th floor"));
        LatLng pattaya = new LatLng(12.934668, 100.8812342);
        mMap.addMarker(new MarkerOptions().position(pattaya).title("Central Festival Pattaya").snippet("3rd floor"));
        LatLng samyan = new LatLng(13.7434877,100.5233661);
        mMap.addMarker(new MarkerOptions().position(samyan).title("SuanLuang Square").snippet("Chula soi 6"));
        LatLng silomcom = new LatLng(13.7238316,100.5124011);
        mMap.addMarker(new MarkerOptions().position(silomcom).title("Silom Complex"));
        LatLng ctw = new LatLng(13.7239138,100.5124011);
        mMap.addMarker(new MarkerOptions().position(ctw).title("Central World").snippet("Chula soi 6"));
        LatLng emqua = new LatLng(13.7319755,100.5674966);
        mMap.addMarker(new MarkerOptions().position(emqua).title("Emquatier").snippet("Chula soi 6"));
        LatLng megab = new LatLng(13.6486135,100.6776184);
        mMap.addMarker(new MarkerOptions().position(megab).title("Mega Bangna").snippet("Chula soi 6"));
        LatLng esplanade = new LatLng(13.7661459,100.5673483);
        mMap.addMarker(new MarkerOptions().position(esplanade).title("Esplanade").snippet("Chula soi 6"));
        LatLng thonglorten = new LatLng(13.7314571,100.580487);
        mMap.addMarker(new MarkerOptions().position(thonglorten).title("Thonglor Soi 10").snippet("Chula soi 6"));
        LatLng centrallp = new LatLng(13.8164066,100.5586294);
        mMap.addMarker(new MarkerOptions().position(centrallp).title("Central Ladprao").snippet("Chula soi 6"));
        LatLng centralwg = new LatLng(13.876368,100.4088379);
        mMap.addMarker(new MarkerOptions().position(centralwg).title("Central Westgate").snippet("Chula soi 6"));
        LatLng centralev= new LatLng(13.8036168,100.6119115);
        mMap.addMarker(new MarkerOptions().position(centralev).title("Central Festival Eastville").snippet("Chula soi 6"));
        LatLng chaophraya = new LatLng(13.7741903,100.5008947);
        mMap.addMarker(new MarkerOptions().position(chaophraya).title("Sri-Ayutthaya Road").snippet("Chula soi 6"));

        CameraPosition bangkok = CameraPosition.builder().target(new LatLng(13.7245599, 100.4926818)).zoom(9).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(bangkok));
    }
}