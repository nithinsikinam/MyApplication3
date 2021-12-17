package com.example.myapplication;

import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Joinchannel {
    private String email;
    private String channelid;
    private Context context;

    public Joinchannel(String email, String channelid,Context context) {
        this.email = email;
        this.channelid = channelid;
        this.context=context;
    }
public void joiner(){
    StringRequest jsonArrayRequest = new StringRequest(
            "http://27.6.130.5:12345/getid.php?Name="+email,  new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {


                String idUser= response;

                StringRequest stringRequest = new StringRequest("http://27.6.130.5:12345/Users/"+idUser+".json", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Log.d("what is it",response);
                        join json = gson.fromJson(response, join.class);



                        json.joined.add(channelid);
                        executeSendFeedbackForm(json,email);
                        Log.d("Data is here","cl");
                        Intent intent = new Intent(context,MainActivity.class);
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


    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("myapp", "Something went wrong?????");
            Log.d("LOGCAT", "" + error.getMessage());
        }
    });
    RequestQueue requestQueue;
    requestQueue = Volley.newRequestQueue(context);
    requestQueue.add(jsonArrayRequest);
}
    private void executeSendFeedbackForm(join Join,String string){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setLenient().create();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://27.6.130.5:12345/").addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        TaskService client = retrofit.create(TaskService.class);
        Call<join> call =  client.sendpost2(Join,string);
        call.enqueue(new Callback<join>(){


            @Override
            public void onResponse(Call<join> call, retrofit2.Response<join> response) {
                Log.d("Done","doneee");
            }

            @Override
            public void onFailure(Call<join> call,Throwable t){

            }

        });
    }

}
