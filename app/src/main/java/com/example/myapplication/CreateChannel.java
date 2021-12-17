package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class CreateChannel extends AppCompatActivity {
Button button ;
EditText editText;
String ChannelName="";
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
    Toast.makeText(getApplicationContext(),"Enter valid Channel Name ",Toast.LENGTH_SHORT).show();
}
else{
    StringRequest stringRequest = new StringRequest("http://27.6.130.5:12345/create.php?name="+ChannelName, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            String s1 = sh.getString("name", "");
            Joinchannel joinchannel = new Joinchannel(s1,response,CreateChannel.this);
            joinchannel.joiner();


            Log.d("Data is here","cl");
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("not donee","not don");
        }
    });

    RequestQueue queue = Volley.newRequestQueue(CreateChannel.this);
    queue.add(stringRequest);

}
                }


            });

    }
}