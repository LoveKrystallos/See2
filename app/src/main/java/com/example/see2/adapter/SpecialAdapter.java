package com.example.see2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.example.see2.R;
import com.example.see2.bean.SpecialBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;


import java.util.ArrayList;

public class SpecialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<SpecialBean.DataBean.BannerListBean> bannerList;
    private ArrayList<SpecialBean.DataBean.ListBean> list;
    private int TYPE_VIEW_ONE = 1;
    private int TYPE_VIEW_TWO = 2;
    private final LayoutInflater inflater;

    public SpecialAdapter(Context context, ArrayList<SpecialBean.DataBean.BannerListBean> bannerList, ArrayList<SpecialBean.DataBean.ListBean> list) {
        this.context = context;
        this.bannerList = bannerList;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW_ONE) {
            View view = inflater.inflate(R.layout.change_banner, parent, false);
            return new ViewHolder5(view);
        } else {
            View view = inflater.inflate(R.layout.change_article, parent, false);
            return new ViewHolder6(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if (itemViewType == TYPE_VIEW_ONE) {
            //获取图片，和theme
            ArrayList<String> imgs = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (SpecialBean.DataBean.BannerListBean bannerListBean : bannerList) {
                imgs.add(bannerListBean.getImage_url());
                titles.add(bannerListBean.getTheme());
            }

            final ViewHolder5 holder5 = (ViewHolder5) holder;
            holder5.banner
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    .setImages(imgs)
                    .setBannerTitles(titles)
                    .setDelayTime(3000)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).into(imageView);
                        }
                    })
                    .start();
            holder5.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    holder5.pb.setMax(bannerList.size());
                    holder5.pb.setProgress(position + 1);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            ViewHolder6 holder2 = (ViewHolder6) holder;
            Glide.with(context).load(list.get(position+1).getImage_url()).into(holder2.article_img);
            holder2.article_title.setText(list.get(position+1).getTheme());
            holder2.article_type.setText(list.get(position+1).getColumn_name());
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_VIEW_ONE;
        } else {
            return TYPE_VIEW_TWO;
        }
    }

    class ViewHolder5 extends RecyclerView.ViewHolder {
        public View rootView;
        public Banner banner;
        public ProgressBar pb;

        public ViewHolder5(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.banner = (Banner) rootView.findViewById(R.id.banner);
            this.pb = (ProgressBar) rootView.findViewById(R.id.pb);
        }

    }

    class ViewHolder6 extends RecyclerView.ViewHolder{
        public View rootView;
        public ImageView article_img;
        public TextView article_title;
        public TextView article_type;

        public ViewHolder6(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.article_img = (ImageView) rootView.findViewById(R.id.article_img);
            this.article_title = (TextView) rootView.findViewById(R.id.article_title);
            this.article_type = (TextView) rootView.findViewById(R.id.article_type);
        }

    }
}
