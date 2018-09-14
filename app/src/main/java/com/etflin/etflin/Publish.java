package com.etflin.etflin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.text.emoji.widget.EmojiButton;
import android.support.text.emoji.widget.EmojiEditText;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringEscapeUtils;

public class Publish extends AppCompatActivity {
    private EmojiTextView text;
    private EmojiEditText edit;
    private Spinner spinner, spinner2;
    private ArrayAdapter<CharSequence> adapter, adapter2;
    public static final String PREFS_NAME = "MyEtflin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        EmojiCompat.Config config = new BundledEmojiCompatConfig(this) ;
        EmojiCompat.init(config);

        EmojiButton button = (EmojiButton) findViewById(R.id.publishKirim);
        text = (EmojiTextView) findViewById(R.id.textView8);
        edit = (EmojiEditText) findViewById(R.id.publishMessage);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.jenis_publish, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.bagikanke, R.layout.spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        button.setOnClickListener(owmay);
    }

    private View.OnClickListener owmay = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String isi = StringEscapeUtils.escapeJava(edit.getText().toString());
            String tipe = spinner.getSelectedItem().toString();

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String user = settings.getString("username", "");

            perintah("https://www.etflin.com/publish.php?user="+user+"&tipe="+tipe+"&isipublish="+isi);
        }
    };

    public void perintah(String urlTarget) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = urlTarget;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast toast = Toast.makeText(getApplication().getApplicationContext(),
                                spinner.getSelectedItem().toString() + " berhasil dipublish.",
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
