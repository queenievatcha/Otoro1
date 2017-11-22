package com.queenievatcha.otoro1;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckoutActivity extends AppCompatActivity {

    Button buttBack;
    static String name, address, address1, address2;
    ImageView ivReceipt;
    @BindView(R.id.btDraw)
    Button btDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setTitle("ORDER COMPLETE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        ivReceipt = findViewById(R.id.ivReceipt);
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");

        int address1S = address.indexOf(" ", address.indexOf(" ") + 1);
        address1 = address.substring(0, address1S + 1);
        String address15 = address.substring(address1S);
        int address2S = address15.indexOf(" ", address15.indexOf(" ") + 1);
        address2 = address15.substring(1, address2S + 1);

        buttBack = (Button) findViewById(R.id.buttBack);
    }

    @OnClick(R.id.btDraw)
    public void drawReceipt(View view) {
        Bitmap barcode = BitmapFactory.decodeResource(this.getResources(), R.drawable.barcode);
        ReceiptBuilder receipt = new ReceiptBuilder(1200);
        receipt.setMargin(30, 20).
                setAlign(Paint.Align.CENTER).
                setColor(Color.BLACK).
                setTextSize(60).
                setTypeface(this, "fonts/RobotoMono-Regular.ttf").
                addText("OTORO").
                addText("1234 Main St.").
                addText("Palo Alto, CA 94568").
                addText("999-999-9999").
                addBlankSpace(30).
                setAlign(Paint.Align.LEFT).
                addText(name + "", false).
                setAlign(Paint.Align.RIGHT).
                addText("1234").
                setAlign(Paint.Align.LEFT).
                addLine().
                addText("08/15/16", false).
                setAlign(Paint.Align.RIGHT).
                addText("SERVER #4").
                setAlign(Paint.Align.LEFT).
                addParagraph().
                addText(address1).
                addText(address2).
                //addText("ACCT #: *********1111").
                        addParagraph().
                setTypeface(this, "fonts/RobotoMono-Bold.ttf").
                addText("CREDIT SALE").
                addText("UID: 12345678", false).
                //setAlign(Paint.Align.RIGHT).
                //addText("REF #: 1234").
                        setTypeface(this, "fonts/RobotoMono-Regular.ttf").
                setAlign(Paint.Align.LEFT).
                addText("BATCH #: 091", false).
                setAlign(Paint.Align.RIGHT).
                addText("AUTH #: 0701C").
                setAlign(Paint.Align.LEFT).
                addParagraph().
                setTypeface(this, "fonts/RobotoMono-Bold.ttf").
                addText("AMOUNT", false).
                setAlign(Paint.Align.RIGHT).
                addText("$ 15.00").
                setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("TIP", false).
                setAlign(Paint.Align.RIGHT).
                addText("$        ").
                addLine(180).
                setAlign(Paint.Align.LEFT).
                addParagraph().
                addText("TOTAL", false).
                setAlign(Paint.Align.RIGHT).
                addText("$        ").
                addLine(180).
                addParagraph().
                setAlign(Paint.Align.CENTER).
                setTypeface(this, "fonts/RobotoMono-Regular.ttf").
                addText("APPROVED").
                addParagraph().
                addImage(barcode);
        ivReceipt.setImageBitmap(receipt.build());
    }

    public void goHome(View v) {
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
