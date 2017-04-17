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
 * Created by laizhibin on 2017/3/27.
 */
public class MovieTopActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    ViewPagerAdapterWithTitle mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_movie_top;
    }

    @Override
    protected void initViews() {
        initToolBar(mToolBar, "豆瓣Top250");
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
        mTitles.add("Top1-50");
        mTitles.add("51-100");
        mTitles.add("101-150");
        mTitles.add("151-200");
        mTitles.add("201-250");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MovieTopFragment.newInstance(0));
        fragments.add(MovieTopFragment.newInstance(50));
        fragments.add(MovieTopFragment.newInstance(100));
        fragments.add(MovieTopFragment.newInstance(150));
        fragments.add(MovieTopFragment.newInstance(200));
        mAdapter.setItems(fragments, mTitles);
    }

    public static void launchActivity(Activity context){
        context.startActivity(new Intent(context, MovieTopActivity.class));
        context.overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }
}
