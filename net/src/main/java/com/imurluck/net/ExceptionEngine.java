package com.imurluck.net;


import com.google.gson.JsonParseException;
import org.json.JSONException;
import retrofit2.HttpException;
import retrofit2.Response;

import java.net.ConnectException;
import java.text.ParseException;


/**
 * 处理网络请求失败的情况
 * @author imurluck
 * @time 18-9-22 下午3:45
 */

public class ExceptionEngine {

    private static final int SERVE_BUSY = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int CONFLICT = 409;

    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleResponse(Response response) {
        if (isOnError(response.code())) {
            ApiException apiException = new ApiException(new HttpException(response), response.code());
            apiException.message = "服务器出现错误";
            return apiException;
        }
        return null;
    }

    public static boolean isOnError(int code) {
        return code >= 400 && code < 600;
    }

    public static ApiException handleException(Throwable e) {
        ApiException ae;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ae = new ApiException(e, ((HttpException) e).code());
            switch (httpException.code()) {
                case CONFLICT:
                    break;
                case SERVE_BUSY:
                case INTERNAL_SERVER_ERROR:
                    ae.message = "服务器出现错误";
                    break;
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ae.message = "网络错误";
                    break;
            }
            return ae;
        } else if (e instanceof ServerException) {
            ServerException serverException = (ServerException) e;
            ae = new ApiException(serverException, serverException.code);
            ae.message = serverException.message;
            return ae;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ae = new ApiException(e, ERROR.PARSE_ERROR);
            ae.message = "解析错误";
            return ae;
        } else if (e instanceof ConnectException) {
            ae = new ApiException(e, ERROR.NETWORK_ERROR);
            ae.message = "连接失败";
            return ae;
        } else {
            ae = new ApiException(e, ERROR.UNKNOW);
            ae.message = "未知错误";
            return ae;
        }
    }
    /**
     * 存储失败类型
     * @author imurluck
     * @time 18-9-22 下午4:10
     */
    
    public class ERROR {
        //未知错误
        public static final int UNKNOW = 1000;
        //解析错误
        public static final int PARSE_ERROR = 1001;
        //网络错误
        public static final int NETWORK_ERROR = 1002;
        //协议错误
        public static final int HTTP_ERROR = 1003;
    }
}
