package com.xujian.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button post, get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        post = (Button) findViewById(R.id.post);
        get = (Button) findViewById(R.id.get);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post:
                break;
            case R.id.get:
                break;
        }
    }
}
