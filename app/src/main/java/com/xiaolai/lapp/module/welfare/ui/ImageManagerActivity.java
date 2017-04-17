package com.xiaolai.lapp.module.welfare.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.home.adapter.ViewPagerAdapter;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ImageManagerEvent;
import com.xiaolai.lapp.widget.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by laizhibin on 2017/3/24.
 */
public class ImageManagerActivity extends BaseActivity{

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.tl_image)
    SegmentTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    AutoScrollViewPager mViewPager;
    @BindView(R.id.rl_menu)
    RelativeLayout rlMenuView;
    @BindView(R.id.fab_all_select)
    FloatingActionButton fabAllSelect;

    ViewPagerAdapter mPagerAdapter;
    private boolean allSelect = false;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_image_manager;
    }

    @Override
    protected void initViews() {
        initToolBar(mToolbar,"");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTabLayout.setTabData(new String[]{"收藏","下载"});
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(((ImageListFragment)mPagerAdapter.getItem(mViewPager.getCurrentItem())).isEdit()){
                    mTabLayout.setCurrentTab(mViewPager.getCurrentItem());
                }else {
                    mViewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }
        });
        registerRxBus();
    }

    private void registerRxBus() {
        //显示底部操作按钮
        RxBus.getInstance().register(this, ImageManagerEvent.class, new Action1<ImageManagerEvent>() {
            @Override
            public void call(ImageManagerEvent imageManagerEvent) {
                switch (imageManagerEvent.getEventType()){
                    case ImageManagerEvent.SHOW_MENU_EVENT:
                        showMenuView();
                        mViewPager.setNoScroll(true);
                        break;
                    case ImageManagerEvent.ALL_SELECT_EVENT:
                        if(!(imageManagerEvent.isAllSelect() ^ allSelect)){
                            return;
                        }
                        if(imageManagerEvent.isAllSelect()){
                            allSelect = true;
                            fabAllSelect.setImageResource(R.drawable.ic_all_select);
                        }else{
                            allSelect = false;
                            fabAllSelect.setImageResource(R.drawable.ic_all_select_no);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void requestData(boolean isRefresh) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ImageListFragment.newInstance(Constant.IMAGE_TYPE_COLLECT));
        fragments.add(ImageListFragment.newInstance(Constant.IMAGE_TYPE_DOWNLOAD));
        mPagerAdapter.setItems(fragments);
    }

    @OnClick({R.id.fab_all_select,R.id.fab_cancel,R.id.fab_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_cancel:
                allSelect = false;
                fabAllSelect.setImageResource(R.drawable.ic_all_select_no);
                dismissMenuView();
                mViewPager.setNoScroll(false);
                ((ImageListFragment)mPagerAdapter.getItem(mViewPager.getCurrentItem())).cancelEdit();
                break;
            case R.id.fab_all_select:
                if(allSelect){
                    allSelect = false;
                    fabAllSelect.setImageResource(R.drawable.ic_all_select_no);
                }else{
                    allSelect = true;
                    fabAllSelect.setImageResource(R.drawable.ic_all_select);
                }
                ((ImageListFragment)mPagerAdapter.getItem(mViewPager.getCurrentItem())).setAllSelect(allSelect);
                break;
            case R.id.fab_delete:
                ((ImageListFragment)mPagerAdapter.getItem(mViewPager.getCurrentItem())).deleteSelect();
                break;
        }
    }

    private void showMenuView(){
        rlMenuView.setVisibility(View.VISIBLE);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(rlMenuView, "translationY", 100f, 0f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(rlMenuView,"alpha",0f,1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1, animator2);
        set.start();

    }

    private void dismissMenuView(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(rlMenuView, "translationY", 0f, 100f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(rlMenuView,"alpha",1f,0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.playTogether(animator1, animator2);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rlMenuView.setVisibility(View.GONE);
            }
        });
        set.start();
    }

    public static void launchActivity(Activity context){
        context.startActivity(new Intent(context, ImageManagerActivity.class));
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
}
