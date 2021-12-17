package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinPage extends AppCompatActivity {
private EditText editText;
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_page);
        editText=findViewById(R.id.joinchannel);
        button=findViewById(R.id.Joiningchannelbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


                String s1 = sh.getString("name", "");
                Log.d("taaa",s1);
                Joinchannel joinchannel = new Joinchannel(s1,editText.getText().toString(),JoinPage.this);
                joinchannel.joiner();
            }
        });
    }
}