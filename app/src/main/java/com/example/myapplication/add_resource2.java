package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class add_resource2 extends AppCompatActivity {
private Button button;
private Button button1;
private String channel;
private String chapter;
private String topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resource2);
        button=findViewById(R.id.addUrl);
        button1=findViewById(R.id.addFile);
        channel=getIntent().getStringExtra("channel");
        chapter=getIntent().getStringExtra("chapter");
        topic=getIntent().getStringExtra("topic");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_resource2.this,AddFile.class);
                intent.putExtra("channel",channel);
                intent.putExtra("chapter",chapter);
                intent.putExtra("topic",topic);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_resource2.this,AddUrl.class);
                intent.putExtra("channel",channel);
                intent.putExtra("chapter",chapter);
                intent.putExtra("topic",topic);
                startActivity(intent);
            }
        });

    }
}