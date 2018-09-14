package com.etflin.etflin;


import android.os.Bundle;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

        EmojiCompat.Config config = new BundledEmojiCompatConfig(getActivity()) ;
        EmojiCompat.init(config);

        perintah("https://www.etflin.com/beranda.php?type=normal");

        ImageView refreshBeranda = (ImageView) view.findViewById(R.id.refreshBeranda);
        lvHomePage = (ListView) view.findViewById(R.id.dinamiklist);
        lvHomePage.setOnScrollListener(refreshlist);

        refreshBeranda.setOnClickListener(refreshpost);

        prg = (ProgressBar) view.findViewById(R.id.loadingBrnd);
        prg.setVisibility(View.VISIBLE);

        return view;
    }

    private View.OnClickListener refreshpost = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rowItems = new ArrayList<RowItem>();
            CostumAdapter adapter = new CostumAdapter(getActivity().getApplicationContext(), rowItems);
            lvHomePage.setAdapter(adapter);

            prg.setVisibility(View.VISIBLE);
            perintah("https://www.etflin.com/beranda.php?type=normal");
        }
    };

    private AbsListView.OnScrollListener refreshlist = new AbsListView.OnScrollListener() {
        int mOffset=0;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int position = firstVisibleItem+visibleItemCount;
            int limit = totalItemCount - mOffset;
            // Check if bottom has been reached
            if (position >= limit && totalItemCount > 0) {

            }
        }
    };

    private void perintah(String urlTarget) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = urlTarget;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        prg.setVisibility(View.GONE);
                        String[] separated = response.split("xyx");
                        String[] pic_url = separated[0].split(";");
                        String[] nama_user = separated[1].split(";");
                        String[] level = separated[2].split(";");
                        String[] isi_status = separated[3].split(";");
                        String[] jumlah_Suka = separated[4].split(";");
                        String[] jumlah_Komen = separated[5].split(";");
                        String[] tanggal = separated[6].split(";");
                        String[] namasuka = separated[7].split(";");
                        String[] idBer = separated[8].split(";");
                        String[] totaltipe = separated[9].split(";");
                        String[] picutama = separated[10].split(";");
                        String[] userId = separated[11].split(";");

                        rowItems = new ArrayList<RowItem>();

                        for (int i = 0; i < nama_user.length; i++) {
                            RowItem item = new RowItem(nama_user[i], pic_url[i], isi_status[i], tanggal[i], level[i], jumlah_Suka[i], jumlah_Komen[i], idBer[i], namasuka[i], totaltipe[i], picutama[i], userId[i]);
                            rowItems.add(item);
                        }

                        CostumAdapter adapter = new CostumAdapter(getActivity().getApplicationContext(), rowItems);
                        lvHomePage.setAdapter(adapter);
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
