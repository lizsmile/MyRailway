package com.example.liz.myrailway.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by lenovo on 2017/4/3.
 */

public class OpenResultNoJson {
    public static String getSimpleTrainStringsFromJson(String trainJsonStr) throws JSONException {
        JSONObject TrainJson = new JSONObject(trainJsonStr);
        JSONObject resultObject00 = TrainJson.getJSONObject("result");
        JSONObject resultObject01 = resultObject00.getJSONObject("train_info");
        String name,start,end,starttime,endtime;
        name = resultObject01.getString("name");
        start = resultObject01.getString("start");
        end = resultObject01.getString("end");
        starttime = resultObject01.getString("starttime");
        endtime = resultObject01.getString("endtime");
        String result01 = "列车编号："+name+"\n\n列车始发站:"+start+"\n\n列车终点站:"+end+"\n\n发车时间:"+starttime+"\n\n到站时间:"+endtime;
        String[] train_id,station_name,arrived_time,leave_time,stay,station;
        JSONArray trainArray = resultObject00.getJSONArray("station_list");
        int length = trainArray.length();
        train_id = new String[trainArray.length()];
        station_name = new String[trainArray.length()];
        arrived_time = new String[trainArray.length()];
        leave_time = new String[trainArray.length()];
        stay = new String[trainArray.length()];
        station = new String[trainArray.length()];
        for (int i = 0; i < length; i++){
            JSONObject trainList = trainArray.getJSONObject(i);
            train_id[i] = trainList.getString("train_id");
            station_name[i] = trainList.getString("station_name");
            arrived_time[i] = trainList.getString("arrived_time");
            leave_time[i] = trainList.getString("leave_time");
            stay[i] = trainList.getString("stay");
            station[i] = "\n\n序号：" +train_id[i]+"   站台名称："+station_name[i] +"  到站时间："+arrived_time[i]+"  离站时间："+leave_time[i]+"  停靠时间："+stay[i];
        }
        String result02 = Arrays.toString(station);
        return result01+result02;
    }
}
