package com.example.root.mynote.core;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by root on 11/6/17.
 */

public final class Api {

    public static final String BASE_URL = "http://192.168.43.97/myNote/api";
    public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public static void post(String endPoint, RequestParams requestParams, TextHttpResponseHandler responseHandler) {
        asyncHttpClient.post(BASE_URL + endPoint, requestParams, responseHandler);
    }
    public static void get(String endPoint, RequestParams requestParams, TextHttpResponseHandler responseHandler) {
        asyncHttpClient.get(BASE_URL + endPoint, requestParams, responseHandler);
    }
    public static void get(String endPoint, TextHttpResponseHandler textHttpResponseHandler) {
        get(endPoint, null, textHttpResponseHandler);
    }
}
