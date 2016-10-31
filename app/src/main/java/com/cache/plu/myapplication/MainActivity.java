package com.cache.plu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cache.plu.api.ViewInject;
import com.example.Layout;

@Layout(id = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInject.inject(this);
    }
}
