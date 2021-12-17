package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class resourcetab extends AppCompatActivity {
private TextView txt;
 private DataModel2 data =new DataModel2();
 private FloatingActionButton floatingActionButton;
 private String channel;
 private String chapter;
 private String topic;

    private RecyclerView recyclerView  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourcetab);
        floatingActionButton=findViewById(R.id.aResource);
        channel=getIntent().getStringExtra("channel");
        chapter=getIntent().getStringExtra("chapter");
        topic=getIntent().getStringExtra("topic");
floatingActionButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(resourcetab.this,add_resource2.class);
                                                intent.putExtra("channel",channel);
                                                intent.putExtra("chapter",chapter);
                                                intent.putExtra("topic",topic);
                                                startActivity(intent);
                                            }
                                        }
);
        recyclerView = findViewById(R.id.reource_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

 data =getIntent().getParcelableExtra("object");
       resourceadapter adapter = new resourceadapter(data,this,channel,chapter,topic);

       recyclerView.setAdapter(adapter);

    }


}