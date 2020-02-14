package com.imurluck.net;

import android.text.TextUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    private static final String MULTUPART_FORM_DATA_TYPE = "multipart/form-data";

    /**
     * 创建单行表单请求体
     * @param value
     * @return
     */
    public static final RequestBody createSingleBody(String value) {
        return RequestBody.create(MediaType.parse(MULTUPART_FORM_DATA_TYPE), value);
    }

    /**
     * 创建表单多行请求体
     * @param params
     * @return
     */
    public static final Map<String, RequestBody> createMapBody(Map<String, String> params) {
        Map<String, RequestBody> resultMap = new HashMap<>();
        for (String key : params.keySet()) {
            resultMap.put(key, createSingleBody(params.get(key)));
        }
        return resultMap;
    }

    /**
     * 创建表单单行文件请求体
     * @param partName
     * @param targetFilePath
     * @return
     */
    public static MultipartBody.Part createFilePart(String partName, String targetFilePath) {
        File targetFile = new File(targetFilePath);
        if (!targetFile.exists()) {
            return null;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse(MULTUPART_FORM_DATA_TYPE),
                targetFile);
        return MultipartBody.Part.createFormData(partName, targetFile.getName(), requestBody);
    }

    /**
     * 创建表单多行参数，单行多文件请求体
     * @param params
     * @param partName
     * @param pics
     * @return
     */
    public static MultipartBody createMultipartBody(Map<String, String> params, String partName, String... pics) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String key : params.keySet()) {
            builder.addFormDataPart(key, params.get(key));
        }
        for (int i = 0; i < pics.length; i++) {
            File picFile = new File(pics[i]);
            if (picFile.exists()) {
                builder.addFormDataPart(partName, picFile.getName(), RequestBody.create(MediaType
                    .parse("image/*"), picFile));
            }
        }
        return builder.build();
    }


    /**
     * 包裹form-data请求参数
     * 有为空时的提示语
     * 数组长度需相等且内容一一对应
     * @param paraKeys 参数键名
     * @param values
     * @return 有空的参数时发出Toast提示返回null，否则返回正常包装后的参数键值对
     */
    public static Map<String, String> wrapParams(String[] paraKeys, Object... values) {
        Map<String, String> paraMap = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            paraMap.put(paraKeys[i], String.valueOf(values[i]));
        }
        return paraMap;
    }


    public static final String checkParams(String[] messages, Object... values) {
        if (messages != null) {
            for (int i = 0; i < messages.length; i++) {
                if (TextUtils.isEmpty(String.valueOf(values[i]))) {
                    return messages[i];
                }
            }
        }
        return null;
    }
}
