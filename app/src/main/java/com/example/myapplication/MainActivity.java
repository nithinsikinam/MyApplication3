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
    private List<DataModel> mList;
    private ItemAdapter adapter;
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
                "https://api.npoint.io/bae1983ffd460af76281", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                 int Chapters= response.getJSONObject("data").getJSONArray("course").length();
                    Log.d("myapp",Integer.toString(Chapters) );

                    mList = new ArrayList<>();
                    for(int x = 0 ;x<Chapters;x++){
    List<String> nestedList1 = new ArrayList<>();
    int topics= response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").length();
  for (int y = 0;y<topics;y++){
    nestedList1.add(response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getJSONArray("topics").getJSONObject(y).getString("topic"));
}
mList.add(new DataModel(nestedList1,response.getJSONObject("data").getJSONArray("course").getJSONObject(x).getString("chapter")));
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

