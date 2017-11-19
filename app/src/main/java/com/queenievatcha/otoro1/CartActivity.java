package com.queenievatcha.otoro1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class CartActivity extends AppCompatActivity {

    ListView menuList;
    TextView subTotalText, vatText, totalText;
    double price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");
        subTotalText = (TextView) findViewById(R.id.subTotalText);
        vatText = (TextView) findViewById(R.id.vatText);
        totalText = (TextView) findViewById(R.id.totalText);

        DecimalFormat df = new DecimalFormat("#.##");


        //Create List
        menuList = (ListView) findViewById(R.id.menuList);
        String[] values = new String[6];
        for (int i = 0; i < values.length; i++) {
            values[i] = i + 1 + " Menu";
        }
        ListAdapter adt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        menuList.setAdapter(adt);


        // Get Price
        price = Double.parseDouble(subTotalText.getText().toString());
        vatText.setText(df.format(getVAT()));
        totalText.setText(getTotalPrice());



    }


    public String getTotalPrice() {
        return (price + getVAT()) + "";
    }

    public double getVAT() {
        return 0.07 * price;
    }


    public void goNext(View v) {
        Intent in = new Intent(CartActivity.this, Cart2Activity.class);
        startActivity(in);
    }


}
