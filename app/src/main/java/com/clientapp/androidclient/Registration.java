package com.clientapp.androidclient;

import android.content.Intent;
import android.os.StrictMode;
import android.provider.Settings;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import okhttp3.*;
import android.annotation.SuppressLint;

import java.util.UUID;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if (android.os.Build.VERSION.SDK_INT > 22) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setTitle("Criar Conta");

        TextView email = findViewById(R.id.input_email);
        TextView password = findViewById(R.id.input_passwd);
        TextView cpf = findViewById(R.id.input_cpf);
        TextView birthday = findViewById(R.id.input_birth);

        Button btn_registration = findViewById(R.id.btn_reg_send);

        btn_registration.setOnClickListener(view -> {
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();
            String cpfInput = cpf.getText().toString();
            String birthdayInput = birthday.getText().toString();

            String guidAndroid = getAndroidGuid();
            String androidId = getAndroidID();

            postForm(emailInput, passwordInput, cpfInput, birthdayInput, guidAndroid, androidId);
        });
    }

    public void tokenScreen() {
        Intent in = new Intent(Registration.this, TokenReceiver.class);
        startActivity(in);
    }

    public void postForm(String email, String password, String cpf, String birthday, String androidGuid, String androidId) {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("cpf", cpf)
                .add("birthday", birthday)
                .add("guid", androidGuid)
                .add("androidId", androidId)
                .build();

        Request request = new Request.Builder()
                .url("https://192.168.45.156:8000/server/register") //10.0.0.142
                .method("POST", body)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.code() != 200) {
                Toast.makeText(getApplicationContext(), "Verifique se as informações estão corretas.", Toast.LENGTH_LONG).show();

            } else {
                tokenScreen();
            }

            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Android ID
    public String getAndroidID() {
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return androidId;
    }

    // Código personalizado globalmente exclusivo (GUID)
    public String getAndroidGuid() {
        return UUID.randomUUID().toString();
    }

}