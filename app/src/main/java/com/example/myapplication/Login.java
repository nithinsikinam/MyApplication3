package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
EditText name ;
EditText pass;
Button button ;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.name2);
        pass= findViewById(R.id.Password2);
        button = findViewById(R.id.Login1);
        textView = findViewById(R.id.chuparustom);
        Log.d("test","clicked");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Log.d("test","clicked");
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(Login.this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.1.13:12345/login2.php?Name="+name.getText()+"&pass="+pass.getText(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("value").equals("yes")){

// Storing data into SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                        myEdit.putString("name", name.getText().toString());
                        myEdit.putString("pass",pass.getText().toString());
                        myEdit.putString("Logged","true");

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myEdit.commit();
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        textView.setTextColor(Color.RED);

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
Log.d("test","erororroro");
            }
        });

        requestQueue.add(jsonObjectRequest);
            }
        });
    }
}