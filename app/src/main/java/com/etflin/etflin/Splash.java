package com.etflin.etflin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Splash extends AppCompatActivity {
    private ProgressBar progressBar;

    public static final String PREFS_NAME = "MyEtflin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String email = settings.getString("email", "");
        String pass = settings.getString("pass", "");

        perintah("https://www.etflin.com/login.php?email="+email+"&user_pass="+pass);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void perintah(String urlTarget) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getApplicationContext());
        String url = urlTarget;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        String currentString = response;
                        String[] separated = currentString.split("/");
                        if(separated[0].equals("selamat")) {
                            Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(myIntent);
                        } else {
                            Intent myIntent = new Intent(getBaseContext(), Login.class);
                            startActivity(myIntent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplication().getApplicationContext(),
                        "Error",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
