package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addchapter extends AppCompatActivity {
private EditText edittext;
private Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addchapter);
        edittext = findViewById(R.id.Addingchannel);
        button = findViewById(R.id.Addingchannelbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clickkk","clickyyy");
                String x = getIntent().getStringExtra("Channel");
                StringRequest stringRequest = new StringRequest("http://192.168.1.13:12345/Channels/" + x + "/Main.json", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        nson json = gson.fromJson(response, nson.class);
                        List<Topic> topics = new ArrayList<>();

                        Course course = new Course(edittext.getText().toString(), topics);
                        json.data.course.add(course);
                        executeSendFeedbackForm(json,x);
                        Log.d("Data is here","cl");
                        Intent intent = new Intent(addchapter.this,MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
Log.d("not donee","not don");
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(addchapter.this);
                queue.add(stringRequest);

            }
        });

    }
    private void executeSendFeedbackForm(nson Nson,String string){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://27.6.130.5:12345/").addConverterFactory(GsonConverterFactory.create());
Retrofit retrofit = builder.build();
    TaskService client = retrofit.create(TaskService.class);
        Call<nson> call =  client.sendpost(Nson,string);
        call.enqueue(new Callback<nson>(){


            @Override
            public void onResponse(Call<nson> call, retrofit2.Response<nson> response) {
                Log.d("Done","doneee");
            }

            @Override
            public void onFailure(Call<nson> call,Throwable t){

            }

        });
    }

    }
