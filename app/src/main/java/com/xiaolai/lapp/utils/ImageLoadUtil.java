package com.xiaolai.lapp.utils;

import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.R;

/**
 * Created by laizhibin on 2017/3/22.
 */
public class ImageLoadUtil {

    public static void loadCenterCrop(String url, ImageView view) {
        Glide.with(view.getContext())
                .load(url)
                .centerCrop()
                .crossFade(500)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .into(view);
    }

    public static void loadFitCenter(String url, ImageView view) {
        Glide.with(view.getContext())
                .load(url)
                .fitCenter()
                .crossFade(500)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
                .into(view);
    }

    public static void loadFitCenter(String url, ImageView view, RequestListener listener) {
        Glide.with(view.getContext()).load(url).fitCenter().crossFade(200)
                .listener(listener).into(view);
    }

    public static void loadCenterCrop(String url, ImageView view, RequestListener listener) {
        Glide.with(view.getContext()).load(url).crossFade(200)
                .listener(listener).into(view);
    }

    public static void displayGif(String url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url)
                .asBitmap()
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_default)
//                .skipMemoryCache(true) //跳过内存缓存
//                .crossFade(1000)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// 缓存图片源文件（解决加载gif内存溢出问题）
//                .into(new GlideDrawableImageViewTarget(imageView, 1));
                .into(imageView);
    }

    /**
     * 加载圆形图
     */
    public static void displayCircle(ImageView imageView, int resId) {
        Glide.with(imageView.getContext())
                .load(resId)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    /**
     * 加载圆形图
     */
    public static void displayCircle(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .into(imageView);
    }

    //加载圆角

    public static void displayRound(ImageView imageView, int resId) {
        Glide.with(imageView.getContext())
                .load(resId)
                .transform(new GlideRoundTransform(imageView.getContext(), 5))
                .into(imageView);
    }

    public static Bitmap blurBitmap(Bitmap bitmap) {

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(LAppApplication.getContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;


    }
}
