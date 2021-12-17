package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddUrl extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText textView;


    private String channel;
    private String chapter;
    private String topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url);
        channel=getIntent().getStringExtra("channel");
        chapter=getIntent().getStringExtra("chapter");
        topic=getIntent().getStringExtra("topic");


        radioGroup = findViewById(R.id.radioGroup);
        textView = findViewById(R.id.editTextTextPersonName2);

        Button buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String type  = radioButton.getText().toString();

                StringRequest stringRequest = new StringRequest("http://27.6.130.5:12345/Channels/" + channel + "/Main.json", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        nson json = gson.fromJson(response, nson.class);
                        // List<Topic> topics = new ArrayList<>();

                        //Course course = new Course(edittext.getText().toString(), topics);
                        int j = json.data.course.size();
                        int l = 0 ;
                        int p = 0;
                        for (int k = 0 ; k<j;k++){
                            if (json.data.course.get(k).chapter.equals(chapter)){
                                l = k;
                                int h = json.data.course.get(k).topics.size();
                                for (int t = 0 ; t<h;t++){
                                    if (json.data.course.get(k).topics.get(t).topic.equals(topic)){
                                        p = t;

                                    }

                                }
                            }

                        }
                        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


                        String s1 = sh.getString("name", "");
                        Resource obj = new Resource(s1,textView.getText().toString(),type);
                        json.data.course.get(l).topics.get(p).resources.add(obj);



                        executeSendFeedbackForm(json,channel);
                        Log.d("Data is here","cl");
                        Intent intent = new Intent(AddUrl.this,MainActivity.class);
                        intent.putExtra("id",channel);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("not donee","not don");
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(AddUrl.this);
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

    public void checkButton1(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

    }
}