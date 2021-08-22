package com.example.aim_project;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 리사이클러 뷰에 보여주기위한 어댑터 자바 클래스
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    public ArrayList<Uri> albumImgList;
    public Context mContext;

    // 생성자 정의
    public PictureAdapter(ArrayList<Uri> albumImgList, Context mContext){
        this.albumImgList = albumImgList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.picture_list,parent,false);
        PictureAdapter.ViewHolder viewHolder = new PictureAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PictureAdapter.ViewHolder holder, int position) {
        // 앨범에서 가져온 이미지 표시
        holder.imageView.setImageURI(albumImgList.get(position));
    }

    @Override
    public int getItemCount() {
        return albumImgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item);
        }
    }
}
