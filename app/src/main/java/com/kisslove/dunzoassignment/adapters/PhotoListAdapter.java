package com.kisslove.dunzoassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kisslove.dunzoassignment.R;
import com.kisslove.dunzoassignment.models.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoListAdapter extends RecyclerView
        .Adapter<PhotoListAdapter
        .DataObjectHolder> {
    static int fixed = 0;
    private static PhotoListAdapter.MyClickListener myClickListener;
    private final Context context;
    private List<Photo> photoList = new ArrayList<>();

    public PhotoListAdapter(Context context,
                            List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_row_layout, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, int position) {
        Photo photo = photoList.get(position);
//        holder.title.setText(photo.getTitle());
        String imageUrl = "https://farm%s.staticflickr.com/%s/%s_%s_m.jpg";
        Glide.with(context)
                .load(String.format(imageUrl, photo.getFarm(), photo.getServer(), photo.getId(), photo.getSecret()))
                .placeholder(R.drawable.photo_placeholder)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

//        @BindView(R.id.tv_title)
//        TextView title;
        @BindView(R.id.iv_photo)
        ImageView photo;

        public DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

}
