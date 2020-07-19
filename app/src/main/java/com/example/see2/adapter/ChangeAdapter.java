package com.example.see2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.see2.R;
import com.example.see2.bean.ChangBean;
import com.example.see2.utils.MarqueeText;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import cn.jzvd.JzvdStd;

public class ChangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ChangBean.DataBean.ArticleListBean> articleList;
    private ArrayList<ChangBean.DataBean.BannerListBean> bannerList;
    private ArrayList<ChangBean.DataBean.FlashListBean> flashList;
    private int TYPE_VIEW_ONE = 1;
    private int TYPE_VIEW_TWO = 2;
    private int TYPE_VIEW_THERE = 3;
    private int TYPE_VIEW_FOUR = 4;
    private final LayoutInflater inflater;
    private int type;

    public ChangeAdapter(Context context, ArrayList<ChangBean.DataBean.ArticleListBean> articleList, ArrayList<ChangBean.DataBean.BannerListBean> bannerList, ArrayList<ChangBean.DataBean.FlashListBean> flashList,int type) {
        this.context = context;
        this.articleList = articleList;
        this.bannerList = bannerList;
        this.flashList = flashList;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW_ONE) {
            View view = inflater.inflate(R.layout.change_banner, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == TYPE_VIEW_TWO) {
            View view = inflater.inflate(R.layout.change_article, parent, false);
            return new ViewHolder2(view);
        } else if (viewType == TYPE_VIEW_THERE) {
            View view = inflater.inflate(R.layout.change_vodel, parent, false);
            return new ViewHolder3(view);
        } else {
            View view = inflater.inflate(R.layout.change_vodel2, parent, false);
            return new ViewHolder4(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if (itemViewType == TYPE_VIEW_ONE) {
            //获取图片，和theme
            ArrayList<String> imgs = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            for (ChangBean.DataBean.BannerListBean bannerListBean
                    : bannerList) {
                imgs.add(bannerListBean.getImage_url());
                titles.add(bannerListBean.getTheme());
            }

            final ViewHolder1 holder1 = (ViewHolder1) holder;
            holder1.banner
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
            holder1.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    holder1.pb.setMax(bannerList.size());
                    holder1.pb.setProgress(position + 1);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            StringBuffer stringBuffer1 = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            for (ChangBean.DataBean.FlashListBean flashListBean : flashList) {
                stringBuffer1.append(flashListBean.getDescription());
                stringBuffer2.append(flashListBean.getTheme());
            }
            Log.i("tag1", stringBuffer2.toString());
            if (type == 1) {
//                holder1.banner_tv.setText(bannerList.get(0).getDescription());
                holder1.banner_tv.setSingleLine(true);
                holder1.banner_tv.setText(stringBuffer2.toString());
            } else {
                holder1.banner_cl.setVisibility(View.GONE);
            }
           /* new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0;i<=bannerList.size();i++){
                            holder1.pb.setProgress(i);
                            Thread.sleep(*//*holder1.banner.getDrawingTime()*//*3000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/
        } else if (itemViewType == TYPE_VIEW_TWO) {
            ViewHolder2 holder2 = (ViewHolder2) holder;
            Glide.with(context).load(articleList.get(position+1).getImage_url()).into(holder2.article_img);
            holder2.article_title.setText(articleList.get(position+1).getTheme());
            holder2.article_type.setText(articleList.get(position+1).getColumn_name());
        } else if (itemViewType == TYPE_VIEW_THERE) {
            ViewHolder3 holder3 = (ViewHolder3) holder;
            Glide.with(context).load(articleList.get(position+1).getImage_url()).into(holder3.vodel_img);
            holder3.vodel_title.setText(articleList.get(position+1).getTheme());
            holder3.vodel_type.setText(articleList.get(position+1).getColumn_name());
        } else {
            ViewHolder4 holder4 = (ViewHolder4) holder;
            Glide.with(context).load(articleList.get(position+1).getImage_url()).into(holder4.video_view.thumbImageView);
            holder4.video_view.setUp(articleList.get(position).getVideo_url(), articleList.get(position).getTheme());
            Glide.with(context).load(articleList.get(position+1).getImage_url()).into(holder4.video_view.thumbImageView);

//            holder4.video_view.thumbImageView.setImageURI(articleList.get(position).getImage_url());
            holder4.vodel2_title.setText(articleList.get(position+1).getTheme());
            holder4.vodel2_type.setText(articleList.get(position+1).getDescription());
        }
    }

    @Override
    public int getItemViewType(int position) {
//        int view_type = articleList.get(position).getView_type();
        if (position == 0) {
            return TYPE_VIEW_ONE;
        } else if (articleList.get(position).getView_type() == 1) {
            return TYPE_VIEW_TWO;
        } else if (articleList.get(position).getView_type() == 2) {
            return TYPE_VIEW_THERE;
        } else /*if (articleList.get(position).getView_type() == 4)*/ {
            return TYPE_VIEW_FOUR;
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size() + 1;
    }


    class ViewHolder1 extends RecyclerView.ViewHolder {
        public Banner banner;
        public ProgressBar pb;
        public MarqueeText banner_tv;
        public RelativeLayout banner_cl;

        public ViewHolder1(View rootView) {
            super(rootView);
            this.banner = (Banner) rootView.findViewById(R.id.banner);
            this.pb = (ProgressBar) rootView.findViewById(R.id.pb);
            this.banner_tv = (MarqueeText) rootView.findViewById(R.id.banner_tv);
            this.banner_cl = (RelativeLayout) rootView.findViewById(R.id.banner_cl);
        }

    }

    public static
    class ViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView article_img;
        public TextView article_title;
        public TextView article_type;

        public ViewHolder2(View rootView) {
            super(rootView);
            this.article_img = (ImageView) rootView.findViewById(R.id.article_img);
            this.article_title = (TextView) rootView.findViewById(R.id.article_title);
            this.article_type = (TextView) rootView.findViewById(R.id.article_type);
        }

    }

    class ViewHolder3 extends RecyclerView.ViewHolder {
        public View rootView;
        public ImageView vodel_img;
        public ImageView vodel_vodel;
        public TextView vodel_title;
        public TextView vodel_type;

        public ViewHolder3(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.vodel_img = (ImageView) rootView.findViewById(R.id.vodel_img);
//            this.vodel_vodel = (ImageView) rootView.findViewById(R.id.vodel2_vodel);
            this.vodel_title = (TextView) rootView.findViewById(R.id.vodel_title);
            this.vodel_type = (TextView) rootView.findViewById(R.id.vodel_type);
        }

    }

    class ViewHolder4 extends RecyclerView.ViewHolder  {
        public View rootView;
        public ImageView vodel2_img;
        public ImageView vodel2_vodel;
        public RelativeLayout ll;
        public TextView vodel2_title;
        public TextView vodel2_type;
        public JzvdStd video_view;

        public ViewHolder4(View rootView) {
            super(rootView);
            this.rootView = rootView;
/*            this.vodel2_img = (ImageView) rootView.findViewById(R.id.vodel2_img);
            this.vodel2_vodel = (ImageView) rootView.findViewById(R.id.vodel2_vodel);*/
            this.ll = (RelativeLayout) rootView.findViewById(R.id.ll);
            this.video_view = (cn.jzvd.JzvdStd) rootView.findViewById(R.id.video_view);
            this.vodel2_title = (TextView) rootView.findViewById(R.id.vodel2_title);
            this.vodel2_type = (TextView) rootView.findViewById(R.id.vodel2_type);
        }

    }
}
