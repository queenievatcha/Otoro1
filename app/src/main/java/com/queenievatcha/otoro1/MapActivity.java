package com.queenievatcha.otoro1;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.maps.model.Marker;

import android.content.Context;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker mMarker;
    LocationManager lm;
    double lat, lng;

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

        LatLng siamDis = new LatLng(13.7487581, 100.5237954);
        mMap.addMarker(new MarkerOptions().position(siamDis).title("Siam Discovery").snippet("4th Floor"));
        LatLng pattaya = new LatLng(12.934668, 100.8812342);
        mMap.addMarker(new MarkerOptions().position(pattaya).title("Central Festival Pattaya").snippet("3rd Floor"));
        LatLng samyan = new LatLng(13.7434877, 100.5233661);
        mMap.addMarker(new MarkerOptions().position(samyan).title("SuanLuang Square").snippet("Chula soi 6"));
        LatLng silomcom = new LatLng(13.7238316, 100.5124011);
        mMap.addMarker(new MarkerOptions().position(silomcom).title("Silom Complex"));
        LatLng ctw = new LatLng(13.7239138, 100.5124011);
        mMap.addMarker(new MarkerOptions().position(ctw).title("Central World").snippet("4th Floor"));
        LatLng emqua = new LatLng(13.7319755, 100.5674966);
        mMap.addMarker(new MarkerOptions().position(emqua).title("Emquartier").snippet("G Floor"));
        LatLng megab = new LatLng(13.6486135, 100.6776184);
        mMap.addMarker(new MarkerOptions().position(megab).title("Mega Bangna").snippet("2nd Floor"));
        LatLng esplanade = new LatLng(13.7661459, 100.5673483);
        mMap.addMarker(new MarkerOptions().position(esplanade).title("Esplanade").snippet("3rd Floor"));
        LatLng thonglorten = new LatLng(13.7314571, 100.580487);
        mMap.addMarker(new MarkerOptions().position(thonglorten).title("Thonglor Soi 10"));
        LatLng centrallp = new LatLng(13.8164066, 100.5586294);
        mMap.addMarker(new MarkerOptions().position(centrallp).title("Central Ladprao").snippet("4th Floor"));
        LatLng centralwg = new LatLng(13.876368, 100.4088379);
        mMap.addMarker(new MarkerOptions().position(centralwg).title("Central Westgate").snippet("2nd Floor"));
        LatLng centralev = new LatLng(13.8036168, 100.6119115);
        mMap.addMarker(new MarkerOptions().position(centralev).title("Central Festival Eastville").snippet("2nd Floor"));
        LatLng chaophraya = new LatLng(13.7741903, 100.5008947);
        mMap.addMarker(new MarkerOptions().position(chaophraya).title("Sri-Ayutthaya Rd."));

       /* CameraPosition current = CameraPosition.builder().target(new LatLng(lat, lng)).zoom(9).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(current));*/
    }

    LocationListener listener = new LocationListener() {
        public void onLocationChanged(Location loc) {
            LatLng coordinate = new LatLng(loc.getLatitude()
                    , loc.getLongitude());
            lat = loc.getLatitude();
            lng = loc.getLongitude();

            if (mMarker != null)
                mMarker.remove();


            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    coordinate, 11));
        }

        public void onStatusChanged(String provider, int status
                , Bundle extras) {
        }
        public void onProviderEnabled(String provider) {
        }
        public void onProviderDisabled(String provider) {
        }
    };

    public void onResume() {
        super.onResume();

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isNetwork =
                lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPS =
                lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isNetwork) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER
                    , 5000, 10, listener);
            Location loc = lm.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER);
            if(loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
        }

        if(isGPS) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER
                    , 5000, 10, listener);
            Location loc = lm.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);

            if(loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
        }
    }
}