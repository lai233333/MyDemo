package com.xiaolai.lapp.http;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.xiaolai.lapp.LAppApplication;
import com.xiaolai.lapp.bean.FrontPageBean;
import com.xiaolai.lapp.bean.GankIoDataBean;
import com.xiaolai.lapp.bean.GankIoDayBean;
import com.xiaolai.lapp.bean.GankItemBean;
import com.xiaolai.lapp.bean.HotMovieBean;
import com.xiaolai.lapp.bean.MovieCommentBean;
import com.xiaolai.lapp.bean.MovieDetailBean;
import com.xiaolai.lapp.bean.MovieItemBean;
import com.xiaolai.lapp.bean.MoviePhotoBean;
import com.xiaolai.lapp.bean.MovieReviewBean;
import com.xiaolai.lapp.http.api.IDongTingApi;
import com.xiaolai.lapp.http.api.IDouBanApi;
import com.xiaolai.lapp.http.api.IGankApi;
import com.xiaolai.lapp.utils.RxUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by laizhibin on 2017/3/21.
 */
public class RetrofitHttpClient {

    private final static String API_GANKIO = "http://gank.io/api/";
    private final static String API_DOUBAN = "https://api.douban.com/v2/movie/";
    private final static String API_DONGTING = "http://api.dongting.com/";

    private static IGankApi gankService;
    private static IDouBanApi doubanService;
    private static IDongTingApi dongTingService;
    // 递增页码
    private static final int INCREASE_PAGE = 20;

    private RetrofitHttpClient(){
        throw new AssertionError();
    }

    public static void init(){
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(LAppApplication.getContext().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .addInterceptor(sLoggingInterceptor)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(API_GANKIO)
                .build();
        gankService = retrofit.create(IGankApi.class);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(API_DOUBAN)
                .build();
        doubanService = retrofit.create(IDouBanApi.class);
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(API_DONGTING)
                .build();
        dongTingService = retrofit.create(IDongTingApi.class);
    }

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo(requestBuffer);
            } else {
                Logger.e("request.body() == null");
            }
            //打印url信息
            Logger.e(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
            final Response response = chain.proceed(request);

            return response;
        }
    };

    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    //首页轮播图
    public static Observable<FrontPageBean> getFrontpage(){
        return dongTingService.getFrontpage().compose(RxUtil.<FrontPageBean>getBaseSchedulerTransform());
    }

    //分类数据
    public static Observable<List<GankItemBean>> getGankIoData(String id, int page){
        return gankService.getGankIoData(id, page, INCREASE_PAGE).compose(RxUtil.<GankIoDataBean>getBaseSchedulerTransform())
                .flatMap(new Func1<GankIoDataBean, Observable<List<GankItemBean>>>() {
                    @Override
                    public Observable<List<GankItemBean>> call(GankIoDataBean gankIoDataBean) {
                        return Observable.just(gankIoDataBean.getResults());
                    }
                });
    }

    //每日数据
    public static Observable<GankIoDayBean> getGankIoDay(){
        Calendar calendar = Calendar.getInstance();
        return gankService.getGankIoDay(String.valueOf(calendar.get(Calendar.YEAR)),String.valueOf(calendar.get(Calendar.MONTH)),String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)))
                .compose(RxUtil.<GankIoDayBean>getBaseSchedulerTransform());
    }

    public static final String API_KEY = "0b2bdeda43b5688921839c8ecb20399b";

    //豆瓣热映电影，每日更新
    public static Observable<HotMovieBean> getHotMovie(int start){
        return doubanService.getHotMovie(API_KEY,"上海",start, 50,"","").compose(RxUtil.<HotMovieBean>getBaseSchedulerTransform());
    }

    //获取电影详情
    public static Observable<MovieDetailBean> getMovieDetail(String id){
        return doubanService.getMovieDetail(id,API_KEY,"上海","","").compose(RxUtil.<MovieDetailBean>getBaseSchedulerTransform());
    }

    //获取豆瓣电影top250
    public static Observable<List<MovieItemBean>> getMovieTop250(int start){
        return doubanService.getMovieTop250(start,50).compose(RxUtil.<HotMovieBean>getBaseSchedulerTransform())
                .flatMap(new Func1<HotMovieBean, Observable<List<MovieItemBean>>>() {
                    @Override
                    public Observable<List<MovieItemBean>> call(HotMovieBean hotMovieBean) {
                        return Observable.just(hotMovieBean.getSubjects());
                    }
                });
    }

    //获取电影短评
    public static Observable<MovieCommentBean> getMovieComments(String id, int start){
        return doubanService.getMovieComments(id, API_KEY,start, INCREASE_PAGE,"","")
                .compose(RxUtil.<MovieCommentBean>getBaseSchedulerTransform());
    }
    //获取影评
    public static Observable<MovieReviewBean> getMovieReviews(String id, int start){
        return doubanService.getMovieReviews(id, API_KEY,start, INCREASE_PAGE,"","")
                .compose(RxUtil.<MovieReviewBean>getBaseSchedulerTransform());
    }

    //获取电影剧照
    public static Observable<MoviePhotoBean> getMoviePhoto(String id,int start){
        return doubanService.getMoviePhoto(id, API_KEY,start, 1000,"","")
                .compose(RxUtil.<MoviePhotoBean>getBaseSchedulerTransform());
    }

}
