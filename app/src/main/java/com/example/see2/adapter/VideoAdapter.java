package com.example.see2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.see2.R;
import com.example.see2.bean.VideoBean;

import java.util.ArrayList;

import cn.jzvd.JzvdStd;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VideoBean.DataBean.ListBean> videolist;

    public VideoAdapter(Context context, ArrayList<VideoBean.DataBean.ListBean> videolist) {
        this.context = context;
        this.videolist = videolist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.change_vodel2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(videolist.get(position).getImage_url()).into(holder.video_view.thumbImageView);
        holder.video_view.setUp(videolist.get(position).getVideo_url(), videolist.get(position).getTheme());
        holder.vodel2_title.setText(videolist.get(position).getTheme());
        holder.vodel2_type.setText(videolist.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return videolist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout ll;
        public TextView vodel2_title;
        public TextView vodel2_type;
        public JzvdStd video_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*this.vodel2_img = (ImageView) itemView.findViewById(R.id.vodel2_img);
            this.vodel2_vodel = (ImageView) itemView.findViewById(R.id.vodel2_vodel);*/

            this.ll = (RelativeLayout) itemView.findViewById(R.id.ll);
            this.video_view = (JzvdStd) itemView.findViewById(R.id.video_view);
            this.vodel2_title = (TextView) itemView.findViewById(R.id.vodel2_title);
            this.vodel2_type = (TextView) itemView.findViewById(R.id.vodel2_type);
        }
    }


}
