package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class resourcetab extends AppCompatActivity {
private TextView txt;
 private DataModel2 data =new DataModel2();

    private RecyclerView recyclerView  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourcetab);
        recyclerView = findViewById(R.id.reource_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

 data =getIntent().getParcelableExtra("object");
       resourceadapter adapter = new resourceadapter(data,this);

       recyclerView.setAdapter(adapter);

    }


}