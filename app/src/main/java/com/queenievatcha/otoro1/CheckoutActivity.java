package com.queenievatcha.otoro1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckoutActivity extends AppCompatActivity {

    Button buttBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setTitle("ORDER COMPLETE");
        buttBack = (Button) findViewById(R.id.buttBack);
    }


    public void goHome(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(CheckoutActivity.this);
        dialog.setTitle("Thank you");
        dialog.setMessage("Bye bye").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent in = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(in);
            }
        });
        dialog.show();

    }

}
