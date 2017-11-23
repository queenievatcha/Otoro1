package com.queenievatcha.otoro1;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Nithis on 23/11/2560.
 */

public class InfoActivity extends AppCompatActivity {

    TextView textInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        textInfo=findViewById(R.id.textView_Info);

    }
}
