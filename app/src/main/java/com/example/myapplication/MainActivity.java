package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private ItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://27.6.130.5:12345/json/sample.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                 int Chapters= response.getJSONObject("data").getJSONArray("course").length();
                    mList = new ArrayList<>();
                    for(int x = 0 ;x<Chapters;x++){
    List<String> nestedList1 = new ArrayList<>();
    int topics= response.getJSONObject("data").getJSONArray("course").getJSONObject(Chapters).getJSONArray("topics").length();
for (int y = 0;y<topics;y++){
    nestedList1.add(response.getJSONObject("data").getJSONArray("course").getJSONObject(Chapters).getJSONArray("topics").getJSONObject(1).getString("topic"));
}
mList.add(new DataModel(nestedList1,response.getJSONObject("data").getJSONArray("course").getJSONObject(1).getString("chapter")));
}
                    adapter = new ItemAdapter(mList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp", "Something went wrong");
                Log.d("myapp", error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }



}

