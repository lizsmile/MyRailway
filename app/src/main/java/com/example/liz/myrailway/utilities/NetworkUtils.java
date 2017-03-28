/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.liz.myrailway.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class NetworkUtils {
    private final static String APPKEY = "a0860d470a2a1cede6b613b121959986";

    final static String url = "http://apis.juhe.cn/train/s2swithprice";//请求接口地址
    final static String method = "Get";


    public static URL buildUrl(String start,String end,String date) {
        String strUrl;
        URL queryUrl = null;
        HashMap<String, String> params = new HashMap<>();//请求参数
        params.put("start", start);//出发站
        params.put("end", end);//终点站
        params.put("date", date);//时间
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        strUrl = url + "?" + urlencode(params);
        try {
            queryUrl = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return queryUrl;
    }

    public static String getJsonResult01(URL url) throws IOException{
        String result = null;
        try {
            result = net(url);
            Log.v("function","get json result 01");
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