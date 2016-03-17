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
/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();

    public MovieDetailActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_movie_detail, container, false);
        String final_url="http://image.tmdb.org/t/p/w780/";
        Intent intent = getActivity().getIntent();
        if(intent!=null){
            MovieRowItem selectedMovie = intent.getParcelableExtra("com.example.android.popularmovieappstage1.movieRowItem");
            if(selectedMovie != null) {
                String nm = selectedMovie.getTitle();
            }
                   /* ((TextView) rootView.findViewById(R.id.movieTitle))
                    //.setText(intent.getExtras().getString("name"));
                    .setText(selectedMovie.getTitle());*/
                    /*ImageView imagePoster = (ImageView) rootView.findViewById(R.id.moviePoster);
                    final_url=final_url.concat(intent.getExtras().getString("url"));
                    Picasso.with(getContext()).load(final_url).resize(1500,1500).into(imagePoster);
                    ((TextView) rootView.findViewById(R.id.movieReleaseDate))
                            .setText("Release Date- " + intent.getExtras().getString("releaseDate"));
                    ((TextView) rootView.findViewById(R.id.movieRatings))
                            .setText("Average Ratings-  "+intent.getExtras().getDouble("ratings"));
                    ((TextView) rootView.findViewById(R.id.movieOverview))
                            .setText(intent.getExtras().getString("overview"));
                    */

        }
        return rootView;
    }
}
