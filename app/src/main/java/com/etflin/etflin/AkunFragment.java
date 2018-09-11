package com.etflin.etflin;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment {
    public static final String PREFS_NAME = "MyEtflin";
    private Context context;
    private TextView userNama, userAlamat, userEmail;
    private ImageView prop;


       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_akun, container, false);
           SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
           String user = settings.getString("username", "");

           prop = (ImageView) view.findViewById(R.id.picProfil);
           userNama = (TextView) view.findViewById(R.id.akunName);
           userAlamat = (TextView)  view.findViewById(R.id.akunAlamat);
           userEmail = (TextView) view.findViewById(R.id.akunEmail);

            perintah("https://www.etflin.com/akun.php?user="+user+"&tipe=akun");
        return view;
       }

    private void perintah (String urlTarget) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = urlTarget;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] result = response.split("xxx");
                        Glide.with(getActivity()).load(result[0]).into(prop);
                        userNama.setText(result[1]);
                        userAlamat.setText(result[4]);
                        userEmail.setText(result[2]);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getActivity(),
                        "Error",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
