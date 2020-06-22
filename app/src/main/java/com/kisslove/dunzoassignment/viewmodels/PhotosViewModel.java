package com.kisslove.dunzoassignment.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kisslove.dunzoassignment.models.Photo;
import com.kisslove.dunzoassignment.models.SearchModel;
import com.kisslove.dunzoassignment.repositories.PhotosRepository;

import java.util.List;

public class PhotosViewModel extends ViewModel {

    private MutableLiveData<SearchModel> searchModel = new MutableLiveData<>();

    private PhotosViewModel() {
    }

    public LiveData<List<Photo>> getPhotos() {
        final PhotosRepository photosRepository = PhotosRepository.getInstance();
        LiveData<List<Photo>> photoList = Transformations.switchMap(searchModel, searchModel -> photosRepository
                .getPhotosList(searchModel.getPageNo(), searchModel.getText()));
        
        return photoList;
    }

    public void setSearchModel(SearchModel searchModel) {
        this.searchModel.setValue(searchModel);
    }

    public static class PhotosViewModelFactory extends ViewModelProvider.NewInstanceFactory {

        public PhotosViewModelFactory() {
        }

        @Override
        public <T extends ViewModel> T create(Class<T> viewModel) {
            return (T) new PhotosViewModel();
        }
    }
}