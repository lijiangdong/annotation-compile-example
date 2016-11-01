package com.cache.plu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cache.plu.api.ViewInject;
import com.example.ContentView;
import com.example.Inject;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Inject(R.id.text_view)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInject.inject(this);
//        textView.setText("hello world");
    }
}
