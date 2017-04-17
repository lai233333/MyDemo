package com.xiaolai.lapp.module.mine.ui;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.bean.ThanksBean;
import com.xiaolai.lapp.callback.AppBarStateChangeListener;
import com.xiaolai.lapp.module.news.ui.NewsDetailActivity;
import com.xiaolai.lapp.utils.ImageLoadUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @Author xiaolai
 * @time 2017/4/13 16:48
 * @des 个人中心
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapseToolbar;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.rv_thanks)
    RecyclerView mRvThanks;
    @BindView(R.id.tv_version_name)
    TextView mTvVersion;

    BaseQuickAdapter mThanksAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        initToolBar(mToolbar,"");
        mCollapseToolbar.setCollapsedTitleTextColor(Color.BLACK);
        mCollapseToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    mToolbar.setTitle("");
                }else if(state == State.COLLAPSED){
                    //折叠状态
                    mToolbar.setTitle("个人信息");
                }else {
                    //中间状态
                    mToolbar.setTitle("");
                }
            }
        });

        ImageLoadUtil.displayCircle(ivIcon,R.drawable.icon);
        Glide.with(this).load(R.drawable.icon)
                .bitmapTransform(new BlurTransformation(getActivity(), 23, 4)).into(ivBg);

        mThanksAdapter = new BaseQuickAdapter<ThanksBean, BaseViewHolder>(R.layout.item_thanks) {
            @Override
            protected void convert(BaseViewHolder helper, ThanksBean item) {
                helper.setText(R.id.tv_thanks_title,item.getTitle());
                helper.setText(R.id.tv_thanks_desc,item.getDesc());
            }
        };
        mThanksAdapter.setEnableLoadMore(false);
        mThanksAdapter.openLoadAnimation();
        mThanksAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                NewsDetailActivity.launchActivity(getActivity(),((ThanksBean)mThanksAdapter.getItem(position)).getUrl());
            }
        });
        mRvThanks.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvThanks.setAdapter(mThanksAdapter);

        try {
            mTvVersion.setText("当前版本："+getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void requestData(boolean isRefresh) {
        List<String> thanksTitles = Arrays.asList(getResources().getStringArray(R.array.thanks_title));
        List<String> thanksDesc = Arrays.asList(getResources().getStringArray(R.array.thanks_desc));
        List<String> thanksUrls = Arrays.asList(getResources().getStringArray(R.array.thanks_url));
        List<ThanksBean> thanksBeanList = new ArrayList<>();
        for(int i=0; i<thanksTitles.size(); i++){
            ThanksBean thanksBean = new ThanksBean();
            thanksBean.setTitle(thanksTitles.get(i));
            thanksBean.setDesc(thanksDesc.get(i));
            thanksBean.setUrl(thanksUrls.get(i));
            thanksBeanList.add(thanksBean);
        }
        mThanksAdapter.setNewData(thanksBeanList);
    }
}
