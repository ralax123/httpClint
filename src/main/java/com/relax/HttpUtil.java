package com.relax;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProxySelector;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.relax.HttpMethod.POST;
import static com.relax.HttpMethod.PUT;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * http util
 * @author evan
 */
//@Slf4j
public class HttpUtil {
    /**
     * for normal network
     */
    public final static int CONNECT_TIMEOUT = 2000;
    public final static int SOCKET_TIMEOUT = 15000;
    public final static int CONNECTION_REQUEST_TIMEOUT = 2000;
    public final static String HTML = "html";

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
            .build();

    private static final MediaType JSON_MEDIA_TYPE
            = MediaType.get("application/json; charset=utf-8");

    public final static RequestConfig REQUEST_CONFIG = RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT)
            .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
            .build();

    /**
     * json Content-Type
     */
    public static final Map<String, String> DEFAULT_JSON_HEADERS = new HashMap<>();
    static {
        DEFAULT_JSON_HEADERS.put("Content-Type", "application/json");
    }

    /**
     * get
     * @param baseUrl
     * @return
     */
    public static String get(String baseUrl) {
        return get(baseUrl, null);
    }
    public static String getSSL(String baseUrl) {
        return getSSL(baseUrl, null);
    }

    public static String get(String baseUrl, Map<String, String> params) {
        return get(baseUrl, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }
    public static String getSSL(String baseUrl, Map<String, String> params) {
        return getSSL(baseUrl, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }
    public static String get(String baseUrl, Map<String, String> headers, Map<String, String> params) {
        return get(baseUrl, headers, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }
    public static String getSSL(String baseUrl, Map<String, String> headers, Map<String, String> params) {
        return getSSL(baseUrl, headers, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String get(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout) {
        return get(baseUrl, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }
    public static String getSSL(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout) {
        return getSSL(baseUrl, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }
    public static String get(String baseUrl, Map<String, String> headers, Map<String, String> params,
                             int connectTimeout, int socketTimeout) {
        return get(baseUrl, headers, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }
    public static String getSSL(String baseUrl, Map<String, String> headers, Map<String, String> params,
                                int connectTimeout, int socketTimeout) {
        return getSSL(baseUrl, headers, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }

    /**
     * get
     * @param baseUrl
     * @param params
     * @param connectTimeout milliseconds
     * @param socketTimeout milliseconds
     * @param connectionRequestTimeout milliseconds
     * @return
     */
    public static String get(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout,
                             int connectionRequestTimeout) {
        return get(baseUrl, null, params, connectTimeout, socketTimeout, connectionRequestTimeout);
    }
    public static String getSSL(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout,
                                int connectionRequestTimeout) {
        return getSSL(baseUrl, null, params, connectTimeout, socketTimeout, connectionRequestTimeout);
    }
    public static String get(String baseUrl, Map<String, String> headers, Map<String, String> params,
                             int connectTimeout, int socketTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return handleOkHttpRequest(false, baseUrl, headers, params, HttpMethod.GET);
    }
    public static String getSSL(String baseUrl, Map<String, String> headers, Map<String, String> params,
                                int connectTimeout, int socketTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return handleOkHttpRequest(true, baseUrl, headers, params, HttpMethod.GET);
    }


    /**
     * post
     * @param baseUrl
     * @return
     */
    public static String post(String baseUrl) {
        return post(baseUrl, null);
    }

    public static String postSSL(String baseUrl) {
        return postSSL(baseUrl, null);
    }

    public static String post(String baseUrl, Map<String, String> params) {
        return post(baseUrl, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }
    public static String postSSL(String baseUrl, Map<String, String> params) {
        return postSSL(baseUrl, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }
    public static String post(String baseUrl, Map<String, String> headers, Map<String, String> params) {
        return post(baseUrl, headers, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }
    public static String postSSL(String baseUrl, Map<String, String> headers, Map<String, String> params) {
        return postSSL(baseUrl, headers, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT);
    }

    public static String post(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout) {
        return post(baseUrl, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }
    public static String postSSL(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout) {
        return postSSL(baseUrl, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }
    public static String post(String baseUrl, Map<String, String> headers, Map<String, String> params,
                              int connectTimeout, int socketTimeout) {
        return post(baseUrl, headers, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }
    public static String postSSL(String baseUrl, Map<String, String> headers, Map<String, String> params,
                                 int connectTimeout, int socketTimeout) {
        return postSSL(baseUrl, headers, params,  connectTimeout, socketTimeout, CONNECTION_REQUEST_TIMEOUT);
    }

    /**
     * post
     * @param baseUrl
     * @param params
     * @param connectTimeout milliseconds
     * @param socketTimeout milliseconds
     * @param connectionRequestTimeout milliseconds
     * @return
     */
    public static String post(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout,
                              int connectionRequestTimeout) {
        return post(baseUrl, null, params,  connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static String postSSL(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout,
                                 int connectionRequestTimeout) {
        return postSSL(baseUrl, null, params,  connectTimeout, socketTimeout, connectionRequestTimeout);
    }

    public static String post(String baseUrl, Map<String, String> headers, Map<String, String> params,
                              int connectTimeout, int socketTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return handleOkHttpRequest(false, baseUrl, headers, params, POST);
    }

    public static String postSSL(String baseUrl, Map<String, String> headers, Map<String, String> params,
                                 int connectTimeout, int socketTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return handleOkHttpRequest(true, baseUrl, headers, params, POST);
    }

    /**
     * http post request with map body
     * json format
     *
     * @param baseUrl url
     * @param params request params
     * @return response string
     */
    public static String postJson(String baseUrl, Map<String, Object> params) {
        return postJson(baseUrl, DEFAULT_JSON_HEADERS, params);
    }
    public static String postJsonSSL(String baseUrl, Map<String, Object> params) {
        return postJsonSSL(baseUrl, DEFAULT_JSON_HEADERS, params);
    }
    /**
     * http post request with map body and header
     * json format
     *
     * @param baseUrl url
     * @param headers header
     * @param params request params
     * @return response string
     */
    public static String postJson(String baseUrl, Map<String, String> headers, Map<String, Object> params) {
        return postJson(baseUrl, headers, JSON.toJSONString(params));
    }
    public static String postJsonSSL(String baseUrl, Map<String, String> headers, Map<String, Object> params) {
        return postJsonSSL(baseUrl, headers, JSON.toJSONString(params));
    }
    /**
     * http post request with header and json string body
     * json format
     *
     * @param baseUrl request url
     * @param jsonRequestBody request body, json format
     * @return response string
     */
    public static String postJson(String baseUrl, Map<String, String> headers, String jsonRequestBody) {
        return handleHttpJsonRequest(POST, false, baseUrl, headers, jsonRequestBody, REQUEST_CONFIG, UTF_8, UTF_8);
    }

    public static String postJson(String baseUrl, Map<String, String> headers, String jsonRequestBody,
                                  int connectTimeout, int socketTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return handleHttpJsonRequest(POST, false, baseUrl, headers, jsonRequestBody, requestConfig, UTF_8, UTF_8);
    }

    public static String postJsonSSL(String baseUrl, Map<String, String> headers, String jsonRequestBody) {
        return handleHttpJsonRequest(POST, true, baseUrl, headers, jsonRequestBody, REQUEST_CONFIG, UTF_8, UTF_8);
    }

    public static String postJsonSSL(String baseUrl, Map<String, String> headers, String jsonRequestBody,
                                     int connectTimeout, int socketTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return handleHttpJsonRequest(POST, true, baseUrl, headers, jsonRequestBody, requestConfig, UTF_8, UTF_8);
    }

    /**
     * http put request with map body
     * json format
     *
     * @param baseUrl url
     * @param params request params
     * @return response string
     */
    public static String putJson(String baseUrl, Map<String, Object> params) {
        return putJson(baseUrl, DEFAULT_JSON_HEADERS, JSON.toJSONString(params));
    }

    /**
     * http put request with map body and header
     * json format
     *
     * @param baseUrl url
     * @param headers header
     * @param params request params
     * @return response string
     */
    public static String putJson(String baseUrl, Map<String, String> headers, Map<String, Object> params) {
        return putJson(baseUrl, headers, JSON.toJSONString(params));
    }

    public static String putJson(String baseUrl, Map<String, String> headers, String jsonRequestBody) {
        return handleHttpJsonRequest(PUT, false, baseUrl, headers, jsonRequestBody, REQUEST_CONFIG, UTF_8, UTF_8);
    }

    public static String delete(String baseUrl) {
        return delete(baseUrl, null);
    }

    public static String delete(String baseUrl, Map<String, String> params) {
        return delete(baseUrl, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT, CONNECTION_REQUEST_TIMEOUT);
    }
    public static String delete(String baseUrl, Map<String, String> headers, Map<String, String> params) {
        return delete(baseUrl, headers, params, CONNECT_TIMEOUT, SOCKET_TIMEOUT, CONNECTION_REQUEST_TIMEOUT);
    }

    public static String delete(String baseUrl, Map<String, String> params, int connectTimeout, int socketTimeout,
                                int connectionRequestTimeout) {
        return delete(baseUrl, null, params, connectTimeout, socketTimeout, connectionRequestTimeout);
    }
    public static String delete(String baseUrl, Map<String, String> headers, Map<String, String> params,
                                int connectTimeout, int socketTimeout, int connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
        return handleOkHttpRequest(false, baseUrl, headers, params, HttpMethod.DELETE);
    }


    private static String handleOkHttpRequest(boolean ssl, String baseUrl, Map<String, String> headers,
                                              Map<String, String> params,
                                              HttpMethod method) {


        Headers.Builder builder = new Headers.Builder();
        if(headers != null){
            headers.keySet().forEach(p->{
                builder.add(p, headers.get(p));
            });
        }
        Headers okHttpHeaders = builder.build();
        Request request = null;
        if (method == POST) {
            RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, JSON.toJSONString(params));
            request = new Request.Builder().headers(okHttpHeaders).url(baseUrl).post(body).build();
        } else if (method == HttpMethod.GET) {
            request = new Request.Builder().headers(okHttpHeaders).url(appendRequestUrl(baseUrl, params)).build();
        } else if (method == HttpMethod.DELETE) {
            request = new Request.Builder().headers(okHttpHeaders).url(appendRequestUrl(baseUrl, params)).delete().build();
        }

        //创建/Call
        Call call;
        if(ssl){
            call = okHttpClient.newCall(request);
        }else{
            call = okHttpClient.newCall(request);
        }
        //加入队列 异步操作
        Response response = null;
        try {
            response = call.execute();
            String s = response.body().string();
            if(s.contains(HTML)){
                throw new HttpException("10010", s);
            }
            return s;
        } catch (IOException e) {
            throw new HttpException("", e.getMessage(), e);
        }finally {
            response.body().close();
        }
    }

    /**
     * handleHttpRequest
     * @param ssl
     * @param baseUrl
     * @param headers
     * @param params
     * @param responseCharset
     * @param requestConfig
     * @param method
     * @return
     */
    private static String handleHttpRequest(boolean ssl, String baseUrl, Map<String, String> headers,
                                            Map<String, String> params,
                                            final Charset responseCharset,
                                            RequestConfig requestConfig,
                                            HttpMethod method) {
        // build CloseableHttpClient
        HttpClientBuilder httpClientBuilder;
        if (ssl) {
            httpClientBuilder = HttpClients.custom();
            httpClientBuilder
                    .setSSLContext(allowAllSSLContext())
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
            ;
        } else {
            httpClientBuilder = HttpClientBuilder.create();
        }
        httpClientBuilder.setDefaultRequestConfig(requestConfig != null ? requestConfig : REQUEST_CONFIG);
        // proxy
        SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
        //
        CloseableHttpClient httpClient = httpClientBuilder.setRoutePlanner(routePlanner).build();
        //
        String responseString = null;
        //create RequestInfo
        final HttpRequestInfo requestInfo = new HttpRequestInfo();
        requestInfo.start();
        try {
            HttpRequestBase httpRequest;
            List<NameValuePair> pairs = new ArrayList<>();
            buildPairs(params, pairs);

            if (method == POST) {
                httpRequest = assemblePostRequest(baseUrl, headers, params);
                if (!pairs.isEmpty()) {
                    requestInfo.setRequest(pairs.toString());
                }
            } else if (method == HttpMethod.GET) {
                httpRequest = assembleGetRequest(baseUrl, headers, params);
            } else if (method == HttpMethod.DELETE) {
                httpRequest = assembleDeleteRequest(baseUrl, headers, params);
            } else {
                throw new HttpException("10010", "unSupport HTTP Method:" + method);
            }

            requestInfo.setUrl(httpRequest.getURI().toString());
            requestInfo.setMethod(httpRequest.getMethod());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                requestInfo.setStatus(response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity, responseCharset) : null;
            };

            responseString = execute(httpClient, requestInfo, httpRequest, responseHandler);
        } catch (HttpException e) {
            requestInfo.setSucceed(false);
            requestInfo.setException(e);
            throw e;
        } finally {
            requestInfo.end();
            requestInfo.setResponse(responseString);
            closeConnection(requestInfo, httpClient);
        }

        return responseString;
    }

    /**
     * handleHttpPostJsonRequest post
     * @param ssl
     * @param baseUrl
     * @param headers
     * @param jsonRequestBody
     * @param requestConfig
     * @param requestCharset
     * @param responseCharset
     * @return
     */
    private static String handleHttpJsonRequest(HttpMethod method, boolean ssl, String baseUrl,
                                                Map<String, String> headers,
                                                String jsonRequestBody,
                                                RequestConfig requestConfig,
                                                final Charset requestCharset,
                                                final Charset responseCharset) {
        final HttpRequestInfo requestInfo = new HttpRequestInfo();
        requestInfo.start();
        // build CloseableHttpClient
        HttpClientBuilder httpClientBuilder;
        if (ssl) {
            httpClientBuilder = HttpClients.custom();
            httpClientBuilder
//                    .setSSLSocketFactory(allowAllSSLContextHostnameVerifierFactory())
                    .setSSLContext(allowAllSSLContext())
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
            ;
        } else {
            httpClientBuilder = HttpClientBuilder.create();
        }
        httpClientBuilder.setDefaultRequestConfig(requestConfig != null ? requestConfig : REQUEST_CONFIG);
        // proxy
        SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
        //
        CloseableHttpClient httpClient = httpClientBuilder.setRoutePlanner(routePlanner).build();
        //
        String responseString = null;
        try {
            requestInfo.setRequest(jsonRequestBody);
            HttpRequestBase httpRequest;
            if (method == POST) {
                httpRequest = assemblePostJsonRequest(baseUrl, headers, jsonRequestBody, requestCharset);
            } else if (method == PUT) {
                httpRequest = assemblePutJsonRequest(baseUrl, headers, jsonRequestBody, requestCharset);
            } else {
                throw new HttpException("10010", "unSupport HTTP Method:" + method);
            }
            requestInfo.setUrl(httpRequest.getURI().toString());
            requestInfo.setMethod(httpRequest.getMethod());
            ResponseHandler<String> responseHandler = response -> {
                requestInfo.setStatus(response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity, responseCharset) : null;
            };
            responseString = execute(httpClient, requestInfo, httpRequest, responseHandler);
        } catch (HttpException e) {
            requestInfo.setSucceed(false);
            requestInfo.setException(e);
            throw e;
        } finally {
            requestInfo.end();
            requestInfo.setResponse(responseString);
            closeConnection(requestInfo, httpClient);
        }
        return responseString;
    }

    /**
     * create ssl connection
     * @return
     */
    private static SSLContext allowAllSSLContext() {
        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial((chain, authType) -> true).build();
        } catch (Exception e) {
            throw new HttpException("1001", "system error: create SSLContext error", e);
        }
        return sslContext;
    }

    private static TrustManager[] buildTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, buildTrustManagers(),
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
        }
        return sSLSocketFactory;
    }

    private static void closeConnection(HttpRequestInfo requestInfo, CloseableHttpClient httpClient) {
        try {
            httpClient.close();
        } catch (Exception e) {
            requestInfo.setSucceed(false);
            requestInfo.setException(e);
            throw new HttpException("10010", "close net connect error", e);
        }
    }

    private static HttpPost assemblePostRequest(String baseUrl, Map<String, String> headers, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(baseUrl);
        List<NameValuePair> pairs = new ArrayList<>();
        buildPairs(params, pairs);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        fillHeaders(httpPost, headers);
        return httpPost;
    }

    private static void buildPairs(Map<String, String> params, List<NameValuePair> pairs) {
        if (params != null) {
            for (String paramKey : params.keySet()) {
                pairs.add(new BasicNameValuePair(paramKey, params.get(paramKey)));
            }
        }
    }

    private static HttpPost assemblePostJsonRequest(String baseUrl, Map<String, String> headers, String jsonRequestBody,
                                                    Charset requestCharset) {
        HttpPost httpPost = new HttpPost(baseUrl);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }
        if (Objects.isNull(requestCharset)) {
            requestCharset = UTF_8;
        }
        ContentType contentTypeJson = ContentType.APPLICATION_JSON;
        if (!UTF_8.equals(requestCharset)) {
            contentTypeJson = contentTypeJson.withCharset(requestCharset);
        }
        StringEntity se = new StringEntity(jsonRequestBody, contentTypeJson);
        httpPost.setEntity(se);
        return httpPost;
    }

    private static HttpPut assemblePutJsonRequest(String baseUrl, Map<String, String> headers, String jsonRequestBody,
                                                  Charset requestCharset) {
        HttpPut httpPut = new HttpPut(baseUrl);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPut.setHeader(header.getKey(), header.getValue());
            }
        }

        if (Objects.isNull(requestCharset)) {
            requestCharset = UTF_8;
        }

        ContentType contentTypeJson = ContentType.APPLICATION_JSON;
        if (!UTF_8.equals(requestCharset)) {
            contentTypeJson = contentTypeJson.withCharset(requestCharset);
        }

        StringEntity  entity = new StringEntity(jsonRequestBody,contentTypeJson);
        httpPut.setEntity(entity);

        return httpPut;
    }

    private static HttpGet assembleGetRequest(String baseUrl, Map<String, String> headers,
                                              Map<String, String> params) {
        String reqUrl = appendRequestUrl(baseUrl, params);
        HttpGet httpGet = new HttpGet(reqUrl);
        fillHeaders(httpGet, headers);
        return httpGet;
    }

    private static HttpDelete assembleDeleteRequest(String baseUrl, Map<String, String> headers,
                                                    Map<String, String> params) {
        String reqUrl = appendRequestUrl(baseUrl, params);
        HttpDelete httpDelete = new HttpDelete(reqUrl);
        fillHeaders(httpDelete, headers);
        return httpDelete;
    }

    private static String appendRequestUrl(String baseUrl, Map<String, String> params) {
        StringBuilder reqUrl = new StringBuilder();
        if (params != null && params.size() > 0) {
            reqUrl.append(baseUrl).append("?");
        } else {
            reqUrl.append(baseUrl);
        }

        if (params != null && params.keySet().size() > 0) {
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String paramKey = keys.next();
                try {
                    reqUrl.append(paramKey).append("=").append(URLEncoder.encode(params.get(paramKey), UTF_8.name()));
                } catch (UnsupportedEncodingException e) {
                    throw new UnsupportedOperationException(e);
                }
                if (keys.hasNext()) {
                    reqUrl.append("&");
                }
            }
        }
        return reqUrl.toString();
    }

    private static void fillHeaders(Object http, Map<String, String> headers) {
        if (null != headers && !headers.isEmpty()) {
            if (http instanceof HttpGet) {
                HttpGet httpGet = (HttpGet) http;
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpGet.addHeader(header.getKey(), header.getValue());
                }
            } else if (http instanceof HttpPost) {
                HttpPost httpPost = (HttpPost) http;
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                }
            } else if (http instanceof HttpDelete) {
                HttpDelete httpDelete = (HttpDelete) http;
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpDelete.addHeader(header.getKey(), header.getValue());
                }
            } else {
            }
        }
    }

    private static String execute(CloseableHttpClient httpclient, final HttpRequestInfo requestInfo,
                                  HttpRequestBase httpPost, ResponseHandler<String> responseHandler) {
        String responString;
        try {
            responString = httpclient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            requestInfo.setConnected(false);
            throw new HttpException("10010", "network error: unknown error", e);
        }

        //返回不是json格式
        if(StringUtils.isBlank(responString) || responString.contains(HTML)){
            throw new HttpException("10010", responString);
        }

        requestInfo.setConnected(true);

        if (requestInfo.getStatus() == HttpStatus.SC_UNAUTHORIZED || requestInfo.getStatus() == HttpStatus.SC_FORBIDDEN) {
            throw new HttpException("1001",
                    "network error: invalid authentication, status:" + requestInfo.getStatus());
        }
        if (requestInfo.getStatus() < HttpStatus.SC_OK || requestInfo.getStatus() >= HttpStatus.SC_MULTIPLE_CHOICES) {
        }
        requestInfo.setSucceed(true);
        return responString;
    }
}

