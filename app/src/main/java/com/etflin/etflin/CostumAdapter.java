package com.etflin.etflin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.FontRequestEmojiCompatConfig;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.provider.FontRequest;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

import static java.security.AccessController.getContext;

public class CostumAdapter extends BaseAdapter {
    public static final String PREFS_NAME = "MyEtflin";
    private Context context;
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
        ImageView profile_pic, contactType, statusLike, statusKomen, profesiLogo, picutama;
        TextView member_name, jumlahLike, jumlahKomen, waktuStatus, userLevel;
        EmojiTextView status;
        ViewHolder(View v){
            member_name = (TextView) v.findViewById(R.id.member_name);
            profile_pic = (ImageView) v.findViewById(R.id.profile_pic);
            profesiLogo = (ImageView) v.findViewById(R.id.userMVP);
            status = (EmojiTextView) v.findViewById(R.id.status);
            contactType = (ImageView) v.findViewById(R.id.contact_type);
            statusLike = (ImageView) v.findViewById(R.id.statusLike);
            statusKomen = (ImageView) v.findViewById(R.id.statusKomen);
            jumlahLike = (TextView) v.findViewById(R.id.jumlahLike);
            jumlahKomen= (TextView) v.findViewById(R.id.jumlahKomen);
            waktuStatus = (TextView) v.findViewById(R.id.waktuStatus);
            userLevel = (TextView) v.findViewById(R.id.levelUser);
            picutama = (ImageView) v.findViewById(R.id.picutama);

        }
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater minflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
        final String user = settings.getString("username", "");

        if (convertView == null) {
            convertView = minflater.inflate(R.layout.berandalist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }

        final RowItem row_pos = rowItems.get(position);

        EmojiCompat.Config config = new BundledEmojiCompatConfig(context) ;
        EmojiCompat.init(config);




        Glide.with(context).load(row_pos.getProfile_pic_id()).into(holder.profile_pic);
        if (row_pos.getTotalnamasuka().contains(user)){
            holder.statusLike.setImageResource(R.drawable.starfull);
        } else {
            holder.statusLike.setImageResource(R.drawable.star);
        }

        final ViewHolder finalHolder = holder;
        holder.statusLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView jum = finalHolder.jumlahLike;
                ImageView statusLike = finalHolder.statusLike;
                if (statusLike.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.star).getConstantState()) {
                    statusLike.setImageResource(R.drawable.starfull);
                    perintah("https://www.etflin.com/sukai.php?user="+user+"&idber="+row_pos.getIdBer()+"&tipe=sukai");
                    jum.setText("Suka " + String.valueOf(Integer.parseInt(row_pos.getJumlahSuka()) + 1));
                    row_pos.setJumlahSuka(String.valueOf(Integer.parseInt(row_pos.getJumlahSuka()) + 1));
                    row_pos.setTotalnamasuka(row_pos.getTotalnamasuka()+user+",");
                } else {
                    statusLike.setImageResource(R.drawable.star);
                    perintah("https://www.etflin.com/sukai.php?user="+user+"&idber="+row_pos.getIdBer()+"&tipe=batal");
                    jum.setText("Suka " + String.valueOf(Integer.parseInt(row_pos.getJumlahSuka()) - 1));
                    row_pos.setJumlahSuka(String.valueOf(Integer.parseInt(row_pos.getJumlahSuka()) - 1));
                    row_pos.setTotalnamasuka(row_pos.getTotalnamasuka().replace(user + ",", ""));
                }

            }
        });


        if (row_pos.getTotaltipe().toString().equals("Pertanyaan")) {
            CharSequence isi = EmojiCompat.get().process(row_pos.getStatus());
            String label = "<b>PERTANYAAN</b><br><br>" + isi;
            Spanned sp = Html.fromHtml(label);
            finalHolder.status.setGravity(Gravity.CENTER);
            holder.status.setText(sp);
            holder.status.setBackgroundResource(R.drawable.latarstatus);
            holder.status.setPadding(20, 20, 20, 20);
        } else {
            finalHolder.status.setGravity(Gravity.LEFT);
            String isiser = StringEscapeUtils.unescapeJava(row_pos.getStatus());
            CharSequence isi = EmojiCompat.get().process(isiser);
            holder.status.setText(isi);
            holder.status.setBackgroundResource(0);
            holder.status.setPadding(0, 0, 0, 0);
        }


        holder.contactType.setImageResource(R.drawable.titiktiga);
        holder.contactType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                if (user.equals(row_pos.getUserId())){
                    editor.putString("myPost", user);
                    editor.putString("myPostID", row_pos.getIdBer());
                    editor.commit();
                } else {
                    editor.putString("myPost", "nope");
                    editor.commit();
                }

                Intent myIntent = new Intent(context, Pop.class);
                context.startActivity(myIntent);
            }
        });

        if (row_pos.getPicutama().toString().equals("0")) {
            holder.picutama.setImageResource(0);
            holder.picutama.setMaxHeight(0);
        } else {
            Glide.with(context).load(row_pos.getPicutama()).into(holder.picutama);
            holder.status.setPadding(0, 10, 0, 0);
        }


        holder.statusKomen.setImageResource(R.drawable.komen);
        holder.jumlahLike.setText("Suka " + row_pos.getJumlahSuka());
        holder.jumlahKomen.setText("Komentar " + row_pos.getJumlahKomen());
        holder.waktuStatus.setText(row_pos.getStatusWaktu());
        holder.member_name.setText(row_pos.getMember_name());
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

    public void perintah(String urlTarget) {

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = urlTarget;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(context,
                        "Error",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
