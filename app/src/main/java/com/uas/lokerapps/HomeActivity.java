package com.uas.lokerapps;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView jobRecyclerView;
    private JobAdapter jobAdapter;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        // Arahkan ke HomeActivity
                        Intent homeIntent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.action_setting:
                        // Arahkan ke SettingActivity
                        Intent settingIntent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(settingIntent);
                        return true;
                }

                return false;
            }
        });

        jobRecyclerView = findViewById(R.id.jobRecyclerView);

        // Mengatur layout manager untuk RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        jobRecyclerView.setLayoutManager(layoutManager);

        // Buat daftar pekerjaan dan tambahkan item dari JSON
        List<Job> jobList = createJobListFromJson();

        // Inisialisasi adapter dan atur ke RecyclerView
        jobAdapter = new JobAdapter(jobList);
        jobRecyclerView.setAdapter(jobAdapter);
    }

    private List<Job> createJobListFromJson() {
        List<Job> jobList = new ArrayList<>();

        try {
            // Membaca file JSON dari folder assets
            String json = loadJSONFromAsset("jobs.json");

            // Parsing data JSON menjadi objek
            JSONArray jsonArray = new JSONArray(json);

            // Iterasi melalui setiap objek dalam array JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Mendapatkan nilai dari setiap kunci JSON
                int logo = getResourceId(jsonObject.getString("logo"), "drawable");
                String position = jsonObject.getString("position");
                String company = jsonObject.getString("company");
                String description = jsonObject.getString("description");

                // Membuat objek Job dan menambahkannya ke daftar
                Job job = new Job(logo, position, company, description);
                jobList.add(job);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jobList;
    }

    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            // Membaca file JSON dari folder assets
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private int getResourceId(String resourceName, String resourceType) {
        return getResources().getIdentifier(resourceName, resourceType, getPackageName());
    }

    @Override
    public void onBackPressed() {
        // Menutup aplikasi dan memindahkan ke latar belakang
        moveTaskToBack(true);
    }

}

