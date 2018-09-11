package com.etflin.etflin;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


/**
 * A simple {@link Fragment} subclass.
 */
public class TulisFragment extends Fragment {
    public static final String PREFS_NAME = "MyEtflin";
    private Spinner spinner;
    private ProgressBar progressBar;
    private EditText isi;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tulis, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        isi = (EditText) view.findViewById(R.id.tulisMessage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.tulis, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        final Button button = (Button) view.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, getActivity().MODE_PRIVATE);
                final String user = settings.getString("username", "");

                String tipe = spinner.getSelectedItem().toString();
                String isipublish = isi.getText().toString();

                perintah("https://www.etflin.com/publish.php?user="+user+"&tipe="+tipe+"&isipublish="+isipublish);

                progressBar.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }


    public void perintah(String urlTarget) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = urlTarget;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(getContext(),
                                response,
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getContext(),
                        "Error",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
