package com.xiaolai.lapp.module.news.ui.adapter;

import android.view.MotionEvent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.callback.SimpleItemTouchHelper;
import com.xiaolai.lapp.module.news.model.GankTypeInfo;

import java.util.Collections;

/**
 * Created by laizhibin on 2017/3/26.
 */
public class ChannelListAdapter extends BaseQuickAdapter<GankTypeInfo, BaseViewHolder>
        implements SimpleItemTouchHelper.OnMoveAndSwipeListener{

    private SimpleItemTouchHelper mItemTouchHelperCallback;
    private OnItemMoveListener mItemMoveListener;

    private boolean selectType = false;

    public void setSelectType(){
        selectType = true;
    }
    public ChannelListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, GankTypeInfo item) {
        if (selectType && helper.getLayoutPosition() < 3){
            helper.setBackgroundRes(R.id.tv_channel_value, R.drawable.shape_channel_grey);
        }else{
            helper.setBackgroundRes(R.id.tv_channel_value, R.drawable.ripple_item_bg_border);
        }
        helper.setText(R.id.tv_channel_value, item.getKey());
        if (mItemTouchHelperCallback != null) {
            helper.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 触摸事件发生的时候，如果是定死频道，直接不给拖拽
                    if (helper.getLayoutPosition() < 3) {
                        mItemTouchHelperCallback.setLongPressDragEnabled(false);
                        return true;
                    } else {
                        mItemTouchHelperCallback.setLongPressDragEnabled(true);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if(fromPosition>2 && toPosition>2){
            Collections.swap(mData, fromPosition, toPosition);
            //交换RecyclerView列表中item的位置
            notifyItemMoved(fromPosition, toPosition);
            if (mItemMoveListener != null) {
                mItemMoveListener.onItemMove(fromPosition, toPosition);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        //删除mItems数据
        mData.remove(position);
        //删除RecyclerView列表对应item
        notifyItemRemoved(position);
    }

    public void setItemTouchHelper(SimpleItemTouchHelper callback1) {
        mItemTouchHelperCallback = callback1;
    }

    /**
     * 设置item拖拽移动的监听
     * @param itemMoveListener item移动时的监听
     */
    public void setItemMoveListener(OnItemMoveListener itemMoveListener) {
        mItemMoveListener = itemMoveListener;
    }

    public interface OnItemMoveListener {
        void onItemMove(int fromPosition, int toPosition);
    }
}
