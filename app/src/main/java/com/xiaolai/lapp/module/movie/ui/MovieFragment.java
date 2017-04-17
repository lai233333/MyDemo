package com.xiaolai.lapp.module.movie.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseFragment;
import com.xiaolai.lapp.base.IBasePresenter;
import com.xiaolai.lapp.bean.MovieItemBean;
import com.xiaolai.lapp.bean.MoviePersonBean;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.module.movie.presenter.MoviePresenterImpl;
import com.xiaolai.lapp.module.movie.view.IMovieView;
import com.xiaolai.lapp.utils.ImageLoadUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by laizhibin on 2017/3/27.
 */
public class MovieFragment extends BaseFragment<IBasePresenter> implements IMovieView, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.rv_movie_list)
    RecyclerView mRvMovie;

    View mHeaderView;

    BaseQuickAdapter mAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void initView() {
        mPresenter = new MoviePresenterImpl(this,0);
        initToolBar(mToolbar, "千钟粟");
        mAdapter = new BaseQuickAdapter<MovieItemBean, BaseViewHolder>(R.layout.item_movie_list){

            @Override
            protected void convert(BaseViewHolder helper, MovieItemBean item) {
                ImageLoadUtil.loadFitCenter(item.getImages().getLarge(), (ImageView) helper.getView(R.id.iv_movie_icon));

                helper.setRating(R.id.rb_movie_rating, (float) (item.getRating().getAverage()/2.0));
                helper.setText(R.id.tv_movie_rating, item.getRating().getAverage()+"");
                helper.setText(R.id.tv_movie_title, item.getTitle());
                helper.setText(R.id.tv_movie_director, "导演："+formatName(item.getDirectors()));
                helper.setText(R.id.tv_movie_actor, "主演："+formatName(item.getCasts()));
                //helper.setText(R.id.tv_movie_type,"类型："+formatGenres(item.getGenres()));
                helper.setText(R.id.tv_movie_collect_count,+item.getCollect_count()+"人看过");
            }
        };
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        //mAdapter.setEnableLoadMore(true);
        //mAdapter.setOnLoadMoreListener(this, mRvMovie);
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
        mHeaderView = View.inflate(getActivity(), R.layout.header_hot_movie, null);
        ImageLoadUtil.displayRound((ImageView) mHeaderView.findViewById(R.id.iv_movie_top), R.drawable.ic_top_movie);
        mHeaderView.findViewById(R.id.rl_top_movie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieTopActivity.launchActivity(getActivity());
            }
        });
        mAdapter.addHeaderView(mHeaderView);
        isFirstIn = false;
    }

    @Override
    public void loadMoreData(List<MovieItemBean> data) {
        mAdapter.addData(data);
        mAdapter.loadMoreComplete();
    }

    @Override
    public int getType() {
        return Constant.MOVIE_TYPE_HOT;
    }

    public static String formatName(List<MoviePersonBean> casts) {
        if (casts != null && casts.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < casts.size(); i++) {
                if (i < casts.size() - 1) {
                    stringBuilder.append(casts.get(i).getName()).append(" / ");
                } else {
                    stringBuilder.append(casts.get(i).getName());
                }
            }
            return stringBuilder.toString();

        } else {
            return "佚名";
        }
    }

    /**
     * 格式化电影类型
     */
    public  static String formatGenres(List<String> genres) {
        if (genres != null && genres.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < genres.size(); i++) {
                if (i < genres.size() - 1) {
                    stringBuilder.append(genres.get(i)).append(" / ");
                } else {
                    stringBuilder.append(genres.get(i));
                }
            }
            return stringBuilder.toString();

        } else {
            return "不知名类型";
        }
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestMoreData();
    }
}
