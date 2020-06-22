package com.kisslove.dunzoassignment.api;

import com.kisslove.dunzoassignment.models.PhotosModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApi {

    @GET("rest")
    Call<PhotosModel> getPhotos(@Query("method") String method, @Query("api_key") String api_key,
                                @Query("text") String text, @Query("format") String format,
                                @Query("nojsoncallback") int nojsoncallback, @Query("per_page") int per_page,
                                @Query("page") int page);
}
