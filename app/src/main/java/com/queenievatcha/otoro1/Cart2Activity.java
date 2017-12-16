package com.queenievatcha.otoro1;

/**
 * Created by inkz on 19/11/2017 AD.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class Cart2Activity extends AppCompatActivity {

    //FOR PASSING DATA
    int[] amount;
    String[] nameList;
    int[] priceForEach;
    String price;
    static String payment;

    Button buttCheckout;
    PayPalConfiguration payConfig;
    EditText name, address, phone;
    Intent service;
    String clientID = "AdpOU4oQtBnhzwOKXxUXLNRdREk_AnWACHU0fJbiQjgsp26JDYAE-3F27w1Cre8JSzLAFjEQNjNNBDP0";
    int paypalRequestCode = 999;
    RadioButton cash, paypal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        setTitle("BILLING ADDRESS");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //setting up a confirm dialog box
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);

        // JUST FOR PASSING DATA
        amount = getIntent().getIntArrayExtra("amount");
        nameList = getIntent().getStringArrayExtra("nameList");
        priceForEach = getIntent().getIntArrayExtra("priceForEach");
        price = CartActivity.realTotalPrice;


        buttCheckout = (Button) findViewById(R.id.buttComplete);
        cash = (RadioButton) findViewById(R.id.radioOnDelivery);
        paypal = (RadioButton) findViewById(R.id.radioPayPal);
        name = (EditText) findViewById(R.id.editText);
        address = (EditText) findViewById(R.id.editText2);
        phone = findViewById(R.id.phoneEditText);
        payConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(clientID); // envi_production for real money
        service = new Intent(this, PayPalService.class);
        service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payConfig);
        startService(service); // paypal service, listen to calls to paypal app

        buttCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paypal.isChecked()) {
                    payment = "PayPal";
                } else if (cash.isChecked()) {
                    payment = "Cash";
                }

                // all are blank
                if (name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();

                    // only method selected
                else if (name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && (paypal.isChecked() || cash.isChecked()))
                    Toast.makeText(Cart2Activity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();

                    // address entered
                else if (name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();

                    // address entered & method selected
                else if (name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && (paypal.isChecked() || cash.isChecked()))
                    Toast.makeText(Cart2Activity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();

                    // name entered
                else if (!name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Please enter your address.", Toast.LENGTH_SHORT).show();

                    // name entered & method selected
                else if (!name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && (paypal.isChecked() || cash.isChecked()))
                    Toast.makeText(Cart2Activity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();

                    // payment method not selected
                else if (!name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Please Select Payment Method!!", Toast.LENGTH_SHORT).show();

                    //address is just scrambled words example: dsfkjhgas
                else if (!address.getText().toString().contains(" ")) {
                    Toast.makeText(Cart2Activity.this, "Address is invalid", Toast.LENGTH_SHORT).show();
                }

                //phone number invalid
                else if (!phone.getText().toString().contains("0") || phone.getText().toString().isEmpty() || (phone.getText().toString().length() != 10)) {
                    Toast.makeText(Cart2Activity.this, "Phone number is invalid", Toast.LENGTH_SHORT).show();
                }

                //name contains number
                else if(!name.getText().toString().trim().matches("^[ A-Za-z]+$")){
                    Toast.makeText(Cart2Activity.this, "Name must be only a-z, or A-Z.", Toast.LENGTH_SHORT).show();
                }

                // selected paypal
                else if (paypal.isChecked())
                    pay(v);

                    // selected cash
                else if (cash.isChecked()) {

                    //adb.setTitle("Confirm Order?");
                    adb.setMessage("Confirm Your Order?");
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int arg1) {
                            goCheckout();
                        }
                    });
                    adb.show();
                }

            }
        });
    }

    /**
     * For Paying with PayPal
     */
    public void pay(View view) {

        //Get totalPrice from Cart
        String amount = price;

        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "THB", "Otoro", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, paypalRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == paypalRequestCode) {
            if (resultCode == Activity.RESULT_OK) {

                // confirm that the payment works
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirm != null) {
                    String state = confirm.getProofOfPayment().getState();
                    if (state.equals("approved")) { // payment works
                        Toast.makeText(this, "Paid Successfully!!", Toast.LENGTH_LONG).show();
                        goCheckout();
                    } else {
                        Toast.makeText(this, "Payment Failed!!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Confirmation Failed!! (null)", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public void goCheckout() {
        Intent in = new Intent(getApplicationContext(), CheckoutActivity.class);
        in.putExtra("name", name.getText().toString());
        in.putExtra("address", address.getText().toString());
        in.putExtra("phone", phone.getText().toString());
        startActivity(in);
    }
}