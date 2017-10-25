package me.crazystone.study.okhttp;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by crazy_stone on 17-10-24.
 */

public abstract class NetCallBack<T> implements Callback {

    private static final String TAG = NetCallBack.class.getSimpleName();
    Gson gson;

    {
        gson = new Gson();
    }

    @Override
    public void onFailure(Call call, IOException e) {
//        String url = call.request().url().url().toString();
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            int statusCode = response.code();
            ResponseBody responseBody = response.body();
            String url = response.request().url().url().toString();
            Headers headers = response.headers();
            printResponseHeader(headers);
            parseResponse(statusCode, responseBody, url);
            if (statusCode >= 500) {
                Log.d(TAG, url + ":服务器内部错误");
            }
        }
    }

    private void printResponseHeader(Headers headers) {
        Set<String> names = headers.names();
        StringBuilder sb = new StringBuilder();
        sb.append("response header:");
        int index = 0;
        for (String name : names) {
            if (index != 0) {
                sb.append(";");
            }
            sb.append(name).append(":").append(headers.get(name));
            index++;
        }
    }


    protected abstract T parseResponse(int statusCode, ResponseBody responseBody, String url);

}
