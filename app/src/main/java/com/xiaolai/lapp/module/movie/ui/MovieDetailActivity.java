package com.xiaolai.lapp.module.movie.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.base.IBasePresenter;
import com.xiaolai.lapp.bean.MovieComment;
import com.xiaolai.lapp.bean.MovieDetailBean;
import com.xiaolai.lapp.bean.MoviePersonBean;
import com.xiaolai.lapp.bean.MoviePhoto;
import com.xiaolai.lapp.bean.MoviePopularReview;
import com.xiaolai.lapp.bean.MovieRatingBean;
import com.xiaolai.lapp.bean.MovieTrailer;
import com.xiaolai.lapp.bean.TrailerPhotoEntity;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.movie.presenter.MovieDetailPresenterImpl;
import com.xiaolai.lapp.module.movie.ui.adapter.TrailerPhotoAdapter;
import com.xiaolai.lapp.module.movie.view.IMovieDetailView;
import com.xiaolai.lapp.module.news.ui.NewsDetailActivity;
import com.xiaolai.lapp.module.news.ui.adapter.ViewPagerAdapterWithTitle;
import com.xiaolai.lapp.module.welfare.ui.WelfareFragment;
import com.xiaolai.lapp.utils.DensityUtil;
import com.xiaolai.lapp.utils.ImageLoadUtil;
import com.xiaolai.lapp.utils.ShareUtil;
import com.xiaolai.lapp.widget.FullyLinearLayoutManager;
import com.xiaolai.lapp.widget.TranslucentScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by laizhibin on 2017/3/28.
 */
public class MovieDetailActivity extends BaseActivity<IBasePresenter> implements TranslucentScrollView.OnScrollChangedListener, IMovieDetailView {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tv_movie_title)
    TextView mTvTitle;
    @BindView(R.id.tsv_movie_detail)
    TranslucentScrollView mScrollView;
    @BindView(R.id.iv_movie_icon)
    ImageView mIvIcon;
    @BindView(R.id.iv_head_bg)
    ImageView mIvHeadBg;
    @BindView(R.id.tv_rating)
    TextView tvRate;
    @BindView(R.id.rb_rating)
    RatingBar rbRate;
    @BindView(R.id.tv_rating_people)
    TextView tvRatePeople;
    @BindView(R.id.tv_title)
    TextView tvName;
    @BindView(R.id.tv_year_type)
    TextView tvYearType;
    @BindView(R.id.tv_row_name)
    TextView tvRowName;
    @BindView(R.id.tv_pub_date)
    TextView tvPubDate;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_summary_expand)
    TextView tvSummaryExpand;
    @BindView(R.id.ll_content)
    LinearLayout llContext;
    @BindView(R.id.rv_actors)
    RecyclerView rvActors;
    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;
    @BindView(R.id.tab_layout)
    TabLayout mTabComment;
    @BindView(R.id.rv_comment)
    RecyclerView rvComments;
    @BindView(R.id.rv_review)
    RecyclerView rvReviews;

    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private static final int SUMMARY_STATE_SHRINK = 1;// 收起状态
    private static final int SUMMARY_STATE_EXPAND = 2;// 展开状态
    private int mState = SUMMARY_STATE_SHRINK;//默认收起状态

    private String mId, mTitle, mUrl;
    private BaseQuickAdapter mActorAdapter;
    private BaseMultiItemQuickAdapter mPhotoAdapter;
    private int directorsNum;
    private String shareUrl;
    private List<TrailerPhotoEntity> trailerPhotoList;
    private BaseQuickAdapter mCommentAdapter;
    private BaseQuickAdapter mReviewAdapter;
    private MovieDetailBean data;

    public static void launchActivity(Activity context, View transitionView, String id, String title, String url){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra("movieId", id);
        intent.putExtra("movieTitle", title);
        intent.putExtra("movieUrl", url);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        transitionView, context.getResources().getString(R.string.transition_movie_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void initViews() {
        getExtra();
        mPresenter = new MovieDetailPresenterImpl(this, mId);
        initToolBar(mToolBar, "");
        mToolBar.setBackgroundColor(Color.argb(0, 44, 44, 44));
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置头部图片
        Glide.with(this).load(mUrl).into(mIvIcon);
        Glide.with(this).load(mUrl)
                .error(R.drawable.image_default_2)
                .bitmapTransform(new BlurTransformation(this, 23, 4)).into(mIvHeadBg);

        headerHeight = getResources().getDimension(R.dimen.dimen_350);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);

        //影人列表
        mActorAdapter = new BaseQuickAdapter<MoviePersonBean, BaseViewHolder>(R.layout.item_actor_list) {
            @Override
            protected void convert(BaseViewHolder helper, MoviePersonBean item) {
                ImageLoadUtil.loadFitCenter(item.getAvatars().getLarge(), (ImageView) helper.getView(R.id.iv_actor_icon));
                helper.setText(R.id.tv_actor_name, item.getName());
                if(helper.getLayoutPosition()<directorsNum){
                    helper.setText(R.id.tv_actor_position, "导演");
                }else{
                    helper.setText(R.id.tv_actor_position, "演员");
                }
            }
        };
        mActorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetailActivity.launchActivity(MovieDetailActivity.this,((MoviePersonBean)mActorAdapter.getItem(position)).getAlt());
            }
        });
        mActorAdapter.setEnableLoadMore(false);
        mActorAdapter.openLoadAnimation();
        rvActors.setFocusable(false);
        rvActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rvActors.setAdapter(mActorAdapter);
        //预告片/剧照
        trailerPhotoList = new ArrayList<>();
        mPhotoAdapter = new TrailerPhotoAdapter(trailerPhotoList);
        mPhotoAdapter.setEnableLoadMore(false);
        mPhotoAdapter.openLoadAnimation();
        mPhotoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(adapter.getItemViewType(position) == Constant.ITEM_TYPE_PHOTO) {
                    MoviePhotoActivity.launchActivity(MovieDetailActivity.this, mId,((TrailerPhotoEntity)adapter.getItem(position)).getMoviePhoto().getPosition());
                }
            }
        });
        rvPhotos.setFocusable(false);
        rvPhotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        rvPhotos.setAdapter(mPhotoAdapter);
        //评论
        mTabComment.addTab(mTabComment.newTab().setText("短评"));
        mTabComment.addTab(mTabComment.newTab().setText("影评"));
        mTabComment.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    rvComments.setVisibility(View.VISIBLE);
                    rvReviews.setVisibility(View.GONE);
                }else if(tab.getPosition() == 1){
                    rvComments.setVisibility(View.GONE);
                    rvReviews.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mCommentAdapter = new BaseQuickAdapter<MovieComment,BaseViewHolder>(R.layout.item_comment_list) {
            @Override
            protected void convert(BaseViewHolder helper, MovieComment item) {
                ImageLoadUtil.displayCircle((ImageView) helper.getView(R.id.iv_author_icon),item.getAuthor().getAvatar());
                helper.setText(R.id.tv_author_name,item.getAuthor().getName());
                ((RatingBar)helper.getView(R.id.rb_movie_rating)).setRating((float) item.getRating().getValue());
                helper.setText(R.id.tv_useful_count, item.getUseful_count()+"");
                helper.setText(R.id.tv_comment_content, item.getContent());
                helper.setText(R.id.tv_comment_date, item.getCreated_at());
            }
        };
        mCommentAdapter.setEnableLoadMore(false);
        mCommentAdapter.openLoadAnimation();
        rvComments.setLayoutManager(new FullyLinearLayoutManager(this));
        rvComments.setAdapter(mCommentAdapter);
        rvComments.setFocusable(false);

        mReviewAdapter = new BaseQuickAdapter<MoviePopularReview, BaseViewHolder>(R.layout.item_review_list) {
            @Override
            protected void convert(BaseViewHolder helper, MoviePopularReview item) {
                ImageLoadUtil.displayCircle((ImageView) helper.getView(R.id.iv_author_icon),item.getAuthor().getAvatar());
                helper.setText(R.id.tv_review_title, item.getTitle());
                helper.setText(R.id.tv_author_name, item.getAuthor().getName());
                ((RatingBar)helper.getView(R.id.rb_movie_rating)).setRating((float) item.getRating().getValue());
                helper.setText(R.id.tv_review_content, item.getSummary());
            }
        };
        mReviewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieCommentActivity.launchActivity(MovieDetailActivity.this,mId,mTitle,1);
            }
        });
        mReviewAdapter.setEnableLoadMore(false);
        mReviewAdapter.openLoadAnimation();
        rvReviews.setLayoutManager(new FullyLinearLayoutManager(this));
        rvReviews.setAdapter(mReviewAdapter);
        rvReviews.setFocusable(false);
        //scrollview滑到顶部 设置监听
        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        mScrollView.setOnScrollChangedListener(this);
        mScrollView.setNestedScrollingEnabled(false);
    }

    public void getExtra() {
        mId = getIntent().getStringExtra("movieId");
        mTitle = getIntent().getStringExtra("movieTitle");
        mUrl = getIntent().getStringExtra("movieUrl");
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mPresenter.requestData();
    }

    @OnClick({R.id.tv_summary_expand, R.id.ll_rate, R.id.ib_share,R.id.iv_movie_icon})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_summary_expand:
                if(mState == SUMMARY_STATE_SHRINK){
                    tvSummary.setMaxLines(Integer.MAX_VALUE);
                    tvSummary.requestLayout();
                    tvSummaryExpand.setText("收起");
                    mState = SUMMARY_STATE_EXPAND;
                }else if(mState == SUMMARY_STATE_EXPAND){
                    tvSummary.setMaxLines(4);
                    tvSummary.requestLayout();
                    tvSummaryExpand.setText("展开");
                    mState = SUMMARY_STATE_SHRINK;
                }
                break;
            case R.id.ll_rate:
                showRateDetailDialog();
                break;
            case R.id.ib_share:
                ShareUtil.share(this,"推荐一部大片给你："+shareUrl);
                break;
            case R.id.iv_movie_icon:
                MoviePhotoActivity.launchActivity(this,mId,0);
                break;
        }
    }

    private void showRateDetailDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_rate_detail,null);
        ((TextView)view.findViewById(R.id.tv_rating)).setText(data.getRating().getAverage()+"");
        ((RatingBar)view.findViewById(R.id.rb_rating)).setRating((float) (data.getRating().getAverage()/2.0));
        ((TextView)view.findViewById(R.id.tv_rating_people)).setText(data.getRatings_count()+"人评分");

        DecimalFormat df = new DecimalFormat("#.0");

        int total = data.getRating().getDetails().getStar5()+data.getRating().getDetails().getStar4()+data.getRating().getDetails().getStar3()+data.getRating().getDetails().getStar2()+data.getRating().getDetails().getStar1();
        ((TextView)view.findViewById(R.id.tv_rate_5)).setText(df.format(data.getRating().getDetails().getStar5()/(total*1.0)*100)+"%");
        setWidth(view.findViewById(R.id.pb_5),Float.parseFloat(df.format(data.getRating().getDetails().getStar5()/(total*1.0)*100)));

        ((TextView)view.findViewById(R.id.tv_rate_4)).setText(df.format(data.getRating().getDetails().getStar4()/(total*1.0)*100)+"%");
        setWidth(view.findViewById(R.id.pb_4),Float.parseFloat(df.format(data.getRating().getDetails().getStar4()/(total*1.0)*100)));

        ((TextView)view.findViewById(R.id.tv_rate_3)).setText(df.format(data.getRating().getDetails().getStar3()/(total*1.0)*100)+"%");
        setWidth(view.findViewById(R.id.pb_3),Float.parseFloat(df.format(data.getRating().getDetails().getStar3()/(total*1.0)*100)));

        ((TextView)view.findViewById(R.id.tv_rate_2)).setText(df.format(data.getRating().getDetails().getStar2()/(total*1.0)*100)+"%");
        setWidth(view.findViewById(R.id.pb_2),Float.parseFloat(df.format(data.getRating().getDetails().getStar2()/(total*1.0)*100)));

        ((TextView)view.findViewById(R.id.tv_rate_1)).setText(df.format(data.getRating().getDetails().getStar1()/(total*1.0)*100)+"%");
        setWidth(view.findViewById(R.id.pb_1),Float.parseFloat(df.format(data.getRating().getDetails().getStar1()/(total*1.0)*100)));

        dialog.setContentView(view);
        dialog.show();
    }

    private void setWidth(View view,float percent){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (DensityUtil.dip2px(200f)*percent/100);
        view.setLayoutParams(layoutParams);
    }

    @Override
    public void onScrollChanged(NestedScrollView who, int l, int t, int oldl, int oldt) {
        //Y轴偏移量
        float scrollY = who.getScrollY();
        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
        if(offset == 1){
            mTvTitle.setText(mTitle);
        }else{
            mTvTitle.setText("电影");
        }
        //Toolbar背景色透明度
        mToolBar.setBackgroundColor(Color.argb((int) (offset * 255), 44, 44, 44));
    }

    @Override
    public void loadData(final MovieDetailBean data) {
        this.data = data;
        //影片基本信息
        tvName.setText(data.getTitle());
        tvYearType.setText(data.getYear()+" / " +MovieFragment.formatGenres(data.getGenres()));
        tvRowName.setText("原名："+data.getOriginal_title());
        StringBuilder country = new StringBuilder("");
        for(String s : data.getCountries()){
            country.append(s+"，");
        }
        country.deleteCharAt(country.length()-1);
        //tvPubDate.setText("上映时间："+data.getYear()+"（"+country+"）");
        tvPubDate.setText("上映时间："+data.getPubdates().get(0));
        tvDuration.setText("片长："+ data.getDurations().get(0));

        //评分
        tvRate.setText(data.getRating().getAverage()+"");
        rbRate.setRating((float) (data.getRating().getAverage()/2.0));
        tvRatePeople.setText(data.getRatings_count()+"人");
        //简介
        tvSummary.setText(data.getSummary());
        //影人
        directorsNum = data.getDirectors().size();
        mActorAdapter.setNewData(data.getDirectors());
        mActorAdapter.addData(data.getCasts());
        /*View footerView = View.inflate(this,R.layout.footer_movie_actor,null);
        mActorAdapter.addFooterView(footerView,-1,LinearLayout.HORIZONTAL);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //分享
        shareUrl = data.getShare_url();
        //预告片/剧照
        for(MovieTrailer movieTrailer : data.getTrailers()){
            TrailerPhotoEntity entity = new TrailerPhotoEntity();
            entity.setItemType(Constant.ITEM_TYPE_TRAILER);
            entity.setMovieTrailer(movieTrailer);
            trailerPhotoList.add(entity);
        }
        for(MoviePhoto moviePhoto : data.getPhotos()){
            TrailerPhotoEntity entity = new TrailerPhotoEntity();
            entity.setItemType(Constant.ITEM_TYPE_PHOTO);
            entity.setMoviePhoto(moviePhoto);
            trailerPhotoList.add(entity);
        }
        mPhotoAdapter.notifyDataSetChanged();
        //评论
        View footerView = View.inflate(this,R.layout.footer_more,null);
        TextView tvMore = (TextView) footerView.findViewById(R.id.tv_more);
        tvMore.setText("全部短评"+data.getComments_count()+"个");
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieCommentActivity.launchActivity(MovieDetailActivity.this,data.getId(),data.getTitle(),0);
            }
        });
        mCommentAdapter.setNewData(data.getPopular_comments());
        mCommentAdapter.addFooterView(footerView);
        View footerView1 = View.inflate(this,R.layout.footer_more,null);
        TextView tvMore1 = (TextView) footerView1.findViewById(R.id.tv_more);
        tvMore1.setText("全部影评"+data.getReviews_count()+"个");
        tvMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieCommentActivity.launchActivity(MovieDetailActivity.this,data.getId(),data.getTitle(),1);
            }
        });
        mReviewAdapter.setNewData(data.getPopular_reviews());
        mReviewAdapter.addFooterView(footerView1);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        llContext.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        llContext.setVisibility(View.VISIBLE);
    }
}
