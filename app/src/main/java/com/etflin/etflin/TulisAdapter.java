package com.etflin.etflin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.List;

;

public class TulisAdapter extends BaseAdapter {
    public static final String PREFS_NAME = "MyEtflin";
    private Context context;
    List<GridItem> rowItems;


    TulisAdapter(Context context, List<GridItem> rowItems){
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView gridImage;
        TextView gridJudul, gridKet;
        ViewHolder(View v){
            gridImage = (ImageView) v.findViewById(R.id.gridImage);
            gridJudul = (TextView) v.findViewById(R.id.gridJudul);
            gridKet = (TextView) v.findViewById(R.id.gridKet);
        }
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater minflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        final String user = settings.getString("username", "");

        if (convertView == null) {
            convertView = minflater.inflate(R.layout.tulis_grid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }
        final GridItem row_pos = rowItems.get(position);

        holder.gridImage.setImageResource(context.getResources().getIdentifier(row_pos.getGridImage(), "drawable", "com.etflin.etflin"));
        holder.gridImage.getLayoutParams().height = 150;
        holder.gridImage.getLayoutParams().width = 150;
        holder.gridJudul.setText(row_pos.getGridJudul());
        holder.gridKet.setText(row_pos.getGridKet());

        return convertView;
    }

}
