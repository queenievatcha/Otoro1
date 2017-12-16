package com.queenievatcha.otoro1;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.util.Date;
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
    private static final int PERMISSION_REQUEST_CODE = 666;

    //firebase database section
    DatabaseReference databaseOrder;
    ArrayList<Food> foodOrder;

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                //Permission granted already
            } else {
                requestPermission();
            }
        } else {
            // Code for Below 23 API Oriented Device
            // No idea what to do here
        }

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
            String c = "" + count;
            menu.put(c, new Food(foodList.get(i), amount.get(i)));
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
        CapturePhotoUtils.insertImage(getContentResolver(), bmap, receiptName(), "This is Otoro receipt.");
        Toast.makeText(this, "Receipt is saved.", Toast.LENGTH_LONG).show();
    }

    private String receiptName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "OTORO_RECEIPT_" + timestamp + ".png";
    }

    public void autosave() {
        ivReceipt.setDrawingCacheEnabled(true);
        Bitmap bm = ivReceipt.getDrawingCache();
        CapturePhotoUtils.insertImage(getContentResolver(), bm, "Receipt(auto create)"
                , "This is Otoro receipt (auto create).");
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(CheckoutActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(CheckoutActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(CheckoutActivity.this, "External Storage permission is needed to save receipt.", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(CheckoutActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Storage Permission Granted.");
                } else {
                    Log.e("value", "Storage Permission Denied.");
                }
                break;
        }
    }

}

