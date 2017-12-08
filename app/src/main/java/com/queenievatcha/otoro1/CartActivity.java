package com.queenievatcha.otoro1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView listViewSummary;
    TextView vatText, totalText, subTotalText;
    double price;
    static ArrayList<Integer> amountListFinal = new ArrayList<Integer>();
    static ArrayList<String> foodListFinal = new ArrayList<String>();
    static ArrayList<Integer> imgIDFinal = new ArrayList<Integer>();
    static ArrayList<Integer> eachPriceFinal = new ArrayList<Integer>();
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Your Cart");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // create amount list
        int[] amountList = getIntent().getIntArrayExtra("amount");

        //create food list
        String[] foodList = getIntent().getStringArrayExtra("nameList");

        //create image list
        int[] imgID = getIntent().getIntArrayExtra("imgID");

        //create price for each list
        int[] eachPriceF = getIntent().getIntArrayExtra("priceForEach");

        //amountListFinal.clear();
        //foodListFinal.clear();
        //eachPriceFinal.clear();
        //foodListFinal.clear();
        //price = 0;

        // add components to array lists
        if(foodList != null) {
            for (int i = 0; i < amountList.length; i++) {
                if (amountList[i] != 0) {
                    if (!foodListFinal.contains(foodList[i]))
                        addList(amountList[i], foodList[i], imgID[i], eachPriceF[i]);
                } else if (amountList[i] == 0 && !foodListFinal.isEmpty()) {
                    if (foodListFinal.contains(foodList[i])) {
                        index = foodListFinal.indexOf(foodList[i]);
                        removeList(amountList[index], foodList[index], imgID[index], eachPriceF[index]);
                    }
                }
            }
        }

        subTotalText = (TextView) findViewById(R.id.subTotalText);
        vatText = (TextView) findViewById(R.id.vatText);
        totalText = (TextView) findViewById(R.id.totalText);


        // show priceForEach

        price = MenuActivity.totalPrice;
        // Get Price
        subTotalText.setText(MenuActivity.totalPrice+"");
        DecimalFormat df = new DecimalFormat("#.##");
        vatText.setText(df.format(getVAT()));
        totalText.setText(getTotalPrice());

        //ListView
        ListAdapter myAdapter = new CustomAdapterCart(CartActivity.this, foodListFinal, imgIDFinal,amountListFinal, eachPriceFinal);
        listViewSummary = (ListView) findViewById(R.id.listViewSummary);
        listViewSummary.setAdapter(myAdapter);
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
        intent.putExtra("amount", amountListFinal);
        intent.putExtra("nameList", foodListFinal);
        intent.putExtra("imgID", imgIDFinal);
        intent.putExtra("priceForEach", eachPriceFinal);
        startActivity(intent);
    }

    public static void addAmount(int position) {
        int newAmount = (amountListFinal.get(position)) + 1;
        amountListFinal.set(position, newAmount);
    }

    public void addList(int amount, String foodName, int imgID, int eachPrice) {
        amountListFinal.add(amount);
        foodListFinal.add(foodName);
        imgIDFinal.add(imgID);
        eachPriceFinal.add(eachPrice*amount);
    }

    public void removeList(int amount, String foodName, int imgID, int eachPrice) {
        amountListFinal.remove(amount);
        foodListFinal.remove(foodName);
        imgIDFinal.remove(imgID);
        eachPriceFinal.remove(eachPrice);

    }
}