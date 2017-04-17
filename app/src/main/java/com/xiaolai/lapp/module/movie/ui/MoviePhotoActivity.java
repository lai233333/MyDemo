package com.xiaolai.lapp.module.movie.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.bean.MoviePhoto;
import com.xiaolai.lapp.bean.MoviePhotoBean;
import com.xiaolai.lapp.module.movie.presenter.MoviePhotoPresenterImpl;
import com.xiaolai.lapp.module.movie.ui.adapter.MoviePhotoPagerAdapter;
import com.xiaolai.lapp.module.movie.view.IMoviePhotoView;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ViewShowEvent;
import com.xiaolai.lapp.utils.ShareUtil;
import com.xiaolai.lapp.widget.PhotoViewPager;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * @Author xiaolai
 * @time 2017/4/3 21:52
 * @des ${TODO}
 */

public class MoviePhotoActivity extends BaseActivity implements IMoviePhotoView{

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.view_pager)
    PhotoViewPager mViewPager;
    @BindView(R.id.tv_photo_date)
    TextView tvPhotoDate;
    @BindView(R.id.tv_photo_desc)
    TextView tvPhotoDesc;
    @BindView(R.id.tv_photo_useful)
    TextView tvPhotoUseful;
    @BindView(R.id.tv_photo_comment)
    TextView tvPhotoComment;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottomView;

    private String mId;
    private MoviePhotoPagerAdapter mAdapter;
    private int totalCount;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_movie_photo;
    }

    @Override
    protected void initViews() {
        mId = getIntent().getStringExtra("movieId");
        mPresenter = new MoviePhotoPresenterImpl(this,mId);
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mAdapter = new MoviePhotoPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                MoviePhoto moviePhoto = mAdapter.getItem(position);
                mToolBar.setTitle("电影剧照（"+(position+1)+"/"+mAdapter.getCount()+")");
                tvPhotoDate.setText(moviePhoto.getCreated_at());
                tvPhotoDesc.setText(moviePhoto.getDesc());
                tvPhotoUseful.setText(moviePhoto.getRecs_count()+"");
                tvPhotoComment.setText(moviePhoto.getComments_count()+"");
            }
        });
        registerViewShowEvent();
    }

    private void registerViewShowEvent() {
        RxBus.getInstance().register(this, ViewShowEvent.class, new Action1<ViewShowEvent>() {
            @Override
            public void call(ViewShowEvent viewShowEvent) {
                if(mToolBar.getVisibility() == View.VISIBLE){
                    dismissViewUp(mToolBar);
                    dismissViewDown(rlBottomView);
                }else{
                    showViewDown(mToolBar);
                    showViewUp(rlBottomView);
                }
            }
        });
    }

    private void showViewUp(View view){
        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", 100f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,"alpha",0f,1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1, animator2);
        set.start();

    }
    private void showViewDown(View view){
        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", -100f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,"alpha",0f,1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1, animator2);
        set.start();

    }

    private void dismissViewDown(final View view){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", 0f, 100f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,"alpha",1f,0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1, animator2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        set.start();
    }
    private void dismissViewUp(final View view){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", 0f, -100f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,"alpha",1f,0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1, animator2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        set.start();
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mPresenter.requestData();
    }

    @OnClick(R.id.ib_share)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ib_share:
                ShareUtil.share(this,"高清大图："+mAdapter.getItem(mViewPager.getCurrentItem()).getAlt());
                break;
        }
    }

    public static void launchActivity(Activity context, String id, int position){
        Intent intent = new Intent(context, MoviePhotoActivity.class);
        intent.putExtra("movieId",id);
        intent.putExtra("position",position);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }

    @Override
    public void loadData(MoviePhotoBean data) {
        totalCount = data.getTotal();
        mAdapter.setNewData(data.getPhotos());
        MoviePhoto moviePhoto = data.getPhotos().get(0);
        mToolBar.setTitle("电影剧照（"+1+"/"+mAdapter.getCount()+")");
        tvPhotoDate.setText(moviePhoto.getCreated_at());
        tvPhotoDesc.setText(moviePhoto.getDesc());
        tvPhotoUseful.setText(moviePhoto.getRecs_count()+"");
        tvPhotoComment.setText(moviePhoto.getComments_count()+"");

    }

    @Override
    public void loadMoreData(MoviePhotoBean data) {
        mAdapter.addData(data.getPhotos());
    }
}
