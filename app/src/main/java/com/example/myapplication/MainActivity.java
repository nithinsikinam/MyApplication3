package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.databinding.ActivityMainBinding;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;

import android.view.View;

import android.widget.Toast;




import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;


import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private List<DataModel> mList= new ArrayList<>();;
    private ItemAdapter adapter;
    private List<DataModel2> mList2;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final ImageView fabIconNaw = new ImageView(this);
        fabIconNaw.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_adjust_24));

        final FloatingActionButton topLeftButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNaw)
                .setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT)
                .build();

        SubActionButton.Builder tLSubBuilder = new SubActionButton.Builder(this);
        ImageView menuOption1 = new ImageView(this);
        ImageView menuOption2 = new ImageView(this);
        ImageView menuOption3 = new ImageView(this);


        menuOption1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_arrow_forward_ios_24));
        menuOption2.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_add_24));
        menuOption3.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_library_books_24));


        final FloatingActionMenu topLeftMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(tLSubBuilder.setContentView(menuOption1).build())
                .addSubActionView(tLSubBuilder.setContentView(menuOption2).build())
                .addSubActionView(tLSubBuilder.setContentView(menuOption3).build())

                .attachTo(topLeftButton)
                .build();

        topLeftMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu floatingActionMenu) {
                fabIconNaw.setRotation(180);
                PropertyValuesHolder pvhr = PropertyValuesHolder.ofFloat(View.ROTATION, 270);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNaw, pvhr);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu floatingActionMenu) {

                fabIconNaw.setRotation(90);
                PropertyValuesHolder pvhr = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNaw, pvhr);
                animation.start();

            }
        });

        menuOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Option1", Toast.LENGTH_SHORT).show();
            }
        });

        menuOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateChannel.class);
                startActivity(intent);


            }
        });
        menuOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Option 3", Toast.LENGTH_SHORT).show();
            }
        });







    recyclerView = findViewById(R.id.main_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://192.168.1.13:12345/json/sample.json", null, new Response.Listener<JSONObject>() {
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
                    adapter = new ItemAdapter(mList,MainActivity.this);
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

