package com.queenievatcha.otoro1;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


public class CheckoutActivity extends AppCompatActivity {

    ArrayList<Integer> amount, priceForEach;
    ArrayList<String> foodList;
    Button buttBack;
    static String name, price, phoneNum, address, date, payMethod;
    ImageView ivReceipt;
    String imagePath;
    Uri URI;

    //firebase database section
    DatabaseReference databaseOrder;
    ArrayList<Food> foodOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setTitle("ORDER COMPLETE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);

        // GET FOOD DATA
        // save these data to online database
        name = getIntent().getStringExtra("name"); // customer name
        address = getIntent().getStringExtra("address"); // customer address
        amount = CartActivity.amountListFinal;
        priceForEach = CartActivity.eachPriceFinal;
        foodList = CartActivity.foodListFinal;
        phoneNum = getIntent().getStringExtra("phone");
        price = CartActivity.realTotalPrice;

        date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        String VAT = CartActivity.vat;
        payMethod = Cart2Activity.payment;

        ButterKnife.bind(this);
        ivReceipt = findViewById(R.id.ivReceipt);
        buttBack = findViewById(R.id.buttBack);

        // BELOW THIS IS MADNESS


        // make address a multiple line

       /*
        int address1S = address.indexOf(" ", address.indexOf(" ") + 1);
        address1 = address.substring(0, address1S + 1);
        String address15 = address.substring(address1S);
        int address2S = address15.indexOf(" ", address15.indexOf(" ") + 1);
        address2 = address15.substring(1, address2S + 1);
        */

        Bitmap barcode = BitmapFactory.decodeResource(this.getResources(), R.drawable.barcode);

        ReceiptBuilder receipt = new ReceiptBuilder(1200);


        receipt.setMargin(10, 20).
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK).
                setTextSize(60).
                setTypeface(this, "fonts/RobotoMono-Regular.ttf").
                addText("Otoro Japanese Restaurant").
                addText("4th fl. Siam Discovery").
                addText("Bangkok, Thailand 10330").
                addText("02-658-1160").

                //

                        addBlankSpace(30).
                setAlign(Paint.Align.LEFT).
                addText(name, false).


                setAlign(Paint.Align.RIGHT).
                addText("Status: Delivery").


                setAlign(Paint.Align.LEFT).
                addLine().
                addText(date, false).


                setAlign(Paint.Align.RIGHT).
                addText("VAT INCLUDED").

                //

                        setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("Phone number: " + phoneNum).
                addText("Payment method: " + payMethod).

                //

                        addParagraph().
                setTypeface(this, "fonts/RobotoMono-Regular.ttf").
                addText("ORDER", false).


                setAlign(Paint.Align.RIGHT).
                addText("PRICE").

                //

                        setTypeface(this, "fonts/RobotoMono-Bold.ttf").
                addMenu().

                //

                        setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("VAT", false).


                setAlign(Paint.Align.RIGHT).
                addText(VAT + " ฿").
                addLine(180).


                setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("TOTAL", false).


                setAlign(Paint.Align.RIGHT).
                addText(price + " ฿").
                addLine(180).

                //

                        addParagraph().
                setAlign(Paint.Align.CENTER).
                setTypeface(this, "fonts/RobotoMono-Regular.ttf").
                addText("APPROVED").
                addText("THANK YOU FOR YOUR ORDER").
                addText("").
                addLine().
                addText("").
                addText("ENJOY YOUR MEAL :)");
        ivReceipt.setImageBitmap(receipt.build());

        autosave();


        //Firebase database section

        databaseOrder = FirebaseDatabase.getInstance().getReference("Orders");
        String id = databaseOrder.push().getKey();

        Map<String, Food> menu = new HashMap<>();
        int count = 1;
        for (int i = 0; i < foodList.size(); i++) {
                String c = ""+count;
                menu.put(c,new Food(foodList.get(i),amount.get(i)));
                count++;
        }
        Order order = new Order(id, date, name, address, phoneNum, payMethod, price, menu);
        databaseOrder.child(id).setValue(order);
        Toast.makeText(this, "Your order is sent.", Toast.LENGTH_LONG).show();

    }


    public void goHome(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(CheckoutActivity.this);
        dialog.setMessage("Thank you for purchasing.").setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent in = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(in);
            }
        });
        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CheckoutActivity.this, HomeActivity.class));
        finish();
    }


    public void saveReceipt(View v) {
        ivReceipt.buildDrawingCache();
        Bitmap bmap = ivReceipt.getDrawingCache();
        CapturePhotoUtils.insertImage(getContentResolver(), bmap, "Receipt", "This is Otoro receipt.");
        Toast.makeText(this, "Receipt is saved.", Toast.LENGTH_LONG).show();
    }

    public void autosave() {
        ivReceipt.setDrawingCacheEnabled(true);
        Bitmap bm = ivReceipt.getDrawingCache();
        CapturePhotoUtils.insertImage(getContentResolver(), bm, "Receipt(auto create)"
                , "This is Otoro receipt (auto create).");
    }

}

