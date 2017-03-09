package com.codepath.flickerster.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.codepath.flickerster.models.Movie;

import java.util.List;

/**
 * Created by lsyang on 3/9/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie>movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

}
