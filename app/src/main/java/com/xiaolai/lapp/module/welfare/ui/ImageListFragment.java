package com.xiaolai.lapp.module.welfare.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.base.IBasePresenter;
import com.xiaolai.lapp.bean.ImageShowBean;
import com.xiaolai.lapp.module.welfare.presenter.ImageListPresenterImpl;
import com.xiaolai.lapp.module.welfare.ui.adapter.WelfareListAdapter;
import com.xiaolai.lapp.module.welfare.view.IImageListView;
import com.xiaolai.lapp.rxbus.RxBus;
import com.xiaolai.lapp.rxbus.event.ImageManagerEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by laizhibin on 2017/3/24.
 */
public class ImageListFragment extends BaseFragment<ImageListPresenterImpl> implements IImageListView {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private String imageType;

    WelfareListAdapter mAdapter;

    private ArrayList<String> imgList = new ArrayList<>();

    /*public static ImageListFragment newInstance(List<ImageShowBean> imgList){
        ImageListFragment instance = new ImageListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("imgList", (Serializable) imgList);
        instance.setArguments(bundle);
        return instance;
    }*/

    public static ImageListFragment newInstance(String imageType){
        ImageListFragment instance = new ImageListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imageType", imageType);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //imgList = (List<ImageShowBean>) getArguments().getSerializable("imgList");
            imageType = getArguments().getString("imageType");
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_image_list;
    }

    @Override
    protected void initView() {
        mPresenter = new ImageListPresenterImpl(this, imageType);
        mAdapter = new WelfareListAdapter(R.layout.item_image_list);
        mAdapter.setEnableLoadMore(false);
        SlideInBottomAnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(animationAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if(mAdapter.isEdit()){
                    mAdapter.toggleItemChecked(i, (BaseViewHolder) mRecyclerView.getChildViewHolder(view));
                    if(mAdapter.getSelectCount() == mAdapter.getData().size()){
                        RxBus.getInstance().post(new ImageManagerEvent(ImageManagerEvent.ALL_SELECT_EVENT, true));
                    }else{
                        RxBus.getInstance().post(new ImageManagerEvent(ImageManagerEvent.ALL_SELECT_EVENT, false));
                    }
                }else {
                    WelfareDetailActivity.launchActivity(getActivity(), i, imgList);
                }
            }
        });

        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if(!mAdapter.isEdit()) {
                    RxBus.getInstance().post(new ImageManagerEvent(ImageManagerEvent.SHOW_MENU_EVENT));
                    mAdapter.setEdit(true);
                    //mAdapter.toggleItemChecked(position, (BaseViewHolder) mRecyclerView.getChildViewHolder(view));
                }
                return true;
            }
        });
    }

    public void cancelEdit(){
        mAdapter.setEdit(false);
        mAdapter.setAllSelect(false);
        mAdapter.notifyDataSetChanged();
    }

    public boolean isEdit(){
        return mAdapter.isEdit();
    }

    public void setAllSelect(boolean allSelect){
        mAdapter.setAllSelect(allSelect);
        mAdapter.notifyDataSetChanged();
    }

    public void deleteSelect(){
        List<ImageShowBean> selectData = mAdapter.getSelectData();
        for(ImageShowBean imageShowBean : selectData){
            mAdapter.remove(mAdapter.getData().indexOf(imageShowBean));
        }
        mPresenter.deleteData(selectData, imageType);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mPresenter.requestData();
    }

    @Override
    public void loadData(List<? extends ImageShowBean> imageList) {
        if(imageList.size() == 0){
            mEmptyLayout.showNoData();
            return;
        }
        mAdapter.setNewData(imageList);

        imgList.clear();
        for(ImageShowBean imageBean : imageList){
            imgList.add(imageBean.getImgPath());
        }
        isFirstIn = false;
    }
}
