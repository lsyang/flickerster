package com.codepath.flickerster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickerster.R;
import com.codepath.flickerster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lsyang on 3/9/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        TextView title;
        TextView overview;
        ImageView movieImage;
    }

    public MovieArrayAdapter(Context context, List<Movie>movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for position
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        // check the existing view being used
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView =  inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.movieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String image_url;
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image_url = movie.getBackdropPath();
        } else {
            image_url = movie.getPosterPath();
        }

        viewHolder.overview.setText(movie.getOverview());
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.movieImage.setImageResource(0);

        Picasso.with(getContext()).load(image_url).fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.movieImage);
        // return to view
        return convertView;
    }
}
