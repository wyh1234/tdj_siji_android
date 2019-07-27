package com.tdj_sj_webandroid.http;

import android.app.ProgressDialog;
import android.content.Context;
import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.tdj_sj_webandroid.AppAplication;
import com.tdj_sj_webandroid.utils.Constants;
import com.tdj_sj_webandroid.utils.GeneralUtils;
import com.tdj_sj_webandroid.utils.ToastUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.request.GetRequest;
import com.zhouyou.http.request.PostRequest;
import com.zhouyou.http.utils.HttpLog;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.Map;


public class HttpUtils {

    /**
     * 设置请求头
     *
     * @param
     * @return
     */
    private static HttpHeaders SetHeaders() {
        return !GeneralUtils.isNullOrZeroLenght(GeneralUtils.getToken(AppAplication.getAppContext())) ? new HttpHeaders("token", GeneralUtils.getToken(AppAplication.getAppContext())): new HttpHeaders("token", "");
    }

    /**
     * get
     */
    public static void onGet(final Context context, Map<String, String> params, String url, final IResponse response) {
        GeneralUtils.isNetworkAvailables(context);
        GetRequest getRequest = EasyHttp.get(url);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                getRequest.params(entry.getKey(), entry.getValue());
            }
        }
        getRequest.addInterceptor(new TokenInterceptor(context)).headers( SetHeaders()).execute(new ProgressDialogCallBack<String>(new MyIProgressDialog(context), true, true) {
            @Override
            public void onSuccess(String t) {
                LogUtils.i(t.toString());
                IResponseType(response, t);
            }

            @Override
            public void onError(ApiException e) {
                super.onError(e);
                ToastUtils.showToast(context, e.getMessage());
                response.onError(e);
//                ApiExceptionManage.handleException(e,context);
            }
        });
    }


    /**
     * post
     */
    public static void onPost(final Context context, Map<String, String> params, String url, final IResponse response) {
        PostRequest postRequest = EasyHttp.post(url);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                postRequest.params(entry.getKey(), entry.getValue());
            }
        }
//        Disposable dis = postRequest.addInterceptor(new TokenInterceptor(context)).headers(SetHeaders()).
        postRequest.addInterceptor(new TokenInterceptor(context)).headers(SetHeaders()).execute(new ProgressDialogCallBack<String>(new MyIProgressDialog(context), true, true) {
            @Override
            public void onSuccess(String t) {
                LogUtils.i(t.toString());
                IResponseType(response, t);
            }

            @Override
            public void onError(ApiException e) {
                super.onError(e);
                ToastUtils.showToast(context, e.getMessage());

            }
        });
    }

    /**
     * 上传单张图片
     */
    public static void onUploadOne(final Context context, File file, String url, final IResponse response) {
        PostRequest postRequest = EasyHttp.post(url).baseUrl(Constants.URL).headers(SetHeaders());
        postRequest.params("file", file, null);
        postRequest. execute(new ProgressDialogCallBack<String>(new MyIProgressDialog(context), true, true) {
                    @Override
                    public void onSuccess(String t) {
                        LogUtils.i(t.toString());
                        IResponseType(response, t);
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        ToastUtils.showToast(context, e.getMessage());

                    }
                });
    }

    /**
     * 上传图片带进度<可传多张>
     */
    public void onUpload(final Context context, File file, String url, final IResponse response) {
        EasyHttp.post(url).params("img", file, new UpdaterIProgressDialog(context)).
                execute(new ProgressDialogCallBack<String>(new MyIProgressDialog(context), true, true) {
                    @Override
                    public void onSuccess(String t) {
                        IResponseType(response, t);
                        ;
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        ToastUtils.showToast(context, e.getMessage());

                    }
                });
    }

    /**
     * 下载文件
     */
    public void onDownloadFile2(final Context context, String url, String path, String name) {
        final ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
        dialog.setMessage("正在下载...");
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        dialog.setTitle("下载文件");
        dialog.setMax(100);
        EasyHttp.downLoad(url)
                .savePath(path)
                .saveName(name)
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        dialog.setProgress(progress);
                        if (done) {
                            dialog.setMessage("下载完成");
                        }
                    }

                    @Override
                    public void onStart() {
                        dialog.show();
                    }

                    @Override
                    public void onComplete(String path) {
                        ToastUtils.showToast(context, "文件保存路径：" + path);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showToast(context, e.getMessage());
                        dialog.dismiss();
                    }
                });
    }

    //嵌套请求
/*    public static void onNestedRequest(final Context context, final String url) {
        //例如：先登录获取token后，再去请求另一个接口,在访问另一个接口
        Observable<LoginInfo> login = EasyHttp.post(url)
                .params("mobile", "15002728051")
                .params("password", "123456")
//                .params("password", MD5.encrypt4login("123456", "afd55f877bad4aaeab45fb4ca567d234"))
                .execute(LoginInfo.class);
        login.flatMap(new Function<LoginInfo, ObservableSource<BannerInfo>>() {
            @Override
            public ObservableSource<BannerInfo> apply(@NonNull LoginInfo authModel) throws Exception {
                return EasyHttp.get("banner/index")
                        .params("type", "1")
                        .params("c", "2")
//                .params("password", MD5.encrypt4login("123456", "afd55f877bad4aaeab45fb4ca567d234"))
                        .execute(BannerInfo.class);
            }
        }).subscribe(new ProgressSubscriber<BannerInfo>(context, new MyIProgressDialog(context)) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);
                ToastUtils.showToast(context, e.getCode() + "");
            }

            @Override
            public void onNext(BannerInfo skinTestResult) {
                LogUtils.e("第2次");
                // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
            }
        });
    }*/

    //采用merge合并请求
 /*   public static void onMergeRequest(final Context context, final String url) {
        Observable<LoginInfo> mobileObservable = EasyHttp.post(url)
                .params("mobile", "15002728051")
                .params("password", "123456")
                .execute(new CallClazzProxy<ApiResult<LoginInfo>, LoginInfo>(LoginInfo.class) {
                });

        Observable<BannerInfo> searchObservable = EasyHttp.get("banner/index")
                .params("type", "1")
                .params("c", "2")
                //采用代理
                .execute(new CallClazzProxy<ApiResult<BannerInfo>, BannerInfo>(BannerInfo.class) {
                });
        //merge和mergeDelayError都是合并，但是需要注意二者区别。
        //merge：合并的请求，如果有一个接口报错了，就立马报错，会终止整个流，另外的接口也不会请求。
        //mergeDelayError：合并的请求，如果有一个接口报错了，会延迟错误处理，后面的接口会继续执行没有被中断。
        //使用时根据需要选用merge/mergeDelayError
        //Observable.merge(searchObservable,mobileObservable).subscribe(new BaseSubscriber<Object>() {
        Observable.mergeDelayError(searchObservable, mobileObservable).subscribe(new BaseSubscriber<Object>() {
            @Override
            public void onError(ApiException e) {
                ToastUtils.showToast(context, e.getMessage());
            }

            @Override
            public void onNext(@NonNull Object object) {
                //为什么用Object接收，因为两个接口请求返回的数据类型不是一样的，如果是一样的就用具体的对象接收就可以了，
                // 不再需要instanceof麻烦的判断
                if (object instanceof LoginInfo) {//mobileObservable 返回的结果
                    ToastUtils.showToast(context, "LoginInfo");
                } else if (object instanceof BannerInfo) {
                    ToastUtils.showToast(context, "BannerInfo");
                }

            }
        });

    }*/

    //与view结合防止按钮重复点击-点击防抖
    /*public void onRepeatRquest(View view, final Context context, final String url) {
        RxView.clicks(view).throttleFirst(1, TimeUnit.SECONDS).flatMap(new Function<Object, ObservableSource<LoginInfo>>() {
            @Override
            public ObservableSource<LoginInfo> apply(@NonNull Object o) throws Exception {
                return EasyHttp.get(url)
                        .execute(new CallClazzProxy<ApiResult<LoginInfo>, LoginInfo>(LoginInfo.class) {
                        });
            }
        }).subscribe(new BaseSubscriber<LoginInfo>() {
            @Override
            public void onError(ApiException e) {
                ToastUtils.showToast(context, e.getMessage());
            }

            @Override
            public void onNext(@NonNull LoginInfo resultBean) {
                ToastUtils.showToast(context, resultBean.toString());
            }
        });
    }*/

/*    public void search(EditText mEditText, final Context context, final String url) {
        //500毫秒才让它去请求一次网络，这样可以初步的避免数据混乱，也优了app性能
        //可以使用debounce减少频繁的网络请求。避免每输入（删除）一个字就做一次请求
        Disposable mDisposable = RxTextView.textChangeEvents(mEditText)
                .debounce(500, TimeUnit.MILLISECONDS).filter(new Predicate<TextViewTextChangeEvent>() {
                    @Override
                    public boolean test(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        // 过滤，把输入字符串长度为0时过滤掉
                        String key = textViewTextChangeEvent.text().toString();
                        //这里可以对key进行过滤的判断逻辑
                        return key.trim().length() > 0;
                    }
                }).flatMap(new Function<TextViewTextChangeEvent, ObservableSource<LoginInfo>>() {
                    @Override
                    public ObservableSource<LoginInfo> apply(@NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        String key = textViewTextChangeEvent.text().toString();
                        Log.d("test", String.format("Searching for: %s", textViewTextChangeEvent.text().toString()));
                        return EasyHttp.get(url)
                                //采用代理
                                .execute(new CallClazzProxy<ApiResult<LoginInfo>, LoginInfo>(LoginInfo.class) {
                                });
                    }
                }).subscribeWith(new BaseSubscriber<LoginInfo>() {
                    @Override
                    protected void onStart() {
                    }

                    @Override
                    public void onError(ApiException e) {
                        ToastUtils.showToast(context, e.getMessage());
                    }

                    @Override
                    public void onNext(@NonNull LoginInfo content) {
                        ToastUtils.showToast(context, content.toString());
                    }
                });
    }*/

    public static void IResponseType(IResponse response, String resString) {
        if (response instanceof GsonResponseHandler) {    //gson回调（可以得到gson数据）；
            try {
                JSONObject jsonBody = new JSONObject(resString);
                Gson gson = new Gson();
                ((GsonResponseHandler) response).onSuccess(
                        gson.fromJson(resString, ((GsonResponseHandler) response).getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (response instanceof JsonResponseHandler) {       //json回调(可以得到json数据)
            try {
                JSONObject jsonBody = new JSONObject(resString);
                ((JsonResponseHandler) response).onSuccess(jsonBody);
            } catch (JSONException e) {
                LogUtils.e("onResponse fail parse jsonobject, body=" + resString);
            }
        }
    }
}
