package com.example.android.popularmovieappstage1;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.util.Log;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Intent;
import android.widget.AdapterView;
import android.os.Parcelable;
import android.os.Parcel;
import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;
/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private String LOG_TAG = "Popular_movie_Stage1";
    CustomMovieAdapter customMovieAdapter;
    ArrayList<MovieRowItem> movieRowItemsList;
    String sort_type="popularity";
    @Bind(R.id.gridview_movies) GridView gridView;

    //final static String BASE_URL = "http://image.tmdb.org/t/p/w342"; // BASE_URL to fetch movie data
    public MainActivityFragment() {
    }
    @Override
    public void onStart() {
        super.onStart();
        updateMovie(sort_type);
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }
    private void updateMovie(String sort_type){

            FetchMovieData fetchMovieData = new FetchMovieData();
            fetchMovieData.execute(sort_type);

    }
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.sorting, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //fetch movie by ratings
        if(id == R.id.action_sort_by_ratings){
            sort_type = getString(R.string.pref_sort_ratings);
            updateMovie(sort_type);
            return true;
        }
        //fetch movie by popularity
        if(id==R.id.action_sort_by_popularity){
            sort_type=getString(R.string.pref_sort_popular);
            updateMovie(sort_type);
            return true;
        }
        if(id==R.id.action_refresh){
            updateMovie(sort_type);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        movieRowItemsList = new ArrayList<>();


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,rootView);
        setUpAdapter();
        updateMovie(sort_type);
        return rootView;
    }
    private void setUpAdapter(){
        //initialize main activity with custom adapter object with grid view
        customMovieAdapter= new CustomMovieAdapter(getActivity(),R.layout.list_movie_forecast,movieRowItemsList);
        gridView.setAdapter(customMovieAdapter);
    }
    @OnItemClick(R.id.gridview_movies)
    void onItemClick(int position){
        MovieRowItem movieRowItem = (MovieRowItem) movieRowItemsList.get(position);
        Intent intent = new Intent(getActivity(),MovieDetailActivity.class)
                .putExtra("movieRowItem",movieRowItem);
        startActivity(intent);


    }
/*
    * User defined method for background activity like making url and
    * fetching data from URL
 */
    public class FetchMovieData extends AsyncTask<String, Void, MovieRowItem[]> {
        @Override
        protected MovieRowItem[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            String moviesDetailStr = null;
            final String DISCOVER = "/discover";
            final String MOVIE = "/movie";
            final String SORT_BY = "?sort_by=" + params[0] + ".desc";
            final String API_KEY = "&api_key=";
            //final String API_KEY_VALUE = buildConfig.MOVIE_API_KEY;
            final String API_KEY_VALUE = "1f0f081607bc4bce8419e47eeb04fa99";
            String completeUrl = ConstantData.BASE_URL + DISCOVER + MOVIE + SORT_BY + API_KEY + API_KEY_VALUE;


            try {
                URL url = new URL(completeUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    Log.d(LOG_TAG, "No data fetch");
                    return null;
                }
                moviesDetailStr = buffer.toString();

            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

            }
            return getMovieDataFromJson(moviesDetailStr);
        }
/*
        user defined method for parsing JSON string to individual data
 */

        public MovieRowItem[] getMovieDataFromJson(String movieDetailStr) {
            final String RESULT = "results";
            final String TITLE = "original_title";
            final String OVER_VIEW = "overview";
            final String POSTER_PATH = "poster_path";
            final String RELEASE_DATE = "release_date";
            final String RATINGS = "vote_average";
            MovieRowItem[] movies = null;

            try {
                JSONObject movieJson = new JSONObject(movieDetailStr);
                JSONArray movieArray = movieJson.getJSONArray(RESULT);
                movies = new MovieRowItem[movieArray.length()];
                for (int i = 0; i < movieArray.length(); i++) {
                    JSONObject movieObject = movieArray.getJSONObject(i);
                    MovieRowItem temp_movie = new MovieRowItem();
                    temp_movie.setTitle(movieObject.getString(TITLE));
                    temp_movie.setImageUrl(movieObject.getString(POSTER_PATH));
                    temp_movie.setOverview(movieObject.getString(OVER_VIEW));
                    temp_movie.setRatings(movieObject.getDouble(RATINGS));
                    temp_movie.setReleaseDate(movieObject.getString(RELEASE_DATE));
                    movies[i] = temp_movie;
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }
            return movies;
        }
        @Override
        protected void onPostExecute(MovieRowItem[] all_movies) {
            if(all_movies!=null) {
                movieRowItemsList.clear();
                customMovieAdapter.clear();
                for(MovieRowItem movieData : all_movies){
                    customMovieAdapter.add(movieData);

                }
                customMovieAdapter.notifyDataSetChanged();
                for(MovieRowItem movie : all_movies){
                    movieRowItemsList.add(movie);
                }

            }
        }

    }

}