package com.etflin.etflin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class Daftar extends AppCompatActivity {
    private ProgressBar progressBar;
    public static final String PREFS_NAME = "MyEtflin";
    private EditText emailDf, passDf, namaDf;
    private TextView statusDf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);

        emailDf = (EditText) findViewById(R.id.emailDaftar);
        passDf = (EditText) findViewById(R.id.passDaftar);
        namaDf = (EditText)findViewById(R.id.namaDaftar);
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        statusDf = (TextView) findViewById(R.id.textView2);
    }

    public void login(View view) {
        Intent myIntent = new Intent(getBaseContext(), Login.class);
        startActivity(myIntent);
    }

    public void daftar(View view) {
        String email = emailDf.getText().toString();
        String pass = passDf.getText().toString();
        String nama = namaDf.getText().toString();
        String type = "login";

        Daftarin process = new Daftarin();
        process.execute(type, email, pass, nama);
        progressBar.setVisibility(View.VISIBLE);
    }

    public class Daftarin extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "https://www.etflin.com/daftar.php";
            if(type.equals("login")){
                try {
                    String email = params[1];
                    String pass = params[2];
                    String nama = params[3];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"
                            +URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass, "UTF-8")+"&"
                            +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(nama, "UTF-8");

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line;

                    while((line = bufferedReader.readLine()) != null){
                        result = line;
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPreExecute();
            progressBar.setVisibility(View.GONE);
            String currentString = result;
            String[] separated = currentString.split("/");
            if(separated[0].equals("selamat")) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                // Writing data to SharedPreferences
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("email", emailDf.getText().toString());
                editor.putString("pass", passDf.getText().toString());
                editor.commit();

                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);
            } else {
                statusDf.setText("Pendaftaran gagal, email telah digunakan!") ;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onPreExecute();
        }
    }
}
