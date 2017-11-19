package com.queenievatcha.otoro1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class CartActivity extends AppCompatActivity {

    Button buttPayPal;
    //EditText thb;
    PayPalConfiguration payConfig;
    Intent service;
    String clientID = "AdpOU4oQtBnhzwOKXxUXLNRdREk_AnWACHU0fJbiQjgsp26JDYAE-3F27w1Cre8JSzLAFjEQNjNNBDP0";
    int paypalRequestCode = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        buttPayPal = (Button) findViewById(R.id.buttPayPal);

        payConfig = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(clientID); // envi.production for real money
        service = new Intent(this, PayPalService.class);
        service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payConfig); // config above

        startService(service); // paypal service, listen to calls to paypal app
    }



    public void pay(View view) {

        String amount = 190+""; //thb.getText()+"";

        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "THB", "Pay for me m8 ty", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, paypalRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == paypalRequestCode) {

            if (resultCode == Activity.RESULT_OK){

                // confirm that the payment works
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirm != null){

                    String state = confirm.getProofOfPayment().getState();

                    if(state.equals("approved")) { // payment works
                        Toast.makeText(this, "Paid Successfully!!", Toast.LENGTH_LONG).show();
                        Intent in = new Intent(this, CheckoutActivity.class);
                        startActivity(in);
                        //payText.setText("Payment Success!!");
                    } else {
                        Toast.makeText(this, "Payment Failed!!", Toast.LENGTH_LONG).show();
                        //payText.setText("Patment Failed!! ");
                    }
                } else {
                    Toast.makeText(this, "Confirmation Failed!! (null)", Toast.LENGTH_LONG).show();
                    //payText.setText("Confirmation Failed!! (null) ");



                }
            }
        }
    }
}
