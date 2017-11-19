package com.queenievatcha.otoro1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class CartActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

    }

    public void goNext(View v){

        Intent in = new Intent(CartActivity.this, Cart2Activity.class);
        startActivity(in);

    }


}
