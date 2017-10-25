package me.crazystone.study.okhttp;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by crazy_stone on 17-10-24.
 */

public class JsonCallback<T> extends NetCallBack<T> {

    @Override
    protected T parseResponse(int statusCode, ResponseBody responseBody, String url) {
        try {
            String jsonString = responseBody.string();
//            T t = gson.fromJson(jsonString,);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
