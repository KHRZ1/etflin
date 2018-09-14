package com.etflin.etflin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class Pop extends Activity {
    private TextView hapusPost;
    private ProgressBar progressBar;
    public static final String PREFS_NAME = "MyEtflin";
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupberanda);

        hapusPost = (TextView) findViewById(R.id.hapusPost);
        progressBar = (ProgressBar) findViewById(R.id.progressBar5);

        getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String myPost = settings.getString("myPost", "");
        String username = settings.getString("username", "");

        if (!myPost.equals(username)){
            hapusPost.setVisibility(View.INVISIBLE);
            hapusPost.setHeight(0);
        }

        hapusPost.setOnClickListener(hapuspos);
    }

    private View.OnClickListener hapuspos = new View.OnClickListener() {
        public void onClick(View v) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String berId = settings.getString("myPostID", "");

            perintah("https://www.etflin.com/hapuspos.php?idpos="+berId);
            progressBar.setVisibility(View.VISIBLE);
        }
    };

    public void perintah(String urlTarget) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getApplicationContext());
        String url = urlTarget;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(),
                                "Berhasil dihapus",
                                Toast.LENGTH_SHORT);


                        toast.show();
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
