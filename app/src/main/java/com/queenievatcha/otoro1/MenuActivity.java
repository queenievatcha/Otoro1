package com.queenievatcha.otoro1;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
//lullklk
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String[] food = {"burger", "crab", "fish", "pizza", "shrimp"};
        String[] description = {"This is a burger", "This is a crab", "This is a fish", "This is a pizza", "This is a shrimp"};
        Integer[] imgID = {R.drawable.burger, R.drawable.crab, R.drawable.fish, R.drawable.pizza, R.drawable.shrimp};
        String[] butPlus = {"+", "+", "+", "+", "+"};
        String[] butMinus = {"-", "-", "-", "-", "-"};
        Integer[] amount = {0,0,0,0,0};

        ListAdapter myAdapter = new CustomAdapter(this, food, description, imgID, butPlus, butMinus, amount);
        ListView myListView = (ListView) findViewById(R.id.ListView);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String food = String.valueOf(adapterView.getItemAtPosition(i));
                Toast.makeText(MenuActivity.this, food, Toast.LENGTH_SHORT).show();
            }
        });
    }
}