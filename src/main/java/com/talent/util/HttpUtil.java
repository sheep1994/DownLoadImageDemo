package com.talent.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: DownLoadImageDemo
 * @author: Mr.Guo
 * @description: Http 请求方式工具类
 * @create: 2019-05-05 14:48
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private HttpUtil() {}

    /**
     * post请求方式
     * @param url 请求地址
     * @param headMap 请求头
     * @param paramsMap 请求参数
     * @return
     */
    public static String post(String url, Map<String, String> headMap, Map<String, String> paramsMap) {
        logger.info("post请求方式开始... url : [{}], headMap : [{}], paramsMap : [{}]", url, JSON.toJSON(headMap), JSON.toJSON(paramsMap));
        String result = null;
        // 创建一个可关闭的HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        // head
        getHeadMap(headMap, post);
        // params
        List<NameValuePair> pairs = paramsMap.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(pairs));
            response = httpClient.execute(post);
            logger.info("响应状态 status : [{}]", response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(httpClient, response);
        }
        return result;
    }

    /**
     * post请求方式，参数为json字符串
     * @param url
     * @param headMap
     * @param jsonStr
     * @return
     */
    public static String postWithJson(String url, Map<String, String> headMap, String jsonStr) {
        logger.info("post请求方式，参数为json字符串开始... url : [{}], headMap : [{}], jsonStr : [{}]", url, JSON.toJSON(headMap), jsonStr);
        String result = null;
        // 创建一个可关闭的HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        getHeadMap(headMap, post);
        post.addHeader("Content-Type","application/json");
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new ByteArrayEntity(jsonStr.getBytes("utf-8")));
            response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(httpClient, response);
        }
        return result;
    }

    private static void getHeadMap(Map<String, String> headMap, HttpPost post) {
        // head
        if (headMap != null) {
            Iterator<Map.Entry<String, String>> iterator = headMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                post.addHeader(next.getKey(), next.getValue());
            }
        }
    }

    /**
     * get请求方式
     * @param url
     * @param headMap
     * @param paramsMap
     * @return
     */
    public static String get(String url, Map<String, String> headMap, Map<String, String> paramsMap) {
        logger.info("get请求方式开始... url : [{}], headMap : [{}], paramsMap : [{}]", url, JSON.toJSON(headMap), JSON.toJSON(paramsMap));
        String result = null;
        // 创建一个可关闭的HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> pairs = paramsMap.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            builder.setParameters(pairs);
            HttpGet get = new HttpGet(builder.build());
            if (headMap != null) {
                Iterator<Map.Entry<String, String>> iterator = headMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    get.setHeader(next.getKey(), next.getValue());
                }
            }
            response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(httpClient, response);
        }
        return result;
    }

    /**
     * put请求方式
     * @param url
     * @param headMap
     * @param paramsMap
     * @return
     */
    public static String put(String url, Map<String, String> headMap, Map<String, String> paramsMap) {
        logger.info("put请求方式开始... url : [{}], headMap ： [{}], paramsMap : [{}]", url, JSON.toJSON(headMap), JSON.toJSON(paramsMap));
        String result = null;
        // 创建一个可关闭的HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut put = new HttpPut(url);
        // head
        if (headMap != null) {
            Iterator<Map.Entry<String, String>> iterator = headMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                put.addHeader(next.getKey(), next.getValue());
            }
        }
        List<NameValuePair> pairs = paramsMap.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        CloseableHttpResponse response = null;
        try {
            put.setEntity(new UrlEncodedFormEntity(pairs));
            response = httpClient.execute(put);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(httpClient, response);
        }
        return result;
    }

    /**
     * 关闭http对象
     * @param httpClient
     * @param response
     */
    private static void close(CloseableHttpClient httpClient, CloseableHttpResponse response) {
        try {
            httpClient.close();
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
