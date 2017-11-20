package com.queenievatcha.otoro1;

/**
 * Created by inkz on 19/11/2017 AD.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

    Button buttCheckout;
    PayPalConfiguration payConfig;
    EditText name, address;
    Intent service;
    String clientID = "AdpOU4oQtBnhzwOKXxUXLNRdREk_AnWACHU0fJbiQjgsp26JDYAE-3F27w1Cre8JSzLAFjEQNjNNBDP0";
    int paypalRequestCode = 999;
    RadioButton cash, paypal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);
        setTitle("ADDRESS");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        buttCheckout = (Button) findViewById(R.id.buttComplete);
        cash = (RadioButton) findViewById(R.id.radioOnDelivery);
        paypal = (RadioButton) findViewById(R.id.radioPayPal);
        name = (EditText) findViewById(R.id.editText);
        address = (EditText) findViewById(R.id.editText2);
        payConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(clientID); // envi_production for real money
        service = new Intent(this, PayPalService.class);
        service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payConfig);
        startService(service); // paypal service, listen to calls to paypal app

        buttCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // all are blank
                if (name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Enter your name!!", Toast.LENGTH_SHORT).show();

                // only method selected
                if (name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && (paypal.isChecked() || cash.isChecked()))
                    Toast.makeText(Cart2Activity.this, "Enter your name!!", Toast.LENGTH_SHORT).show();

                // address entered
                if (name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Enter your name!!", Toast.LENGTH_SHORT).show();

                // address entered & method selected
                if (name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && (paypal.isChecked() || cash.isChecked()))
                    Toast.makeText(Cart2Activity.this, "Enter your name!!", Toast.LENGTH_SHORT).show();

                // name entered
                if (!name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Enter your address!!", Toast.LENGTH_SHORT).show();

                // name entered & method selected
                if (!name.getText().toString().trim().equals("") && address.getText().toString().trim().equals("") && (paypal.isChecked() || cash.isChecked()))
                    Toast.makeText(Cart2Activity.this, "Enter your name!!", Toast.LENGTH_SHORT).show();

                // payment method not selected
                if (!name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && !paypal.isChecked() && !cash.isChecked())
                    Toast.makeText(Cart2Activity.this, "Please Select Payment Method!!", Toast.LENGTH_SHORT).show();

                // selected paypal
                if (!name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && paypal.isChecked())
                    pay(v);

                // selected cash
                if (!name.getText().toString().trim().equals("") && !address.getText().toString().trim().equals("") && cash.isChecked()) {
                    Intent in = new Intent(getApplicationContext(), CheckoutActivity.class);
                    startActivity(in);
                }

            }
        });

    }


    /**
     * For Paying with PayPal
     */
    public void pay(View view) {

        //Get totalPrice from Cart
        Bundle bundle = getIntent().getExtras();
        String amount = bundle.getString("totalPrice");

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
                        Intent in = new Intent(this, CheckoutActivity.class);
                        startActivity(in);
                        Toast.makeText(this, "Paid Successfully!!", Toast.LENGTH_LONG).show();
                        //payText.setText("Payment Success!!");
                    } else {
                        Intent in = new Intent(this, Cart2Activity.class);
                        startActivity(in);
                        Toast.makeText(this, "Payment Failed!!", Toast.LENGTH_LONG).show();
                        //payText.setText("Patment Failed!! ");
                    }
                } else {
                    Intent in = new Intent(this, Cart2Activity.class);
                    startActivity(in);
                    Toast.makeText(this, "Confirmation Failed!! (null)", Toast.LENGTH_LONG).show();
                    //payText.setText("Confirmation Failed!! (null) ");

                }
            }
        }
    }
}
