package me.crazystone.study.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by crazy_stone on 17-10-23.
 */

public class OKHttps {

    private static final String TAG = OKHttps.class.getSimpleName();
    private static final int DEFAULT_CONNECT_TIMEOUT = 10, DEFAULT_WRITE_TIMEOUT = 10, DEFAULT_READ_TIMEOUT = 10;
    private static volatile OKHttps instance;
    Request.Builder requestBuilder;
    private OkHttpClient client;
    private Handler mHandler;
    private Callback callBack;
    private int method = Method.GET;

    private OKHttps() {
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .cookieJar(new StoreCookie())
//                .addInterceptor()
                .build();
    }

    public static OKHttps getInstance() {
        if (instance == null) {
            synchronized (OKHttps.class) {
                if (instance == null) {
                    instance = new OKHttps();
                }
            }
        }
        return instance;
    }

    public OKHttps get(String url, OKHttpParams params) {
        this.method = Method.GET;
        requestBuilder = new Request.Builder()
                .url(parseGetParams(url, params))
                .get();
        addHeaders(params);
        connect();
        return this;
    }

    public void addHeaders(OKHttpParams params) {
        if (params != null && requestBuilder != null && !params.isHeaderEmpty()) {
//            Map<String, String> headerMap = params.getHeaderMap();
//            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
//                requestBuilder.addHeaders(entry.getKey(), entry.getValue());
//            }
            requestBuilder.headers(params.getHeaders());
        }
    }

    private HttpUrl parseGetParams(String url, OKHttpParams params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url).append((params != null && !params.isParamEmpty()) ? "?" : "");
        if (params != null && !params.isParamEmpty()) {
            Map<String, OKHttpParams.ParamValue> map = params.getParams();
            for (Map.Entry<String, OKHttpParams.ParamValue> entry : map.entrySet()) {
                String key = entry.getKey();
                OKHttpParams.ParamValue value = entry.getValue();
                value.toGetParam(key, sb);
            }
        }
        Log.d(TAG, "real url:" + sb.toString());
        return HttpUrl.parse(sb.toString());
    }

    private void connect() {
        client.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d(TAG, "res:" + res);
            }
        });
    }


    public OKHttps get(String url) {
        get(url, null);
        return this;
    }

    public OKHttps post(String url, OKHttpParams params) {
        method = Method.POST;
        requestBuilder = new Request.Builder()
                .url(url)
                .post(parsePostParams(params));
        addHeaders(params);
        connect();
        return this;
    }

    public RequestBody parsePostParams(OKHttpParams params) {
        if (params == null) return RequestBody.create(MediaType.parse("no"), "");
        if (params.isFileParamEmpty()) {
            return getMultiPartBody(params);
        } else {
            return getFormBody(params);
        }
    }

    public RequestBody getFormBody(OKHttpParams params) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params.isParamEmpty()) {
            for (Map.Entry<String, OKHttpParams.ParamValue> entry : params.getParams().entrySet()) {
                String key = entry.getKey();
                OKHttpParams.ParamValue value = entry.getValue();
                value.toFormParam(key, formBuilder);
            }
        }
        return formBuilder.build();
    }

    public RequestBody getMultiPartBody(OKHttpParams params) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        for (Map.Entry<String, List<OKHttpParams.FileWrapper>> entry : params.getFileMap().entrySet()) {
            String key = entry.getKey();
            List<OKHttpParams.FileWrapper> list = entry.getValue();
            if (list != null && list.size() > 0) {
                for (OKHttpParams.FileWrapper fileWrapper : list) {
                    fileWrapper.addFile(key, multipartBodyBuilder);
                }
            }
        }
        if (!params.isParamEmpty()) {
            for (Map.Entry<String, OKHttpParams.ParamValue> entry : params.getParams().entrySet()) {
                String key = entry.getKey();
                OKHttpParams.ParamValue value = entry.getValue();
                value.toMultiParam(key, multipartBodyBuilder);
            }
        }
        return multipartBodyBuilder.build();
    }


    public void listen(NetCallBack callBack) {
        this.callBack = callBack;
    }


    public Handler getHandler() {
        return mHandler != null ? mHandler : new Handler(Looper.myLooper());
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    //cookie persistence storage
    private static class StoreCookie implements CookieJar {

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            Log.d(TAG, printUrlCookie(url, cookies));
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            return null;
        }

        private String printUrlCookie(HttpUrl httpUrl, List<Cookie> cookies) {
            StringBuilder sb = new StringBuilder();
            sb.append("url:").append(httpUrl.url().toString()).append(";");
            for (int i = 0; i < cookies.size(); i++) {
                if (i != 0) {
                    sb.append(";");
                }
                sb.append("key:").append(cookies.get(i).name()).append(",value:").append(cookies.get(i).value());
            }
            return sb.toString();
        }
    }

    private class RequestParam {
        private String value;
        private Collection values;

        public String getValue() {
            return value;
        }

        public RequestParam setValue(String value) {
            this.value = value;
            return this;
        }

        public Collection getValues() {
            return values;
        }

        public RequestParam setValues(Collection values) {
            this.values = values;
            return this;
        }
    }


}
