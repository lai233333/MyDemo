package com.xiaolai.lapp.module.news.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.bean.FrontPageBean;
import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.module.news.presenter.INewsListPresenter;
import com.xiaolai.lapp.module.news.presenter.NewsListPresenterImpl;
import com.xiaolai.lapp.module.news.ui.adapter.GankListAdapter;
import com.xiaolai.lapp.module.news.view.INewsListView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by laizhibin on 2017/3/22.
 */
public class NewsListFragment extends BaseFragment<INewsListPresenter> implements INewsListView, BaseQuickAdapter.RequestLoadMoreListener {


    private static final String GANK_TYPE_VALUE = "gankTypeValue";

    private String gankTypeValue;

    @BindView(R.id.rv_news_list)
    RecyclerView mRecyclerView;
    private GankListAdapter mAdapter;

    public static NewsListFragment newInstance(String gankTypeValue) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GANK_TYPE_VALUE, gankTypeValue);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gankTypeValue = getArguments().getString(GANK_TYPE_VALUE);
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initView() {
        mPresenter = new NewsListPresenterImpl(this, gankTypeValue);
        mAdapter = new GankListAdapter(R.layout.item_gank_list);
        if("all".equals(gankTypeValue)){
            mAdapter.setAllType(true);
            mPresenter.requestAdData();
        }
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NewsDetailActivity.launchActivity(getActivity(),mAdapter.getItem(i).getUrl());
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                return false;
            }
        });
    }

    @Override
    protected void requestData(boolean isRefresh) {
        this.isRefresh = isRefresh;
        mPresenter.requestData();
    }

    @Override
    public void loadData(List<GankItemBean> data) {
        if(data.size() == 0){
            mEmptyLayout.showNoData();
            return;
        }
        mAdapter.setNewData(data);
        isFirstIn = false;
    }

    @Override
    public void loadMoreData(List<GankItemBean> data) {
        mAdapter.loadMoreComplete();
        mAdapter.addData(data);
    }

    @Override
    public void loadAdData(List<FrontPageBean.DataBeanX.DataBean> adData) {

    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestMoreData();
    }
}
