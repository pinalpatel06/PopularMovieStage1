package com.example.android.popularmovieappstage1;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import butterknife.ButterKnife;
import butterknife.Bind;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
/**
 * ;
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    private  static final String MOVIE_SHARE_HASHTAG = "#Popular Movie App";
    private String mtitle;


    //Bing XML control with Object to used other method of class
    @Bind(R.id.movieTitle) TextView title;
    @Bind(R.id.moviePoster) ImageView moviePoster;
    @Bind(R.id.movieOverview) TextView overView;
    @Bind(R.id.movieRatings) TextView ratings;
    @Bind(R.id.movieReleaseDate) TextView releaseDate;

    //set sharing option on action bar
    public MovieDetailActivityFragment() {
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this,rootView);
        Intent intent = getActivity().getIntent();
        if(intent!=null || intent.hasExtra("movieRowItem")){
            MovieRowItem selectedMovie = intent.getParcelableExtra("movieRowItem");
            if(selectedMovie != null) {
                mtitle = selectedMovie.getTitle();
                fillDataOnDetailView(selectedMovie); // fill movie detail on detail activity
            }
        }
        return rootView;
    }
    private void fillDataOnDetailView(MovieRowItem SelectedMovie){
        title.setText(SelectedMovie.getTitle());
        Picasso.with(getContext()).load(SelectedMovie.getImageUrl())
                .resize(1500,1500)
                .placeholder(R.drawable.clip)
                .error(R.drawable.error)
                .into(moviePoster);
        releaseDate.setText("Release Date " + SelectedMovie.getReleaseDate());
        ratings.setText("Ratings "+SelectedMovie.getRatings());
        overView.setText(SelectedMovie.getOverview());
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_movie_detail, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareMovieIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null.....");
        }
    }
    private Intent createShareMovieIntent(){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                mtitle + MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }

}
