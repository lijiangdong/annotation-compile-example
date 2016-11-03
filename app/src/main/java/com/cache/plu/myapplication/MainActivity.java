package com.cache.plu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cache.plu.api.ViewInject;
import com.example.ContentView;
import com.example.Inject;
import com.example.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Inject(R.id.text_view)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInject.inject(this);
        textView.setText("hello world");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @OnClick({ R.id.button1,R.id.button2 })
    public void onClickButton(View view){
        switch (view.getId()){
            case R.id.button1:
                Toast.makeText(this,"one",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                Toast.makeText(this,"two",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
