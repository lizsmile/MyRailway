package com.example.liz.myrailway.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by lenovo on 2017/4/3.
 */

public class OpenTicketPointJson {
    public static String getSimpleTicketPointFromJson(String trainJsonStr) throws JSONException {
        JSONObject TrainJson = new JSONObject(trainJsonStr);
        String[] point_id,agency_name,address,phone_no,start_time_am,stop_time_am,start_time_pm,stop_time_pm,result01;
        JSONArray trainArray = TrainJson.getJSONArray("result");
        int length = trainArray.length();
        point_id = new String[trainArray.length()];
        agency_name = new String[trainArray.length()];
        address = new String[trainArray.length()];
        phone_no = new String[trainArray.length()];
        start_time_am = new String[trainArray.length()];
        stop_time_am = new String[trainArray.length()];
        start_time_pm = new String[trainArray.length()];;
        stop_time_pm = new String[trainArray.length()];
        result01 = new String[trainArray.length()];
        for (int i = 0; i < length; i++){
            JSONObject trainList = trainArray.getJSONObject(i);
            point_id[i] = i+"";
            agency_name[i] = trainList.getString("agency_name");
            address[i] = trainList.getString("address");
            phone_no[i] = trainList.getString("phone_no");
            start_time_am[i] = trainList.getString("start_time_am");
            stop_time_am[i] = trainList.getString("stop_time_am");
            start_time_pm[i] = trainList.getString("start_time_pm");
            stop_time_pm[i] = trainList.getString("stop_time_pm");
            result01[i] = "\n\n序号：" +point_id[i]+"\n\n代售点名称："+agency_name[i] +"\n\n地址："+address[i]+"\n\n联系电话："+phone_no[i]+"\n\n营业时间："+start_time_am[i]+"--"+stop_time_am[i]+"   "+start_time_pm[i]+"--"+stop_time_pm[i];
        }
        String result02 = Arrays.toString(result01);
        return result02;
    }
}
