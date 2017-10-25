package me.crazystone.study.okhttp;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by crazy_stone on 17-10-24.
 */

public class StringCallback extends NetCallBack<String> {
    @Override
    protected String parseResponse(int statusCode, ResponseBody responseBody, String url) {
        String jsonString = "";
        try {
            jsonString = responseBody.string();
            Response response;
//            response.request().url();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
