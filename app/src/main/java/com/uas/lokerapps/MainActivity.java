package com.uas.lokerapps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mengecek status login menggunakan SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Pengguna telah login sebelumnya, buka HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // Menutup MainActivity agar tidak bisa kembali ke halaman login
        }

        usernameEditText = findViewById(R.id.et_username);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.b_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                try {
                    // Membaca file JSON dari assets
                    String json = loadJSONFromAsset("users.json");

                    // Mengonversi JSON menjadi objek
                    JSONObject jsonObject = new JSONObject(json);

                    // Mendapatkan array "users" dari JSON
                    JSONArray usersArray = jsonObject.getJSONArray("users");

                    // Memeriksa apakah ada entri dengan username dan password yang cocok
                    boolean validCredentials = false;
                    for (int i = 0; i < usersArray.length(); i++) {
                        JSONObject user = usersArray.getJSONObject(i);
                        String jsonUsername = user.getString("username");
                        String jsonPassword = user.getString("password");

                        if (username.equals(jsonUsername) && password.equals(jsonPassword)) {
                            validCredentials = true;
                            break;
                        }
                    }

                    if (validCredentials) {
                        // Autentikasi berhasil, menyimpan status login menggunakan SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", username); // Simpan username
                        editor.putString("password", password); // Simpan password
                        editor.putBoolean("isLoggedIn", true); // Simpan status login
                        editor.apply();

                        // Buka HomeActivity
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Membaca file JSON dari assets
    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
