package com.imurluck.net;

import android.util.Log;
import com.imurluck.net.common.Constant;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RetrofitProcessor implements IHttpProcessor {

    private static final String TAG = "RetrofitProcessor";

    private String BASE_URL = Constant.BASE_URL;

    private HttpLoggingInterceptor loggingInterceptor;

    private OkHttpClient client;

    public RetrofitProcessor() {
        loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e(TAG, message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        client = builder.connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Override
    public void post(String url, Map<String, String> paraMap, final NetCallback handleCallback, final String tag, MultipartBody.Part... files) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
//                .addConverterFactory(GsonConverterFactory.style())
                .build();
        retrofit.create(Api.class)
                .post(url, HttpUtil.createMapBody(paraMap), files)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        if (response.body() == null) {
                            onFailure(call, new HttpException(response));
                            return ;
                        }
                        try {
                            handleCallback.onSuccess(response.body().string(), tag);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        handleCallback.onFailed(t, tag);
                    }
                });
    }

    @Override
    public void get(String baseUrl, String url, final NetCallback handleCallback, final String tag) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .build();
        retrofit.create(Api.class)
                .get(url)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() == null) {
                            onFailure(call, new HttpException(response));
                            return;
                        }
                        try {
                            handleCallback.onSuccess(response.body().string(), tag);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        handleCallback.onFailed(t, tag);
                    }
                });
    }
}
