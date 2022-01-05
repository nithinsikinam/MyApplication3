package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFile extends AppCompatActivity {
    private String channel;
    private String chapter;
    private String topic;
    private Button btnSelect, btnUpload;
    private TextView textView;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText textView2;

    private  int REQ_PDF = 21;
    private  String encodedPDF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
        channel=getIntent().getStringExtra("channel");
        chapter=getIntent().getStringExtra("chapter");
        topic=getIntent().getStringExtra("topic");
        radioGroup = findViewById(R.id.radioGroup1);
        textView=findViewById(R.id.textView1);
        btnSelect=findViewById(R.id.btnSelect1);
        btnUpload=findViewById(R.id.button_submit1);
        textView2 = findViewById(R.id.file2);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, REQ_PDF);

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadDocument(channel);

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String type  = radioButton.getText().toString();

                StringRequest stringRequest = new StringRequest("http://27.6.130.5:12345/Channels/" + channel + "/Main.json", new com.android.volley.Response.Listener<String>() {
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
                        Resource obj = new Resource(s1,"http://27.6.130.5:12345/Channels/" + channel + "/"+s1+textView2.getText().toString()+"."+type,type,textView2.getText().toString());
                        json.data.course.get(l).topics.get(p).resources.add(obj);



                        executeSendFeedbackForm(json,channel);
                        Log.d("Data is here","cl");
                        Intent intent = new Intent(AddFile.this,MainActivity.class);
                        intent.putExtra("id",channel);
                        startActivity(intent);
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("not donee","not don");
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(AddFile.this);
                queue.add(stringRequest);

            }
        });

    }

    private void uploadDocument(String channel1) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        String s1 = sh.getString("name", "");

        Call<ResponsePOJO> call = RetrofitClient.getInstance().getAPI().uploadDocument(encodedPDF,s1+textView2.getText().toString(),radioButton.getText().toString(),channel1);
        call.enqueue(new Callback<ResponsePOJO>() {
            @Override
            public void onResponse(Call<ResponsePOJO> call, Response<ResponsePOJO> response) {
                Toast.makeText(AddFile.this, response.body().getRemarks(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsePOJO> call, Throwable t) {
                Toast.makeText(AddFile.this, "Network Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_PDF && resultCode == RESULT_OK && data != null){

            Uri path = data.getData();


            try {
                InputStream inputStream = AddFile.this.getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodedPDF = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);

                textView.setText("Document Selected");
                btnSelect.setText("Change Document");

                Toast.makeText(this, "Document Selected", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public  void checkButton1(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);


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