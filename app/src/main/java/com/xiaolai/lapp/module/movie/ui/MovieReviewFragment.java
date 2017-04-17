package com.xiaolai.lapp.module.movie.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.bean.MovieComment;
import com.xiaolai.lapp.bean.MoviePopularReview;
import com.xiaolai.lapp.bean.MovieReviewBean;
import com.xiaolai.lapp.module.movie.presenter.MovieReviewPresenterImpl;
import com.xiaolai.lapp.module.movie.view.IMovieCommentView;
import com.xiaolai.lapp.module.movie.view.IMovieReviewView;
import com.xiaolai.lapp.utils.ImageLoadUtil;
import com.xiaolai.lapp.widget.FullyLinearLayoutManager;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;

/**
 * @Author xiaolai
 * @time 2017/4/3 14:46
 * @des ${TODO}
 */

public class MovieReviewFragment extends BaseFragment implements IMovieReviewView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv_movie_comment)
    RecyclerView mRvReviews;

    private String id;
    private BaseQuickAdapter mAdapter;

    public static MovieReviewFragment newInstance(String id) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MovieId", id);
        fragment.setArguments(bundle);
        return fragment;
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
        mPresenter = new MovieReviewPresenterImpl(this, id);
        mAdapter = new BaseQuickAdapter<MoviePopularReview, BaseViewHolder>(R.layout.item_review_list) {
            @Override
            protected void convert(BaseViewHolder helper, MoviePopularReview item) {
                ImageLoadUtil.displayCircle((ImageView) helper.getView(R.id.iv_author_icon),item.getAuthor().getAvatar());
                helper.setText(R.id.tv_review_title, item.getTitle());
                helper.setText(R.id.tv_author_name, item.getAuthor().getName());
                ((RatingBar)helper.getView(R.id.rb_movie_rating)).setRating((float) item.getRating().getValue());
                helper.setText(R.id.tv_review_content, item.getSummary());
                helper.setVisible(R.id.rl_review_bottom, true);
                helper.setText(R.id.tv_review_useful,item.getUseful_count()+"/"+(item.getUseful_count()+item.getUseless_count())+" 有用");
                helper.setText(R.id.tv_review_date, item.getCreated_at());
            }
        };
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRvReviews);
        mAdapter.openLoadAnimation();
        mRvReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvReviews.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieReviewDetailActivity.launchActivity(getActivity(), (MoviePopularReview) mAdapter.getItem(position));
            }
        });
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mPresenter.requestData();
    }

    @Override
    public void loadData(MovieReviewBean data) {
        mAdapter.setNewData(data.getReviews());
        isFirstIn = false;
    }

    @Override
    public void loadMoreData(MovieReviewBean data) {
        mAdapter.loadMoreComplete();
        mAdapter.addData(data.getReviews());
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestMoreData();
    }
}
