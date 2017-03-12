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
import com.codepath.flickerster.models.Movie.Popularity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lsyang on 3/9/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    static class ViewHolder {
        @BindView(R.id.tvTitle) TextView title;
        @BindView(R.id.tvOverview) TextView overview;
        @BindView(R.id.ivMovieImage) ImageView movieImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }


    public MovieArrayAdapter(Context context, List<Movie>movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getPopularity().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Popularity.values().length;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for position
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        int type = getItemViewType(position);

        // check the existing view being used
        if (convertView == null) {

            convertView = getInflatedLayoutForType(type);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // setting text only for unpopular movie
        if (type == Popularity.LOW.ordinal()) {
            viewHolder.overview.setText(movie.getOverview());
            viewHolder.title.setText(movie.getOriginalTitle());
        }

        // setting image
        viewHolder.movieImage.setImageResource(0);
        String image_url;
        int placeholder;
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT && type == Popularity.LOW.ordinal()) {
            image_url = movie.getPosterPath();
            placeholder = R.drawable.placeholder;
        } else {
            image_url = movie.getBackdropPath();
            placeholder = R.drawable.placeholder_landscape;
        }

        Picasso.with(getContext()).load(image_url).fit().centerCrop()
                .placeholder(placeholder)
                .into(viewHolder.movieImage);

        // return to view
        return convertView;
    }

    private View getInflatedLayoutForType(int type) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (type == Popularity.HIGH.ordinal()) {
            return inflater.inflate(R.layout.item_popular_movie, null);
        } else {
            return inflater.inflate(R.layout.item_movie, null);
        }
    }
}
