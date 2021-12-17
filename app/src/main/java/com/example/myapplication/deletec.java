package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

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

public class deletec {
private String Chaptern;
private String Channeln;
private Context context;

    public deletec(String chaptern, String channeln,Context context) {
        Chaptern = chaptern;
        Channeln = channeln;
        this.context=context;
    }
public void delete(){
    Log.d("clickkk","clickyyy");

    StringRequest stringRequest = new StringRequest("http://192.168.1.13:12345/Channels/" + Channeln + "/Main.json", new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            nson json = gson.fromJson(response, nson.class);
            int j = json.data.course.size();
            int l = 0 ;
            for (int k = 0 ; k<j;k++){
                if (json.data.course.get(k).chapter.equals(Chaptern)){
                    l = k;
                    break;
                }

            }


            json.data.course.remove(l);
            executeSendFeedbackForm(json,Channeln);
            Log.d("Data is here","cl");
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("id",Channeln);
            context.startActivity(intent);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("not donee","not don");
        }
    });

    RequestQueue queue = Volley.newRequestQueue(context);
    queue.add(stringRequest);

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
