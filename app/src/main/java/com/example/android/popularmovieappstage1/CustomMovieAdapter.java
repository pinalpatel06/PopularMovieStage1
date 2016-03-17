package com.example.android.popularmovieappstage1;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.content.Context;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by pinal on 29/02/2016.
 */
public class CustomMovieAdapter extends ArrayAdapter<MovieRowItem> {
    private static LayoutInflater inflater;
    Context context;

    public CustomMovieAdapter(Context context, int resourceId, ArrayList<MovieRowItem> movieData) {
        super(context, resourceId, movieData);
        this.context = context;
            inflater = (LayoutInflater)context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MovieRowItem movieRowItem = getItem(position);

        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_movie_forecast,parent,false);
        }
        ImageView image1= (ImageView)convertView.findViewById(R.id.list_movie_forecast_imageView);
        TextView title = (TextView)convertView.findViewById(R.id.list_movie_forecast_textView);
        Picasso.with(context)
                .load(movieRowItem.getImageUrl())
                .into(image1);
        title.setText(movieRowItem.getTitle());
        return convertView;
    }
}