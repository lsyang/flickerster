package com.codepath.flickerster;

import android.os.Bundle;
import android.util.Log;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import com.codepath.flickerster.models.Trailer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.codepath.flickerster.models.Trailer.fromJSONArray;

/**
 * Created by lsyang on 3/11/17.
 */

public class QuickPlayActivity extends YouTubeBaseActivity {

    final String APIKEY = "AIzaSyCTxnjYjqANmm7jtbe4YvF3uWX-dUYX12E";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_quick_play);

        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        youTubePlayerView.initialize(APIKEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                            YouTubePlayer youTubePlayer, boolean b) {
                        playVideo(id, youTubePlayer);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                            YouTubeInitializationResult youTubeInitializationResult){

                    }
                });
    }

    private void playVideo(String movieId, final YouTubePlayer youTubePlayer) {
        String url = "https://api.themoviedb.org/3/movie/"+ movieId + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieTrailerJsonResult = null;
                try {
                    movieTrailerJsonResult = response.getJSONArray("results");
                    ArrayList<Trailer> trailers = fromJSONArray(movieTrailerJsonResult);
                    // getting the first trailer in the list to play
                    String key = trailers.get(0).getKey();
                    youTubePlayer.loadVideo(key);
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
