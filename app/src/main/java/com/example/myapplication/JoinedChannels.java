package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinedChannels extends AppCompatActivity {
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_channels);

         recyclerView = findViewById(R.id.joinchannel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("name", "");


        StringRequest jsonObjectRequest = new StringRequest(
                "http://27.6.130.5:12345/getid.php?Name="+s1,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    String idUser= response;

                    StringRequest stringRequest = new StringRequest("http://27.6.130.5:12345/Users/"+idUser+".json", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            join json = gson.fromJson(response, join.class);



                           Joinadapter adapter = new Joinadapter(json,JoinedChannels.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("not donee","not don");
                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(JoinedChannels.this);
                    queue.add(stringRequest);






            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp", "Something went wrong");
                Log.d("LOGCAT", "" + error.getMessage());
            }
        });
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
}