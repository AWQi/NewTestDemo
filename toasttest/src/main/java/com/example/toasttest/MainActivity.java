package com.example.toasttest;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private TextView tv  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MyToast.getIntance(this,"不消失的Toast")
//                .setClickable()
//                .show();
        tv =  findViewById(R.id.tv);
//        View v = new View(this);
        Snackbar.make(tv,"Hello SnackBar!", Snackbar.LENGTH_INDEFINITE).show();
//    ClickToast.showToast(this, Toast.LENGTH_LONG);

    }
}
