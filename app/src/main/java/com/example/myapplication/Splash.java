package com.example.myapplication;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);


        String s1 = sh.getString("Logged", "");
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    if(s1.equals("true")){
                        Intent intent = new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Splash.this,HomeScreen.class);
                        startActivity(intent);
                    }
                }
            }
        };thread.start();









    }


}