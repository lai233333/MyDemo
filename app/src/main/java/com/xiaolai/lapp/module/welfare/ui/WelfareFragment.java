package com.xiaolai.lapp.module.welfare.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.callback.SimpleOnCompleteListener;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.welfare.db.LocalImageDb;
import com.xiaolai.lapp.module.welfare.model.LocalImageInfo;
import com.xiaolai.lapp.module.welfare.presenter.IWelfarePresenter;
import com.xiaolai.lapp.module.welfare.presenter.WelfarePresenterImpl;
import com.xiaolai.lapp.module.welfare.ui.adapter.WelfareListAdapter;
import com.xiaolai.lapp.module.welfare.view.IWelfareView;
import com.xiaolai.lapp.utils.DensityUtil;
import com.xiaolai.lapp.utils.DownloadUtil;
import com.xiaolai.lapp.utils.ShareUtil;
import com.xiaolai.lapp.utils.TimeUtil;
import com.xiaolai.lapp.utils.ToastUtil;
import com.xiaolai.lapp.widget.BaseDialog;
import com.xiaolai.lapp.widget.ListPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfareFragment extends BaseFragment<IWelfarePresenter> implements IWelfareView, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.rv_news_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_right)
    ImageButton ibRight;

    WelfareListAdapter mAdapter;

    private int selectPosition;
    private BaseDialog mDialog;
    private ListPopWindow mPopWindow;

    private List<String> popWindowList = new ArrayList<>();
    private ArrayList<String> imgList = new ArrayList<>();
    private int[] location;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_welfare;
    }

    @Override
    protected void initView() {
        mPresenter = new WelfarePresenterImpl(this, "福利");
        initToolBar(mToolBar, "颜如玉");
        //setHasOptionsMenu(true);
        ibRight.setOnClickListener(this);
        mAdapter = new WelfareListAdapter(R.layout.item_image_list);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        SlideInBottomAnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(animationAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                selectPosition = i;
                WelfareDetailActivity.launchActivity(getActivity(), selectPosition, imgList);
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                selectPosition = i;
                showMenuDialog();
                return false;
            }
        });

        mPopWindow = new ListPopWindow(getActivity());
        popWindowList.add("图片管理");
        popWindowList.add("路径设置");
        BaseQuickAdapter popAdapter = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_textview_black, popWindowList){

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv_text,item);
            }
        };
        mPopWindow.setAdapter(popAdapter);
        popAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPopWindow.dismiss();
                if(position == 0){
                    ImageManagerActivity.launchActivity(getActivity());
                }else if(position == 1){
                    Snackbar.make(mToolBar,"右转设置--->", Snackbar.LENGTH_SHORT).setAction("介里", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.showToast("呵呵");
                        }
                    }).show();
                }
            }
        });

        location = new int[2];
        mToolBar.getLocationOnScreen(location);
    }

    private void showMenuDialog() {
        View menuView = View.inflate(getActivity(), R.layout.dialog_welfare_menu, null);
        mDialog = new BaseDialog.Builder(getActivity()).dismissButton().setContentView(menuView).create();
        mDialog.show();
        menuView.findViewById(R.id.item_collection).setOnClickListener(this);
        menuView.findViewById(R.id.item_download).setOnClickListener(this);
        menuView.findViewById(R.id.item_share).setOnClickListener(this);
        menuView.findViewById(R.id.item_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_collection:
                mDialog.dismiss();
                if(LocalImageDb.getInstance().getImageInfoByUrl(imgList.get(selectPosition),Constant.IMAGE_TYPE_COLLECT) != null){
                    ToastUtil.showToast("不要重复收藏啦");
                    return;
                }
                LocalImageInfo imageInfo = new LocalImageInfo();
                imageInfo.setDesc(Constant.poetry.get((int)(0+Math.random()*(Constant.poetry.size()-1-0+1))));
                imageInfo.setType(Constant.IMAGE_TYPE_COLLECT);
                imageInfo.setImageUrl(imgList.get(selectPosition));
                imageInfo.setDate(TimeUtil.getDataFull());
                if(imageInfo.save()){
                    ToastUtil.showToast("收藏成功");
                }else{
                    ToastUtil.showToast("收藏失败");
                }
                break;
            case R.id.item_download:
                mDialog.dismiss();
                DownloadUtil.downloadImage(imgList.get(selectPosition), new SimpleOnCompleteListener(){
                    @Override
                    public void onComplete(String path) {
                        ToastUtil.showToast("图片已保存至：" + path);
                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
                    }

                    @Override
                    public void onFail() {
                        ToastUtil.showToast("保存失败");
                    }
                });
                break;
            case R.id.item_share:
                mDialog.dismiss();
                ShareUtil.share(getActivity(), LAppApplication.getContext().getResources().getString(R.string.share_image));
                break;
            case R.id.item_delete:
                mDialog.dismiss();
                mAdapter.remove(selectPosition);
                break;
            case R.id.btn_right:
                mPopWindow.setWidth(DensityUtil.dip2px(150f));
                mPopWindow.showAsDropDown(mToolBar,DensityUtil.dip2px(200f)-location[0],0);
                break;
        }
    }

    @Override
    protected void requestData(boolean isRefresh) {
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

        imgList.clear();
        for(GankItemBean gankItemBean : data){
            imgList.add(gankItemBean.getUrl());
        }
    }

    @Override
    public void loadMoreData(List<GankItemBean> data) {
        mAdapter.loadMoreComplete();
        mAdapter.addData(data);

        for(GankItemBean gankItemBean : data){
            imgList.add(gankItemBean.getUrl());
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestMoreData();
    }
}
