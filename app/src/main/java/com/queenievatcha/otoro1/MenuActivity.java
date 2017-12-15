package com.queenievatcha.otoro1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

public class MenuActivity extends AppCompatActivity {

    static String[] food = {"Takoyaki", "Tonkatsu Curry", "3 Pieces Tuna",
            "5 Pieces Sushi", "Hamburg Rice", "Sukiyaki", "Shoyu Ramen"};
    static String[] description = {"Grill octopus, cabbage and batter shaped in ball, top with mayonnaise and fish flakes",
            "Fried pork loins, veggie and Japanese curry serve on rice",
            "Maguro, Chutoro and Otoro sushi, serve with soy sauce and fresh wasabi",
            "2 Salmons, Ika, Ebi and Maguro, serve with soy sauce and fresh wasabi",
            "Ground beef steak with demiglaze sauce serve on rice",
            "Japanese hotpot serve with beef, veggies and noodles",
            "Traditional ramen with shasu pork, bamboo shoot, seaweed, scallions and fresh egg"};
    static int[] priceForEach = {125, 150, 165, 170, 155, 145, 140};
    static int[] imgID = {R.drawable.takoyaki, R.drawable.tonkatsucurry, R.drawable.threepiecestuna, R.drawable.fivepiecesushi,
            R.drawable.hamburg, R.drawable.sukiyakibeef, R.drawable.shoyuramen};
    static String[] butPlus = {"+", "+", "+", "+", "+", "+", "+"};
    static String[] butMinus = {"-", "-", "-", "-", "-", "-", "-"};
    static int[] amount = {0, 0, 0, 0, 0, 0, 0};
    static int[] totalPriceForEach = {0, 0, 0, 0, 0, 0, 0};
    static int totalOrder;
    static int totalPrice;
    static Button buttonCart;
    static String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTitle("Menu");
        ListAdapter myAdapter = new CustomAdapter(this, food, description, imgID, butPlus, butMinus, amount);
        ListView myListView = findViewById(R.id.listView);
        myListView.setAdapter(myAdapter);
        buttonCart = findViewById(R.id.buttonCart);

    }

    public void goToCart(View v) {
        totalOrder=0;
        for (int i = 0; i < amount.length; i++) {
            totalOrder += amount[i];
        }
        if (totalOrder != 0) {
            Intent in = new Intent(MenuActivity.this, CartActivity.class);
            startActivity(in);
        } else {
            Toast.makeText(this, "Please select your menu.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void addAmount(int position) {
        amount[position]++;
        totalPrice += priceForEach[position];

    }

    public static void minusAmount(int position) {
        amount[position]--;
        if (amount[position] < 1) amount[position] = 0;
        totalPrice -= priceForEach[position];
        if (totalPrice < 0) totalPrice = 0;
    }

    public static void addPrice(int position) {
        totalPriceForEach[position] += priceForEach[position];
    }


    public static void minusPrice(int position) {
        totalPriceForEach[position] -= priceForEach[position];
        if (totalPriceForEach[position] < 0) totalPriceForEach[position] = 0;
    }

}
