package com.imurluck.net;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface Api {

    @POST
    @Multipart
    Call<ResponseBody> post(@Url String url, @PartMap Map<String, RequestBody> paraMap,
                            @Part MultipartBody.Part... file);

    @GET
    Call<ResponseBody> get(@Url String url);
}
