package com.xiaolai.lapp.module.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.module.home.adapter.ViewPagerAdapter;
import com.xiaolai.lapp.module.mine.ui.MineFragment;
import com.xiaolai.lapp.module.movie.ui.MovieFragment;
import com.xiaolai.lapp.module.news.ui.NewsMainFragment;
import com.xiaolai.lapp.module.news.ui.adapter.ViewPagerAdapterWithTitle;
import com.xiaolai.lapp.module.video.ui.VideoFragment;
import com.xiaolai.lapp.module.welfare.ui.WelfareFragment;
import com.xiaolai.lapp.utils.ToastUtil;
import com.xiaolai.lapp.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final int MESSAGE_SWITCH_ITEM = 111;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavBar;
    @BindView(R.id.vp_container)
    AutoScrollViewPager vpContainer;

    private ViewPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    private int mCurrentItem = 0;

    private long mExitTime;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_SWITCH_ITEM:
                    vpContainer.setCurrentItem(mCurrentItem, false);
                    break;
            }
        }
    };

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mBottomNavBar
                .setMode(BottomNavigationBar.MODE_SHIFTING)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .setBarBackgroundColor(R.color.main_color)
                .setInActiveColor(R.color.main_color)
                .setActiveColor(R.color.md_white_1000)
                .addItem(new BottomNavigationItem(R.drawable.menu_news, "新闻"))
                .addItem(new BottomNavigationItem(R.drawable.menu_photo, "美女"))
                .addItem(new BottomNavigationItem(R.drawable.menu_movie, "电影"))
                .addItem(new BottomNavigationItem(R.drawable.menu_video, "视频"))
                .addItem(new BottomNavigationItem(R.drawable.menu_mine, "我的"))
                .initialise();
        mBottomNavBar.setTabSelectedListener(new BottomNavigationBar.SimpleOnTabSelectedListener(){
            @Override
            public void onTabSelected(int position) {
                mCurrentItem = position;
                mHandler.sendEmptyMessage(MESSAGE_SWITCH_ITEM);
            }
        });

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpContainer.setAdapter(mAdapter);
        vpContainer.setOffscreenPageLimit(3);
        vpContainer.setNoScroll(true);

    }

    @Override
    protected void requestData(boolean isRefresh) {
        mFragments = new ArrayList<>();
        mFragments.add(new NewsMainFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new MovieFragment());
        mFragments.add(new VideoFragment());
        mFragments.add(new MineFragment());
        mAdapter.setItems(mFragments);
        mBottomNavBar.selectTab(mCurrentItem);
        vpContainer.setCurrentItem(mCurrentItem);
    }

    public static void launchActivity(Activity context){
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.showToast("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }
}
