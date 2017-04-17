package com.xiaolai.lapp.module.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.base.BaseActivity;
import com.xiaolai.lapp.module.home.MainActivity;
import com.xiaolai.lapp.utils.RxUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by laizhibin on 2017/3/19.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_splash_count)
    TextView tvCount;

    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    boolean isJump = false;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.tv_splash_count)
    public void onClick() {
        jump();
    }

    @Override
    protected void requestData(boolean isRefresh) {
        RxUtil.countdown(10)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        animateImage();
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        jump();
                    }

                    @Override
                    public void onError(Throwable e) {
                        jump();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        tvCount.setText("跳过 " + integer+"s");
                    }
                });
    }

    private void jump(){
        if(!isJump) {
            isJump = true;
            finish();
            MainActivity.launchActivity(this);
            overridePendingTransition(R.anim.hold, R.anim.zoom_in_exit);
        }
    }

    public void animateImage() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(ivSplash, "scaleX", 1f,
                1.13f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(ivSplash, "scaleY", 1f,
                1.13f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(4000).play(animatorX).with(animatorY);
        set.start();
    }

}
