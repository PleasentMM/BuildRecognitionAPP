package com.imurluck.net;

import androidx.annotation.NonNull;

/**
 * 用于ViewModel向Responsity请求数据的回调
 * @author imurluck
 * @param <D>
 */

public interface LoadCallback<D> {

    /**
     * 此方法表示请求成功
     * @param data
     */
    void loadSuccess(@NonNull D data);

    /**
     * 此方法表示请求失败，这里失败指出现了错误，比如HTTPException，服务器没有相应等
     * @param errorMessage
     */
    void loadFailed(@NonNull String errorMessage);


    /**
     * 表示获取数据成功，但是内容实体为null，比如服务器请求数据成功，但是查询到的数据为空，
     * 即{@link com.imurluck.net.Response#data} 为null
     * @param message
     */
    void loadNull(@NonNull String message);
}
