package com.xiaolai.lapp.module.news.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.callback.SimpleItemTouchHelper;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;
import com.xiaolai.lapp.module.news.presenter.ChannelPresenterImpl;
import com.xiaolai.lapp.module.news.presenter.IChannelPresenter;
import com.xiaolai.lapp.module.news.ui.adapter.ChannelListAdapter;
import com.xiaolai.lapp.module.news.view.IChannelView;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ChannelEvent;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

/**
 * Created by laizhibin on 2017/3/26.
 */
public class ChannelActivity extends BaseActivity<IChannelPresenter> implements IChannelView{

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.rv_common_channel)
    RecyclerView mRvCommon;
    @BindView(R.id.rv_other_channel)
    RecyclerView mRvOther;

    ChannelListAdapter mCommonAdapter;
    BaseQuickAdapter mOtherAdapter;

    public static void launchActivity(Activity context){
        context.startActivity(new Intent(context, ChannelActivity.class));
        context.overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_channel;
    }

    @Override
    protected void initViews() {
        mPresenter = new ChannelPresenterImpl(this);
        initToolBar(mToolbar, "栏目管理");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCommonAdapter = new ChannelListAdapter(R.layout.item_channel_list);
        mCommonAdapter.setSelectType();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        mRvCommon.setLayoutManager(gridLayoutManager);
        mRvCommon.setAdapter(mCommonAdapter);
        mRvCommon.setItemAnimator(new FadeInDownAnimator());

        mOtherAdapter = new ChannelListAdapter(R.layout.item_channel_list);
        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false);
        mRvOther.setLayoutManager(gridLayoutManager1);
        mRvOther.setAdapter(mOtherAdapter);
        mRvOther.setItemAnimator(new FadeInUpAnimator());

        //删除频道
        mCommonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(position>2) {
                    GankTypeInfo item = mCommonAdapter.getItem(position);
                    mPresenter.onChannelAddOrRemove(item.getKey(), false);
                    mCommonAdapter.remove(position);
                    mOtherAdapter.addData(mOtherAdapter.getData().size(), item);
                    RxBus.getInstance().post(new ChannelEvent(ChannelEvent.DEL_EVENT,item));
                }
            }
        });
        //添加频道
        mOtherAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GankTypeInfo item = (GankTypeInfo) mOtherAdapter.getItem(position);
                mPresenter.onChannelAddOrRemove(item.getKey(),true);
                mOtherAdapter.remove(position);
                mCommonAdapter.addData(mCommonAdapter.getData().size(), item);
                RxBus.getInstance().post(new ChannelEvent(ChannelEvent.ADD_EVENT,item));
            }
        });
        //交换位置
        SimpleItemTouchHelper callback1 = new SimpleItemTouchHelper(mCommonAdapter);
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(callback1);
        itemTouchHelper1.attachToRecyclerView(mRvCommon);
        mCommonAdapter.setItemTouchHelper(callback1);
        mCommonAdapter.setItemMoveListener(new ChannelListAdapter.OnItemMoveListener() {
            @Override
            public void onItemMove(int fromPosition, int toPosition) {
                mPresenter.onChannelSwap(mCommonAdapter.getItem(fromPosition).getKey(), mCommonAdapter.getItem(toPosition).getKey(),fromPosition,toPosition);
                RxBus.getInstance().post(new ChannelEvent(ChannelEvent.SWAP_EVENT,fromPosition, toPosition));
            }
        });
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mPresenter.requestData();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }

    @Override
    public void loadData(List<GankTypeInfo> selectList, List<GankTypeInfo> unSelectList) {
        mCommonAdapter.setNewData(selectList);
        mOtherAdapter.setNewData(unSelectList);
    }
}
