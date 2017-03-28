package com.example.liz.myrailway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView trainDisplay;
    private String singleTrainString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        trainDisplay = (TextView) findViewById(R.id.display_single_train);

        Intent intentThatStartedThisActivity = getIntent();

        //获取从resultActivity获取的信息
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                singleTrainString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                trainDisplay.setText(singleTrainString);
            }
        }
    }
}
