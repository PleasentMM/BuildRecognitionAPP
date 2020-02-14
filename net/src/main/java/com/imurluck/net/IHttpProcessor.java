package com.imurluck.net;

import okhttp3.MultipartBody;

import java.util.Map;

public interface IHttpProcessor {

    void post(String url, Map<String, String> paraMap, NetCallback netCallback, String tag, MultipartBody.Part... files);

    void get(String baseUrl, String url, NetCallback callback, String tag);
}
