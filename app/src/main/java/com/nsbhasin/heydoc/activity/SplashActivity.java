package com.nsbhasin.heydoc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nsbhasin.heydoc.R;
import com.nsbhasin.heydoc.adapter.ChatArrayAdapter;
import com.nsbhasin.heydoc.async.Welcome;
import com.nsbhasin.heydoc.database.entity.ChatMessage;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private Button mBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mBegin = findViewById(R.id.begin);
        mBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });
    }
}
