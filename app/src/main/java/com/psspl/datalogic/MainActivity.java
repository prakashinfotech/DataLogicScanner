package com.psspl.datalogic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by haresh
 * on 24-11-2018.
 */

public class MainActivity extends AppCompatActivity {
    Button buttonDecode,DecodeConfigSampleAPI,DecodeSampleAPI;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDecode=findViewById(R.id.buttonDecode);
        DecodeConfigSampleAPI=findViewById(R.id.DecodeConfigSampleAPI);
        buttonDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(MainActivity.this,DecodeListenerDemo.class);
                startActivity(mIntent);
            }
        });
        DecodeConfigSampleAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(MainActivity.this,DecodeConfigSampleAPI.class);
                startActivity(mIntent);
            }
        });

        DecodeSampleAPI=findViewById(R.id.DecodeSampleAPI);

        DecodeSampleAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent=new Intent(MainActivity.this,DecodeSampleAPI.class);
                startActivity(mIntent);
            }
        });

    }
}
