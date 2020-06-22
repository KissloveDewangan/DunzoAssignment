package com.kisslove.dunzoassignment.repositories;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.kisslove.dunzoassignment.api.FlickrApi;
import com.kisslove.dunzoassignment.api.FlickrService;
import com.kisslove.dunzoassignment.models.Photo;
import com.kisslove.dunzoassignment.models.Photos;
import com.kisslove.dunzoassignment.models.PhotosModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotosRepository {

    private static PhotosRepository instance;

    private FlickrApi flickrApi;

    public PhotosRepository() {
        flickrApi = FlickrService.cteateService(FlickrApi.class);
    }

    public static PhotosRepository getInstance() {
        if (instance == null) {
            instance = new PhotosRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Photo>> getPhotosList(int pageNo, String text) {
        final MutableLiveData<List<Photo>> photoList = new MutableLiveData<>();
        flickrApi.getPhotos("flickr.photos.search", "062a6c0c49e4de1d78497d13a7dbb360",
                text, "json", 1, 4, pageNo)
                .enqueue(new Callback<PhotosModel>() {
                    @Override
                    public void onResponse(Call<PhotosModel> call, Response<PhotosModel> response) {
                        if (response.isSuccessful() && response.body() != null &&
                                response.body().getStat().contentEquals("ok")) {
                            Photos photos = response.body().getPhotos();
                            photoList.setValue(photos.getPhoto());
                        }
                    }

                    @Override
                    public void onFailure(Call<PhotosModel> call, Throwable t) {
                        photoList.setValue(null);
                    }
                });
        return photoList;
    }
}
