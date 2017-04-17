package com.xiaolai.lapp.module.movie.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.bean.MoviePopularReview;
import com.xiaolai.lapp.utils.ImageLoadUtil;
import com.xiaolai.lapp.utils.ShareUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author xiaolai
 * @time 2017/4/3 20:52
 * @des ${TODO}
 */

public class MovieReviewDetailActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.tv_review_title)
    TextView tvTitle;
    @BindView(R.id.iv_author_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_author_name)
    TextView tvName;
    @BindView(R.id.rb_review_rating)
    RatingBar rbRate;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_review_date)
    TextView tvDate;
    @BindView(R.id.tv_review_useful)
    TextView tvUseful;
    @BindView(R.id.tv_review_comment)
    TextView tvComment;

    private MoviePopularReview data;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_movie_review_detail;
    }

    @Override
    protected void initViews() {
        data = (MoviePopularReview) getIntent().getSerializableExtra("MoviePopularReview");
        initToolBar(mToolbar, "影评");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvTitle.setText(data.getTitle());
        ImageLoadUtil.displayCircle(ivIcon, data.getAuthor().getAvatar());
        tvName.setText(data.getAuthor().getName());
        rbRate.setRating((float) data.getRating().getValue());
        tvContent.setText(data.getContent());
        tvDate.setText(data.getUpdated_at());
        tvUseful.setText(data.getUseful_count()+"");
        tvComment.setText(data.getComments_count()+"");
    }

    @Override
    protected void requestData(boolean isRefresh) {

    }

    @OnClick(R.id.ib_share)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ib_share:
                ShareUtil.share(this,"精彩影评："+data.getShare_url());
                break;
        }
    }

    public static void launchActivity(Activity context, MoviePopularReview review){
        Intent intent = new Intent(context, MovieReviewDetailActivity.class);
        intent.putExtra("MoviePopularReview",review);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.fade_exit);
    }
}
