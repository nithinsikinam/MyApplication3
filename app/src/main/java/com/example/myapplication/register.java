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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class register extends AppCompatActivity {
Button button ;
EditText name;
EditText email;
EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
name = findViewById(R.id.Name);
email = findViewById(R.id.Email);
pass = findViewById(R.id.Password);
button = findViewById(R.id.Register2);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d("test","clicked");
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(register.this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://27.6.130.5:12345/register.php?name="+name.getText()+"&pass="+pass.getText()+"&email="+email.getText(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                myEdit.putString("name", email.getText().toString());
                myEdit.putString("pass",pass.getText().toString());
                myEdit.putString("Logged","true");

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                myEdit.commit();

                Intent intent = new Intent(register.this, MainActivity.class);
               startActivity(intent);

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