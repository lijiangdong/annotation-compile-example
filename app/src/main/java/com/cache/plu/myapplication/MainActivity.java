package com.cache.plu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    @Inject(R.id.button1)
    Button button1;
    @Inject(R.id.button2)
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInject.inject(this);
        button1.setText("test1");
        button2.setText("test2");
        textView.setText("hello world");
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
