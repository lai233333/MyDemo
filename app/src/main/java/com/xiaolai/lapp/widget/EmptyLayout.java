package com.xiaolai.lapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.callback.OnRetryListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by laizhibin on 2017/3/19.
 */
public class EmptyLayout extends FrameLayout {

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    private Context mContext;
    private OnRetryListener mOnRetryListener;

    public int getCurrentStatus() {
        return mCurrentStatus;
    }

    private int mCurrentStatus = STATUS_LOADING;

    @BindView(R.id.tv_net_error)
    TextView mTvEmptyMessage;
    @BindView(R.id.fl_empty_container)
    View mEmptyContainer;
    @BindView(R.id.fl_loading_container)
    View mLoadingView;
    @BindView(R.id.empty_layout)
    FrameLayout mEmptyLayout;
    @BindView(R.id.iv_loading)
    ImageView mIvLoading;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(mContext, R.layout.layout_empty, this);
        ButterKnife.bind(this);
    }

    public void hide() {
        mCurrentStatus = STATUS_HIDE;
        switchEmptyView();
    }

    public void showLoading(){
        mCurrentStatus = STATUS_LOADING;
        switchEmptyView();
    }

    public void showNoNet(){
        mCurrentStatus = STATUS_NO_NET;
        switchEmptyView();
    }

    public void showNoData(){
        mCurrentStatus = STATUS_NO_DATA;
        switchEmptyView();
    }

    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }

    @OnClick(R.id.tv_net_error)
    public void onClick() {
        if (mOnRetryListener != null) {
            mOnRetryListener.onRetry();
        }
    }

    /**
     * 切换视图
     */
    private void switchEmptyView() {
        switch (mCurrentStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mEmptyContainer.setVisibility(GONE);
                mLoadingView.setVisibility(VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_rotate);
                mIvLoading.startAnimation(animation);
                break;
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                mIvLoading.clearAnimation();
                mLoadingView.setVisibility(GONE);
                mEmptyContainer.setVisibility(VISIBLE);
                mTvEmptyMessage.setText("暂无数据");
                Drawable drawableNoData=getResources().getDrawable(R.drawable.ic_no_data);
                drawableNoData.setBounds(0, 0, drawableNoData.getMinimumWidth(), drawableNoData.getMinimumHeight());
                mTvEmptyMessage.setCompoundDrawables(null, drawableNoData, null, null);
                break;
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                mIvLoading.clearAnimation();
                mLoadingView.setVisibility(GONE);
                mEmptyContainer.setVisibility(VISIBLE);
                mTvEmptyMessage.setText("网络异常，点击重试");
                Drawable drawableNoNet=getResources().getDrawable(R.drawable.ic_net_error);
                drawableNoNet.setBounds(0, 0, drawableNoNet.getMinimumWidth(), drawableNoNet.getMinimumHeight());
                mTvEmptyMessage.setCompoundDrawables(null, drawableNoNet,null, null);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                mIvLoading.clearAnimation();
                break;
        }
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus{}
}
