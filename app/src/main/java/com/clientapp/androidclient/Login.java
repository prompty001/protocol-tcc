package com.clientapp.androidclient;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.*;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 22) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setTitle("Login");

        TextView emailLogin = findViewById(R.id.input_login_email);
        TextView passwordLogin = findViewById(R.id.input_login_password);

        Button btn_login_send = findViewById(R.id.btn_login_send);

        btn_login_send.setOnClickListener(view -> {
            String emailLoginInput = emailLogin.getText().toString();
            String passwordLoginInput = passwordLogin.getText().toString();

            System.out.println("email " + emailLoginInput);
            System.out.println(("senha " + passwordLoginInput));

            postFormLogin(emailLoginInput, passwordLoginInput);

        });
    }

    // Android ID
    public String getAndroidID() {
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return androidId;
    }

    public void postFormLogin(String email, String password) {
        OkHttpClient client = new OkHttpClient();
        String androidId = getAndroidID();

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .add("androidIdLogin", androidId)
                .build();

        Request request = new Request.Builder()
                .url("https://10.0.0.142:8000/server/login")
                .method("POST", body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                Toast.makeText(getApplicationContext(), "Login com sucesso!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Usuário ou Senha inválidos.", Toast.LENGTH_LONG).show();
            }
            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}