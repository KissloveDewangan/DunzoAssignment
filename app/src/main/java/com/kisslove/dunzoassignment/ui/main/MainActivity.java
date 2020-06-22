package com.kisslove.dunzoassignment.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kisslove.dunzoassignment.R;
import com.kisslove.dunzoassignment.adapters.PhotoListAdapter;
import com.kisslove.dunzoassignment.models.Photo;
import com.kisslove.dunzoassignment.models.SearchModel;
import com.kisslove.dunzoassignment.viewmodels.PhotosViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar_layout)
    RelativeLayout progressBarLayout;
    @BindView(R.id.rv_photos)
    RecyclerView photosRV;
    @BindView(R.id.et_search)
    EditText searchEditText;
    @BindView(R.id.tv_title)
    TextView searchTitle;
    @BindView(R.id.tv_page_no)
    TextView pageNoText;

    private List<Photo> photoList = new ArrayList<>();
    private PhotoListAdapter photoListAdapter;
    private PhotosViewModel photosViewModel;
    private int pageNo = 1;
    private String searchVal = "food";
    private SearchModel searchModel = new SearchModel(searchVal, pageNo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initUI();
        loadPhotos();
    }

    private void initUI() {
        progressBarLayout.setVisibility(View.GONE);

        setImageTitle();
        setPageNo();

        photoListAdapter = new PhotoListAdapter(this, photoList);
        photosRV.setHasFixedSize(false);
        photosRV.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        photosRV.setAdapter(photoListAdapter);

        photoListAdapter.setOnItemClickListener((position, v) -> {

        });
    }

    private void loadPhotos() {
        progressBarLayout.setVisibility(View.VISIBLE);
        photosViewModel = ViewModelProviders
                .of(this, new PhotosViewModel.PhotosViewModelFactory())
                .get(PhotosViewModel.class);
        photosViewModel.setSearchModel(searchModel);
        photosViewModel.getPhotos().observe(this, photos -> {
            progressBarLayout.setVisibility(View.GONE);
            if (photos != null && photos.size() > 0) {
                photoList.clear();
                photoList.addAll(photos);
                photoListAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.ib_search)
    public void onSearchButtonClicked() {
        String text = searchEditText.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter some text!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBarLayout.setVisibility(View.VISIBLE);
        searchVal = text;
        pageNo = 1;
        setImageTitle();
        setPageNo();
        setSearchModel();
        photosViewModel.setSearchModel(searchModel);
    }

    @OnClick(R.id.ib_back)
    public void onBackButtonClicked() {
        if (pageNo <= 1) {
            Toast.makeText(getApplicationContext(), "No previous page!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBarLayout.setVisibility(View.VISIBLE);
        pageNo--;
        setPageNo();
        setSearchModel();
        photosViewModel.setSearchModel(searchModel);
    }

    @OnClick(R.id.ib_next)
    public void onNextButtonClicked() {
        progressBarLayout.setVisibility(View.VISIBLE);
        pageNo++;
        setPageNo();
        setSearchModel();
        photosViewModel.setSearchModel(searchModel);
    }

    private void setImageTitle(){
        StringBuilder sb = new StringBuilder("Search result for ");
        sb.append("\"");
        sb.append(searchVal);
        sb.append("\"");
        searchTitle.setText(sb.toString());
    }

    private void setPageNo(){
        pageNoText.setText("Page " + pageNo);
    }

    private void setSearchModel(){
        searchModel.setText(searchVal);
        searchModel.setPageNo(pageNo);
    }
}