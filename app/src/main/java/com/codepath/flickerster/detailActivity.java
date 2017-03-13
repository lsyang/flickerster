package com.codepath.flickerster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.codepath.flickerster.adapters.MovieArrayAdapter.displayImage;

/**
 * Created by lsyang on 3/12/17.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detailTitle) TextView title;
    @BindView(R.id.detailOverview) TextView overview;
    @BindView(R.id.detailRating) RatingBar rating;
    @BindView(R.id.preview) ImageView preview;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        title.setText(getIntent().getStringExtra("title"));
        overview.setText(getIntent().getStringExtra("overview"));
        displayImage(R.drawable.placeholder_landscape, getIntent().getStringExtra("preview"), preview, getApplicationContext());
        rating.setRating((float) getIntent().getDoubleExtra("rating", 0.0)/2);
        id = getIntent().getStringExtra("id");
    }

    @OnClick(R.id.preview)
    public void playPreview() {
        Intent intent = new Intent(DetailActivity.this, QuickPlayActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
