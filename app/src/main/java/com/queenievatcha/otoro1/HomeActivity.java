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
import android.widget.RelativeLayout;

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
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        dialog.setTitle("Developers");
        dialog.setMessage("5931294521 Boonsita V." + '\n' +
                "5931291621 Nithiphorn Y." + '\n' +
                "5931292221 Nithis P." + '\n' +
                "5931315021 Pattabhum K.");
        dialog.show();
    }

    public void goMap(View v) {
        Intent in = new Intent(HomeActivity.this, MapActivity.class);
        startActivity(in);
    }

    public void popUp(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
        dialog.setTitle("Delivery Schedule");
        dialog.setMessage("We ship your delicious meal 2 hours after the order is placed.\n"+" \n"+
                "The first delivery is at 11:00am every day."+'\n'
                +"Please make your last order before 7:00pm");
        dialog.show();
    }

}
