package com.xiaolai.lapp.module.movie.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.module.news.ui.adapter.ViewPagerAdapterWithTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author xiaolai
 * @time 2017/4/3 18:24
 * @des ${TODO}
 */

public class MovieCommentActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    ViewPagerAdapterWithTitle mAdapter;

    private String id;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_movie_comment;
    }

    @Override
    protected void initViews() {
        id = getIntent().getStringExtra("movieId");
        initToolBar(mToolBar, getIntent().getStringExtra("movieName")+"的评论");
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new ViewPagerAdapterWithTitle(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        List<String> mTitles = new ArrayList<>();
        mTitles.add("短评");
        mTitles.add("影评");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MovieCommentFragment.newInstance(id));
        fragments.add(MovieReviewFragment.newInstance(id));
        mAdapter.setItems(fragments, mTitles);
        mViewPager.setCurrentItem(getIntent().getIntExtra("pagePosition",0));
    }

    public static void launchActivity(Activity context,String id,String name, int position){
        Intent intent = new Intent(context, MovieCommentActivity.class);
        intent.putExtra("movieId",id);
        intent.putExtra("movieName",name);
        intent.putExtra("pagePosition",position);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }
}
