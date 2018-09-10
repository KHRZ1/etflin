package com.etflin.etflin;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment{
    private ListView lvHomePage;
    private ProgressBar prg;

    List<RowItem> rowItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        String type = "normal";

        Berandahome task = new Berandahome();
        task.execute(type);

        lvHomePage = (ListView) view.findViewById(R.id.dinamiklist);
        prg = (ProgressBar) view.findViewById(R.id.loadingBrnd);
        prg.setVisibility(View.VISIBLE);
        return view;
    }

    public class Berandahome extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "https://www.etflin.com/beranda.php";
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type, "UTF-8");

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
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPreExecute();
            prg.setVisibility(View.GONE);
            String[] separated = result.split("xxx");
            String[] pic_url = separated[0].split(";");
            String[] nama_user = separated[1].split(";");
            String[] level = separated[2].split(";");
            String[] isi_status = separated[3].split(";");
            String[] jumlah_Suka = separated[4].split(";");
            String[] jumlah_Komen = separated[5].split(";");
            String[] tanggal = separated[6].split(";");
            String[] idBer = separated[8].split(";");

            rowItems = new ArrayList<RowItem>();

            for (int i = 0; i < nama_user.length; i++) {
                RowItem item = new RowItem(nama_user[i], pic_url[i], isi_status[i], tanggal[i], level[i], jumlah_Suka[i], jumlah_Komen[i], idBer[i]);
                rowItems.add(item);
            }

            CostumAdapter adapter = new CostumAdapter(getActivity().getApplicationContext(), rowItems);
            lvHomePage.setAdapter(adapter);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onPreExecute();
        }
    }
}
