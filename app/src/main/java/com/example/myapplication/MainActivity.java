package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private List<DataModel> mList= new ArrayList<>();;
    private ItemAdapter adapter;
    private List<DataModel2> mList2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://27.6.130.5:12345/json/sample.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                 int Chapters= response.getJSONObject("data").getJSONArray("course").length();
                    Log.d("myapp",Integer.toString(Chapters) );

                    mList = new ArrayList<>();
                    for(int x = 0 ;x<Chapters;x++){
                        List<String> strings = new ArrayList<>();
    List<DataModel2> nestedList1 = new ArrayList<>();
    int topics= response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").length();

    for (int y = 0;y<topics;y++){
      DataModel2 unit =  new DataModel2();
      strings.add(response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").getJSONObject(y).getString("topic"));
      int resources=   response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").getJSONObject(y).getJSONArray("resources").length();
      List<String> URL = new ArrayList<>();
      List<String> type = new ArrayList<>();
      List<String> Giver = new ArrayList<>();
      for (int z = 0;z<resources;z++){
           URL.add(response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").getJSONObject(y).getJSONArray("resources").getJSONObject(z).getString("url"));
          type.add(response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").getJSONObject(y).getJSONArray("resources").getJSONObject(z).getString("type"));
          Giver.add(response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").getJSONObject(y).getJSONArray("resources").getJSONObject(z).getString("name"));
      }
  unit.add(URL,type,Giver);
      nestedList1.add(unit);

    }
mList.add(new DataModel(nestedList1,response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getString("chapter"),strings));
}
                    adapter = new ItemAdapter(mList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("LOGCAT", "error in logic");
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp", "Something went wrong");
                Log.d("LOGCAT", "" + error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }



}

