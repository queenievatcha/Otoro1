package com.queenievatcha.otoro1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class CartActivity extends AppCompatActivity {

    ListView menuList;
    TextView vatText, totalText, subTotalText;
    double price;
    static ArrayList<Integer> amountListFinal = new ArrayList<Integer>();
    static ArrayList<String> foodListFinal = new ArrayList<String>();
    static ArrayList<Integer> imgIDFinal = new ArrayList<Integer>();
    static ArrayList<String> buttPlus = new ArrayList<String>();
    static ArrayList<String> buttMinus = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Your Cart");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // create amount list
        int[] amountList = getIntent().getIntArrayExtra("list");

        //create food list
        String[] foodList = getIntent().getStringArrayExtra("nameList");

        //create image list
        int[] imgID = getIntent().getIntArrayExtra("imgID");

        for (int i = 0; i < amountList.length; i++) {
            if (amountList[i] != 0) {
                amountListFinal.add(amountList[i]);
                foodListFinal.add(foodList[i]);
                imgIDFinal.add(imgID[i]);
                buttPlus.add("+");
                buttMinus.add("-");

            }
        }

        subTotalText = (TextView) findViewById(R.id.subTotalText);
        vatText = (TextView) findViewById(R.id.vatText);
        totalText = (TextView) findViewById(R.id.totalText);
        DecimalFormat df = new DecimalFormat("#.##");

        //Create List

        // show price
        subTotalText.setText("990");

        // Get Price
        price = Double.parseDouble(subTotalText.getText().toString());
        vatText.setText(df.format(getVAT()));
        totalText.setText(getTotalPrice());


        //ListView
        ListAdapter myAdapter = new CustomAdapterCart(CartActivity.this, foodListFinal, imgIDFinal, buttPlus, buttMinus, amountListFinal);
        menuList = (ListView) findViewById(R.id.menuList);
        menuList.setAdapter(myAdapter);

    }


    public String getTotalPrice() {
        return (price + getVAT()) + "";
    }

    public double getVAT() {
        return 0.07 * price;
    }

    public void goNext(View v) {
        Intent intent = new Intent(this, Cart2Activity.class);
        intent.putExtra("totalPrice", getTotalPrice());
        startActivity(intent);
    }


    /*
    public static void addAmount(int position) {
        int newAmount = amountListFinal.get(position)+1;
        amountListFinal.set(position, newAmount);
    }

    public static void minusAmount(int position) {
        int newAmount = amountListFinal.get(position)-1;
        amountListFinal.set(position, newAmount);
    }
    */


}
