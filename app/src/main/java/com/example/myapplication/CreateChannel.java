package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateChannel extends AppCompatActivity {
Button button ;
EditText editText;
String ChannelName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_channel);
button= findViewById(R.id.Next);
editText=findViewById(R.id.classNameInput);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
ChannelName=editText.getText().toString();
if(ChannelName.equals("")){

}
else{

}
                }


            });

    }
}