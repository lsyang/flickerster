package com.codepath.flickerster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.flickerster.adapters.MovieArrayAdapter;
import com.codepath.flickerster.models.Movie;
import com.codepath.flickerster.models.Movie.Popularity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.codepath.flickerster.models.Movie.fromJSONArray;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.lvMovies) ListView lvItems;

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);

        lvItems.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = movies.get(position);
                String movieId = movie.getId();
                Popularity popularity = movie.getPopularity();
                if (popularity == Popularity.HIGH) {
                    Intent intent = new Intent(MovieActivity.this, QuickPlayActivity.class);
                    intent.putExtra("id", movieId);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MovieActivity.this, DetailActivity.class);
                    intent.putExtra("title", movie.getOriginalTitle());
                    intent.putExtra("id", movieId);
                    intent.putExtra("overview", movie.getOverview());
                    intent.putExtra("rating", movie.getRating());
                    intent.putExtra("preview", movie.getBackdropPath());
                    startActivity(intent);

                }

            }
        });

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResult = null;
                try {
                    movieJsonResult = response.getJSONArray("results");
                    movies.addAll(fromJSONArray(movieJsonResult));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                    Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
