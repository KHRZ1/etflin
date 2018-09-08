package com.etflin.etflin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class Login extends AppCompatActivity {
    private EditText emailLg, passwordLg;
    private ProgressBar progressBar;
    public static final String PREFS_NAME = "MyEtflin";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailLg = (EditText) findViewById(R.id.emailLogin);
        passwordLg = (EditText) findViewById(R.id.passLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        textView = (TextView) findViewById(R.id.textView3);
    }

    public void login(View view) {
        String email = emailLg.getText().toString();
        String pass = passwordLg.getText().toString();
        String type = "login";

        Process process = new Process();
        process.execute(type, email, pass);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void register(View view) {
        Intent myIntent = new Intent(getBaseContext(), Daftar.class);
        startActivity(myIntent);
    }




    public class Process extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "https://www.etflin.com/login.php";
            if(type.equals("login")){
                try {
                    String email = params[1];
                    String pass = params[2];
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"+URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(pass, "UTF-8");

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
                editor.putString("email", emailLg.getText().toString());
                editor.putString("pass", passwordLg.getText().toString());
                editor.commit();

                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(myIntent);
            } else {
                textView.setText("Email dan Password salah!");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onPreExecute();
        }
    }
}
