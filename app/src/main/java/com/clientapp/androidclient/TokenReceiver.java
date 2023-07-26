package com.clientapp.androidclient;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import okhttp3.*;

public class TokenReceiver extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_receiver);

        setTitle("Confirmação");

        TextView token = findViewById(R.id.input_token);

        Button btn_token_send = findViewById(R.id.btn_token_send);

        btn_token_send.setOnClickListener(view -> {
            String tokenInput = token.getText().toString();

            System.out.println(tokenInput);

            postTokenServer(tokenInput);
        });
    }

    public void postTokenServer(String token) {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("token", token)
                .build();

        Request getRequest = new Request.Builder()
                .url("https://10.0.0.142:8000/server/codetotp")
                .method("POST", body)
                .build();

        try {
            Response response = client.newCall(getRequest).execute();

            if (response.code() == 200) {
                Toast.makeText(getApplicationContext(), "Conta criada com sucesso. Efetue login.", Toast.LENGTH_LONG).show();
                mainScreen();

            } else {
                Toast.makeText(getApplicationContext(), "Código inválido.", Toast.LENGTH_LONG).show();

            }

            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainScreen() {
        Intent in = new Intent(TokenReceiver.this, MainActivity.class);
        startActivity(in);
    }
}