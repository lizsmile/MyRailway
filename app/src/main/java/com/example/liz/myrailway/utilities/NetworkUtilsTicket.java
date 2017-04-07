package com.example.liz.myrailway.utilities;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by lenovo on 2017/4/3.
 */

public class NetworkUtilsTicket {
    private final static String APPKEY = "a0860d470a2a1cede6b613b121959986";

    final static String url = "http://apis.juhe.cn/train/dsd";//请求接口地址
    final static String method = "Get";


    public static URL buildUrl(String province, String city, String county) {
        String strUrl;
        URL queryUrl = null;
        HashMap<String, String> params = new HashMap<>();//请求参数
        params.put("province", province);//省份
        params.put("city", city);//市
        params.put("county", county);//区
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        strUrl = url + "?" + urlencode(params);
        try {
            queryUrl = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return queryUrl;
    }

    public static String getJsonResultTicket(URL url) throws IOException {
        String result = null;
        try {
            result = net(url);
            Log.v("function","get json result ticket");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private static String net(URL url) throws Exception {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            if (method.equals("GET")) {
                conn.setRequestMethod("GET");
            }
            InputStream is = conn.getInputStream();
            Log.v("inputstream","is is formed");
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            conn.disconnect();
        }
    }
    private static String urlencode(HashMap<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
