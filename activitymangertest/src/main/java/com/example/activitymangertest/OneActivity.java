package com.example.activitymangertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OneActivity extends AppCompatActivity {
    private static final String TAG = "OneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        Intent intent = new Intent(OneActivity.this,TwoActivity.class);
        startActivity(intent);
    }
}
