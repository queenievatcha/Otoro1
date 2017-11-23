package com.queenievatcha.otoro1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void goMenu(View v) {
        Intent in = new Intent(HomeActivity.this, MenuActivity.class);
        startActivity(in);
    }

    public void goInfo(View v) {
        Intent in = new Intent(HomeActivity.this, InfoActivity.class);
        startActivity(in);
    }

    public void goMap(View v) {
        Intent in = new Intent(HomeActivity.this, MapActivity.class);
        startActivity(in);
    }

    public void popUp (View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        dialog.setTitle("Delivery Schedule");
        dialog.setMessage("We ship your delicious meal 2 hours after the order is placed. The first delivery is at 11:00am every day.");
        dialog.show();
    }

}
