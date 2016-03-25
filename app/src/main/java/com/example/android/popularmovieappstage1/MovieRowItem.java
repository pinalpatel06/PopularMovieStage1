package com.example.android.popularmovieappstage1;

import android.os.Parcelable;
import android.os.Parcel;
import android.annotation.SuppressLint;
/**
 * Created by pinal on 29/02/2016.
 */
//MovieRowItem class is used to represent Movies raw data like title,rating, genre,release date etc.

@SuppressLint("ParcelCreator")
public class MovieRowItem implements Parcelable{
    private String title; //store movie title
    private String overview; //store movie overview
    private String releaseDate ; // store future date to release movie
    private double ratings; // store ratings of movie
    private String imageUrl; // store complete url


    public MovieRowItem(String title,String overview , String releaseDate,double ratings, String imageUrl){
        this.title = title;
        this.overview  = overview;
        this.releaseDate = releaseDate;
        this.ratings = ratings;
        this.imageUrl = imageUrl;

    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String nTitle){
        title = nTitle;
    }
    public String getOverview(){
        return overview;
    }
    public void setOverview(String nOverview){
        overview=nOverview;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageBaseUrl){
       imageUrl = ConstantData.BASE_URL_IMAGE + imageBaseUrl;
    }
    public String getReleaseDate(){
        return releaseDate;
    }
    public void setReleaseDate(String nReleaseDate){
        releaseDate = nReleaseDate;
    }
    public double getRatings(){
        return ratings;
    }
    public void setRatings(double nRatings){
        ratings = nRatings;
    }

    public MovieRowItem(){

    }

    //creater
    public static final Parcelable.Creator<MovieRowItem> CREATER =
            new Parcelable.Creator<MovieRowItem>(){

        @Override
        public MovieRowItem createFromParcel(Parcel in){
            return new MovieRowItem(in);
        }

        @Override
        public MovieRowItem[] newArray(int size){
            return new MovieRowItem[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.ratings);
        dest.writeString(this.imageUrl);
    }

    protected MovieRowItem(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.ratings = in.readDouble();
        this.imageUrl = in.readString();
    }

    public static final Creator<MovieRowItem> CREATOR = new Creator<MovieRowItem>() {
        @Override
        public MovieRowItem createFromParcel(Parcel source) {
            return new MovieRowItem(source);
        }

        @Override
        public MovieRowItem[] newArray(int size) {
            return new MovieRowItem[size];
        }
    };
}
