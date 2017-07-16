package com.example.shris.abc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final List<String> web;
    private final List<Drawable> imageId;
    public final static String em = "com.example.shris.abc";
    public CustomAdapter(Activity context, List<String> web, List<Drawable> imageId) {
        super(context, R.layout.single_row, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.single_row, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        final Switch s = (Switch) rowView.findViewById(R.id.switch1);
        txtTitle.setText(web.get(position));
        imageView.setImageDrawable(imageId.get(position));
        Context context = getContext();
        final DatabaseHandler db = new DatabaseHandler(context, null , null, 1);
        boolean isth = db.CheckRecord(web.get(position));
        if (isth)
            s.setChecked(true);
        s.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (s.isChecked()){
                            Runnable ar = new Runnable() {
                                @Override
                                public void run() {
                                    db.addApp(web.get(position));
                                }
                            };
                            Thread dbat = new Thread(ar);
                            dbat.start();
                            Log.e("Added", "Yay");
                            Intent intent = new Intent(v.getContext(), indiapp.class);
                            intent.putExtra(em, web.get(position));
                            //Log.d("Message is ", msg);
                            v.getContext().startActivity(intent);
                        }
                        else {
                            Runnable dr = new Runnable() {
                                @Override
                                public void run() {
                                    db.delApp(web.get(position));
                                }
                            };
                            Thread dbdt = new Thread(dr);
                            dbdt.start();
                            Log.e("Deleted", "Yay");
                        }
                    }
                }
        );
        return rowView;
    }
}
