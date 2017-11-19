package com.queenievatcha.otoro1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void goCart(View v) {
        Intent in = new Intent(HomeActivity.this, CartActivity.class);
        startActivity(in);
    }

    public void goHistory(View v) {
        Intent in = new Intent(HomeActivity.this, HistoryActivity.class);
        startActivity(in);
    }

    public void goMenu(View v) {
        Intent in = new Intent(HomeActivity.this, MenuActivity.class);
        startActivity(in);
    }

    public void goMap(View v) {
        Intent in = new Intent(HomeActivity.this, MapActivity.class);
        startActivity(in);
    }

    public void popUp (View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        dialog.setTitle("Delivery Schedule");
        dialog.setMessage("eflgsjopg[ikgs'epgj'rgj;ho");
        dialog.show();
    }

}
