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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class deleter {
    private String chname;
    private String chapter;
    private String topic;
    private String url;
    private String name;
    private Context context;


    public deleter(String chname, String chapter, String topic,Context context,String url,String name) {
        this.chname = chname;
        this.chapter = chapter;
        this.topic = topic;
        this.context=context;
        this.url=url;
        this.name=name;
    }
    public void delete(){
        StringRequest stringRequest = new StringRequest("http://27.6.130.5:12345/Channels/" + chname + "/Main.json", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                nson json = gson.fromJson(response, nson.class);
                int j = json.data.course.size();
                int l = 0 ;
                int p = 0;
                int s = 0;
                for (int k = 0 ; k<j;k++){
                    if (json.data.course.get(k).chapter.equals(chapter)){
                        l = k;
                        int h = json.data.course.get(k).topics.size();
                        for (int t = 0 ; t<h;t++){
                            if (json.data.course.get(k).topics.get(t).topic.equals(topic)){
                                p = t;
                                int q = json.data.course.get(k).topics.get(t).resources.size();
                                for (int u = 0;u<q;u++ ){
                                    if (json.data.course.get(k).topics.get(t).resources.get(u).name.equals(name) && json.data.course.get(k).topics.get(t).resources.get(u).url.equals(url) ){
                                        s=u;
                                    }
                                }

                            }

                        }
                    }

                }
                json.data.course.get(l).topics.get(p).resources.remove(s);


                executeSendFeedbackForm(json,chname);
                Log.d("Data is here","cl");
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("id",chname);
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
