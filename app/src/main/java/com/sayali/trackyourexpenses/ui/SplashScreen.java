package com.sayali.trackyourexpenses.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sayali.trackyourexpenses.R;
import com.sayali.trackyourexpenses.database.DBManager;
import com.sayali.trackyourexpenses.util.AppPreferences;

import java.io.IOException;

import static com.sayali.trackyourexpenses.util.Constants.IS_LOGIN;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            DBManager.initDatabase(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initSplash();
    }

    private void initSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppPreferences.getBoolean(getApplicationContext(), IS_LOGIN))
                    startActivity(new Intent(SplashScreen.this, DashboardScreen.class));
                else
                    startActivity(new Intent(SplashScreen.this, LoginScreen.class));
            }
        }, SPLASH_TIME_OUT);
    }
}
