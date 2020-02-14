package com.imurluck.net;

public interface NetCallback {

    void onSuccess(String responseString, String tag);

    void onFailed(Throwable t, String tag);
}
