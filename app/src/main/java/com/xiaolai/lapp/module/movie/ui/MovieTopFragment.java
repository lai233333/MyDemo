package com.xiaolai.lapp.module.movie.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.labelview.LabelView;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.bean.MovieItemBean;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.movie.presenter.MoviePresenterImpl;
import com.xiaolai.lapp.module.movie.view.IMovieView;
import com.xiaolai.lapp.utils.ImageLoadUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by laizhibin on 2017/3/27.
 */
public class MovieTopFragment extends BaseFragment implements IMovieView {

    @BindView(R.id.rv_movie_list)
    RecyclerView mRvMovie;

    private BaseQuickAdapter mAdapter;

    private int start;

    public static MovieTopFragment newInstance(int start) {
        MovieTopFragment fragment = new MovieTopFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("start", start);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            start = getArguments().getInt("start");
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_movie_top;
    }

    @Override
    protected void initView() {
        mPresenter = new MoviePresenterImpl(this,start);
        mAdapter = new BaseQuickAdapter<MovieItemBean, BaseViewHolder>(R.layout.item_movie_top) {
            @Override
            protected void convert(BaseViewHolder helper, MovieItemBean item) {
                ImageLoadUtil.loadFitCenter(item.getImages().getLarge(), (ImageView) helper.getView(R.id.iv_movie_icon));

                helper.setRating(R.id.rb_movie_rating, (float) (item.getRating().getAverage()/2.0));
                helper.setText(R.id.tv_movie_rating, item.getRating().getAverage()+"");
                helper.setText(R.id.tv_movie_title, item.getTitle());
                helper.setText(R.id.tv_movie_director, "导演："+MovieFragment.formatName(item.getDirectors()));
                helper.setText(R.id.tv_movie_actor, "主演："+MovieFragment.formatName(item.getCasts()));
                //helper.setText(R.id.tv_movie_type,"类型："+MovieFragment.formatGenres(item.getGenres()));
                helper.setText(R.id.tv_movie_collect_count,+item.getCollect_count()+"人看过");
                final LabelView labelView = helper.getView(R.id.lv_index);
                labelView.setText(helper.getLayoutPosition()+1+start+"");
            }
        };
        mAdapter.setEnableLoadMore(false);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvMovie.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MovieItemBean item = (MovieItemBean) adapter.getItem(position);
                MovieDetailActivity.launchActivity(getActivity(), view.findViewById(R.id.iv_movie_icon), item.getId(),item.getTitle(),item.getImages().getLarge());
            }
        });
    }

    @Override
    protected void requestData(boolean isRefresh) {
        mPresenter.requestData();
    }

    @Override
    public void loadData(List<MovieItemBean> data) {
        mAdapter.setNewData(data);
        isFirstIn  = false;
    }

    @Override
    public void loadMoreData(List<MovieItemBean> data) {

    }

    @Override
    public int getType() {
        return Constant.MOVIE_TYPE_TOP;
    }
}
