package com.clientapp.androidclient;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_login = findViewById(R.id.init_btn_login);
        Button btn_registration = findViewById(R.id.init_btn_registration);

        btn_login.setOnClickListener(view -> loginScreen());
        btn_registration.setOnClickListener(view -> registrationScreen());
    }

    public void loginScreen() {
        Intent in = new Intent(MainActivity.this, Login.class);
        startActivity(in);
    }

    public void registrationScreen() {
        Intent in = new Intent(MainActivity.this, Registration.class);
        startActivity(in);
    }
}