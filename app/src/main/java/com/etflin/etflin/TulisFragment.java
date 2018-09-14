package com.etflin.etflin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
public class TulisFragment extends Fragment {
    private ProgressBar progressBar;
    List<GridItem> gridItems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tulis, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);

        GridView mygrid = (GridView) view.findViewById(R.id.mygrid);
        gridItems = new ArrayList<GridItem>();

        String[] imagelist = getResources().getStringArray(R.array.gridtulisimage);
        final String[] judullist = getResources().getStringArray(R.array.gridtulisjudul);
        String[] keteranganGrid = getResources().getStringArray(R.array.gridtuulisket);

        for (int i = 0; i < judullist.length; i++) {
            GridItem item = new GridItem(imagelist[i], judullist[i], keteranganGrid[i]) ;
            gridItems.add(item);
        }

        TulisAdapter gridadapter = new TulisAdapter(getActivity().getApplicationContext(), gridItems);
        mygrid.setAdapter(gridadapter);

        mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent myIntent = new Intent(getActivity(), Publish.class);
                    startActivity(myIntent);
                }
            }
        });
        return view;
    }

}
