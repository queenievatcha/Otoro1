package com.queenievatcha.otoro1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    static String[] food = {"burger", "crab", "fish", "pizza", "shrimp"};
    static String[] description = {"This is a burger", "This is a crab", "This is a fish", "This is a pizza", "This is a shrimp"};
    static int[] imgID = {R.drawable.burger, R.drawable.crab, R.drawable.fish, R.drawable.pizza, R.drawable.shrimp};
    static String[] butPlus = {"+", "+", "+", "+", "+"};
    static String[] butMinus = {"-", "-", "-", "-", "-"};
    static int[] amount = {0, 0, 0, 0, 0};
    static TextView shrimpText, burgerText, fishText, crabText, pizzaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setTitle("Menu");
        ListAdapter myAdapter = new CustomAdapter(this, food, description, imgID, butPlus, butMinus, amount);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(myAdapter);

        shrimpText = (TextView) findViewById(R.id.shrimpText);
        fishText = (TextView) findViewById(R.id.fishText);
        burgerText = (TextView) findViewById(R.id.burgerText);
        pizzaText = (TextView) findViewById(R.id.pizzaText);
        crabText = (TextView) findViewById(R.id.crabText);

        setAllText();
    }

    public static void setAllText() {
        burgerText.setText("Burger: " + amount[0]);
        crabText.setText("Crab: " + amount[1]);
        fishText.setText("Fish: " + amount[2]);
        pizzaText.setText("Pizza: " + amount[3]);
        shrimpText.setText("Shrimp: " + amount[4]);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void goToCart(MenuItem menuItem) {
        Intent in = new Intent(MenuActivity.this, CartActivity.class);
        in.putExtra("list", amount);
        in.putExtra("imgID", imgID);
        in.putExtra("nameList", food);
        startActivity(in);
    }

    public static void addAmount(int position) {
        amount[position] += 1;
    }

    public static void minusAmount(int position) {
        amount[position] -= 1;
    }
}
