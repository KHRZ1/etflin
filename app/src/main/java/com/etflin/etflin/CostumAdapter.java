package com.etflin.etflin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CostumAdapter extends BaseAdapter {
    public static final String PREFS_NAME = "MyEtflin";
    Context context;
    List<RowItem> rowItems;


    CostumAdapter(Context context, List<RowItem> rowItems){
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
        ImageView profile_pic, contactType, statusLike, statusKomen, profesiLogo;
        TextView member_name, status, jumlahLike, jumlahKomen, waktuStatus, userLevel;
        ViewHolder(View v){
            member_name = (TextView) v.findViewById(R.id.member_name);
            profile_pic = (ImageView) v.findViewById(R.id.profile_pic);
            profesiLogo = (ImageView) v.findViewById(R.id.userMVP);
            status= (TextView) v.findViewById(R.id.status);
            contactType = (ImageView) v.findViewById(R.id.contact_type);
            statusLike = (ImageView) v.findViewById(R.id.statusLike);
            statusKomen = (ImageView) v.findViewById(R.id.statusKomen);
            jumlahLike = (TextView) v.findViewById(R.id.jumlahLike);
            jumlahKomen= (TextView) v.findViewById(R.id.jumlahKomen);
            waktuStatus = (TextView) v.findViewById(R.id.waktuStatus);
            userLevel = (TextView) v.findViewById(R.id.levelUser);

        }
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater minflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = minflater.inflate(R.layout.berandalist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }

        final RowItem row_pos = rowItems.get(position);

        try {
            holder.profile_pic.setImageBitmap(drawable_from_url(row_pos.getProfile_pic_id())); ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        String user = settings.getString("username", "");

        holder.statusLike.setImageResource(R.drawable.star);
        holder.statusLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView statusLike = (ImageView) v.findViewById(R.id.statusLike);
                if (statusLike.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.star).getConstantState()) {
                    statusLike.setImageResource(R.drawable.starfull);

                } else {
                    statusLike.setImageResource(R.drawable.star);

                }

            }
        });

        holder.member_name.setText(row_pos.getMember_name());
        holder.status.setText(row_pos.getStatus());
        holder.contactType.setImageResource(R.drawable.titiktiga);
        holder.statusKomen.setImageResource(R.drawable.komen);
        holder.jumlahLike.setText("Suka " + row_pos.getJumlahSuka());
        holder.jumlahKomen.setText("Komentar " + row_pos.getJumlahKomen());
        holder.waktuStatus.setText(row_pos.getStatusWaktu());
        holder.userLevel.setText(row_pos.getUserLevel());
        if (row_pos.getUserLevel().equals("APOTEKER")){
            holder.profesiLogo.setImageResource(R.drawable.medicine);
        } else if (row_pos.getUserLevel().equals("DOKTER")){
            holder.profesiLogo.setImageResource(R.drawable.stethoscope);
        } else if (row_pos.getUserLevel().equals("PSIKOLOG")){
            holder.profesiLogo.setImageResource(R.drawable.mental);
        } else if (row_pos.getUserLevel().equals("AHLI GIZI")){
            holder.profesiLogo.setImageResource(R.drawable.salad);
        } else if (row_pos.getUserLevel().equals("PERAWAT")){
            holder.profesiLogo.setImageResource(R.drawable.saline);
        }

        return convertView;
    }

    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }
}
