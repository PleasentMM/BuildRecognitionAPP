package com.imurluck.net;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imurluck.net.common.Logger;
import okhttp3.MultipartBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 设计的不太优雅，以[Response]类作为返回实体基类与此App的耦合度太高，
 * 导致使用外置接口时不好处理，后续优化可以考虑使用Builder模式单个拼凑单个Request
 * style by zzx
 * style at 19-4-30
 */
public class HttpHelper {

    private static final String TAG = "HttpHelper";

    private static volatile HttpHelper INSTANCE;

    private IHttpProcessor processor;

    private CopyOnWriteArraySet<String> requestTags;

    private String REPEATEABLE_TAG = "REPEATEABLE_TAG";

    private Gson gson;

    private HttpHelper() {
        requestTags = new CopyOnWriteArraySet<>();
        gson = new GsonBuilder().create();
    }

    public void init(IHttpProcessor processor) {
        this.processor = processor;
    }

    public static HttpHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpHelper();
                }
            }
        }
        return INSTANCE;
    }

    public <D> void post(String url, Map<String, String> paraMap, LoadCallback<D> loadCallback, MultipartBody.Part... files) {
        post(url, paraMap, loadCallback, REPEATEABLE_TAG, files);
    }


    public <D> void post(String url, Map<String, String> paraMap, final LoadCallback<D> loadCallback, String tag, MultipartBody.Part... files) {
        if (requestTags.contains(tag) && !TextUtils.equals(tag, REPEATEABLE_TAG)) {
            Logger.e(TAG, "post: return");
            return ;
        }
        requestTags.add(tag);
        processor.post(url, paraMap, new NetCallback() {
            @Override
            public void onSuccess(String responseString, String tag) {
                try {
                    Type[] superTypes = loadCallback.getClass().getGenericInterfaces();
                    Type[] types = ((ParameterizedType) superTypes[0]).getActualTypeArguments();
                    Response<D> response = gson.fromJson(responseString, new ResponseParameterizedType(Response.class, types[0]));
                    if (response.data != null) {
                        loadCallback.loadSuccess(response.data);
                    } else {
                        loadCallback.loadNull(response.message);
                    }
                } catch (Exception e) {
                    Logger.e(TAG, e.getMessage());
                } finally {
                    removeTag(tag);
                }
            }

            @Override
            public void onFailed(Throwable t, String tag) {
                loadCallback.loadFailed(ExceptionEngine.handleException(t).message);
                removeTag(tag);
            }
        }, tag, files);
    }

    public <D> void get(String baseUrl, String url, final LoadCallback<D> loadCallback, String tag) {
        if (requestTags.contains(tag) && !TextUtils.equals(tag, REPEATEABLE_TAG)) {
            Logger.e(TAG, "post: return");
            return ;
        }
        requestTags.add(tag);
        processor.get(baseUrl, url, new NetCallback() {
            @Override
            public void onSuccess(String responseString, String tag) {
                Type[] superTypes = loadCallback.getClass().getGenericInterfaces();
                Type[] types = ((ParameterizedType) superTypes[0]).getActualTypeArguments();
                D result = gson.fromJson(responseString, new CommonParameterizedType(types[0]));
                if (result != null) {
                    loadCallback.loadSuccess(result);
                } else {
                    loadCallback.loadNull(null);
                }
                removeTag(tag);
            }

            @Override
            public void onFailed(Throwable t, String tag) {
                loadCallback.loadFailed(ExceptionEngine.handleException(t).message);
                removeTag(tag);
            }
        }, tag);
    }

    private void removeTag(String tag) {
        requestTags.remove(tag);
    }

    private static class ResponseParameterizedType implements ParameterizedType {

        private Type rawType;
        private Type genericType;

        public ResponseParameterizedType(Type rawType, Type genericType) {
            this.rawType = rawType;
            this.genericType = genericType;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{genericType};
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    private static class CommonParameterizedType implements ParameterizedType {

        private Type rawType;

        public CommonParameterizedType(Type rawType) {
            this.rawType = rawType;
        }

        @NonNull
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[0];
        }

        @NonNull
        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
