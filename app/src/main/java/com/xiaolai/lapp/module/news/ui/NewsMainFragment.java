package com.xiaolai.lapp.module.news.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;
import com.xiaolai.lapp.module.news.presenter.INewsMainPresenter;
import com.xiaolai.lapp.module.news.presenter.NewsMainPresenterImpl;
import com.xiaolai.lapp.module.news.ui.adapter.ViewPagerAdapterWithTitle;
import com.xiaolai.lapp.module.news.view.INewsMainView;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ChannelEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by laizhibin on 2017/3/19.
 */
public class NewsMainFragment extends BaseFragment<INewsMainPresenter> implements INewsMainView{

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    ViewPagerAdapterWithTitle mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_main;
    }

    @Override
    protected void initView() {
        mPresenter = new NewsMainPresenterImpl(this);
        initToolBar(mToolBar, "黄金屋");
        setHasOptionsMenu(true);
        mAdapter = new ViewPagerAdapterWithTitle(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void handleChannelEvent(ChannelEvent channelEvent) {
        switch (channelEvent.getEventType()){
            case ChannelEvent.ADD_EVENT:
                mAdapter.addItem(NewsListFragment.newInstance(channelEvent.getGankTypeInfo().getValue()), channelEvent.getGankTypeInfo().getKey());
                break;
            case ChannelEvent.DEL_EVENT:
                mViewPager.setCurrentItem(0);
                mAdapter.delItem(channelEvent.getGankTypeInfo().getKey());
                break;
            case ChannelEvent.SWAP_EVENT:
                mAdapter.swapItems(channelEvent.getFromPos(), channelEvent.getToPos());
                break;
        }
    }

    @Override
    protected void requestData(boolean isRefresh) {
        this.isRefresh = isRefresh;
        mPresenter.requestChannelDb();
    }

    @Override
    public void loadData(List<GankTypeInfo> data) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for(GankTypeInfo gankTypeInfo : data){
            titles.add(gankTypeInfo.getKey());
            fragments.add(NewsListFragment.newInstance(gankTypeInfo.getValue()));
        }
        mAdapter.setItems(fragments, titles);
        isFirstIn = false;
    }

    @Override
    public void registerRxBusEvent() {
        RxBus.getInstance().register(this, ChannelEvent.class, new Action1<ChannelEvent>() {
            @Override
            public void call(ChannelEvent channelEvent) {
                handleChannelEvent(channelEvent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_channel, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_channel) {
            ChannelActivity.launchActivity(getActivity());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
