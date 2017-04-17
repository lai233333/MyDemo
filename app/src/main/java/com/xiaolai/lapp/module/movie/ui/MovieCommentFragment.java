package com.xiaolai.lapp.module.movie.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.bean.MovieComment;
import com.xiaolai.lapp.bean.MovieCommentBean;
import com.xiaolai.lapp.module.movie.presenter.MovieCommentPresenterImpl;
import com.xiaolai.lapp.module.movie.view.IMovieCommentView;
import com.xiaolai.lapp.utils.ImageLoadUtil;
import com.xiaolai.lapp.widget.FullyLinearLayoutManager;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * @Author xiaolai
 * @time 2017/4/3 14:41
 * @des 详情页短评fragment
 */

public class MovieCommentFragment extends BaseFragment implements IMovieCommentView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_movie_comment)
    RecyclerView mRvComments;

    private String id;
    private BaseQuickAdapter mAdapter;

    public static MovieCommentFragment newInstance(String id){
        MovieCommentFragment fragment = new MovieCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MovieId", id);
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("MovieId");
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView() {
        mPresenter = new MovieCommentPresenterImpl(this, id);
        mAdapter = new BaseQuickAdapter<MovieComment,BaseViewHolder>(R.layout.item_comment_list) {
            @Override
            protected void convert(BaseViewHolder helper, MovieComment item) {
                ImageLoadUtil.displayCircle((ImageView) helper.getView(R.id.iv_author_icon),item.getAuthor().getAvatar());
                helper.setText(R.id.tv_author_name,item.getAuthor().getName());
                ((RatingBar)helper.getView(R.id.rb_movie_rating)).setRating((float) item.getRating().getValue());
                helper.setText(R.id.tv_useful_count, item.getUseful_count()+"");
                helper.setText(R.id.tv_comment_content, item.getContent());
                helper.setText(R.id.tv_comment_date, item.getCreated_at());
            }
        };
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this,mRvComments);
        mAdapter.openLoadAnimation();
        mRvComments.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvComments.setAdapter(mAdapter);
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mPresenter.requestData();
    }

    @Override
    public void loadData(MovieCommentBean data) {
        mAdapter.setNewData(data.getComments());
        isFirstIn = false;
    }

    @Override
    public void loadMoreData(MovieCommentBean data) {
        mAdapter.loadMoreComplete();
        mAdapter.addData(data.getComments());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestMoreData();
    }
}
