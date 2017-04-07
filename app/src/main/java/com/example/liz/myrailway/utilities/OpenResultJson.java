package com.example.liz.myrailway.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Arrays;

/**
 * Created by lenovo on 2017/3/28.
 */

public class OpenResultJson {
    public static String[] getSimpleTrainStringsFromJson(String trainJsonStr) throws JSONException {

        //每一辆列车的信息是array里的一个元素
        final String TRAIN_LIST = "list";

        final String TRAIN_NO = "train_no";
        final String TRAIN_TYPE = "train_type";
        final String START_STA = "start_station";
        final String START_STA_TYPE = "start_station_type";
        final String END_STA = "end_station";
        final String END_STA_TYPE = "end_station_type";

        final String START_TIME = "start_time";
        final String END_TIME = "end_time";
        final String RUN_TIME = "run_time";
        final String PRICE_LIST = "price_list";
        final String PRICE_TYPE = "price_type";
        final String TRAIN_PRICE = "price";

        final String OWM_MESSAGE_CODE = "cod";

        /* String array to hold each day's weather String */
        String[] parsedTrainData;

        JSONObject TrainJson = new JSONObject(trainJsonStr);

        //如果有错误，待会儿再写
//        if (TrainJson.has(OWM_MESSAGE_CODE)) {
//            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);
//
//            switch (errorCode) {
//                case HttpURLConnection.HTTP_OK:
//                    break;
//                case HttpURLConnection.HTTP_NOT_FOUND:
//                    /* Location invalid */
//                    return null;
//                default:
//                    /* Server probably down */
//                    return null;
//            }
//        }
        JSONObject resultObject = TrainJson.getJSONObject("result");
        JSONArray trainArray = resultObject.getJSONArray(TRAIN_LIST);

        parsedTrainData = new String[trainArray.length()];
        //关于时间的语句，后面再写
//        long localDate = System.currentTimeMillis();
//        long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
//        long startDay = SunshineDateUtils.normalizeDate(utcDate);

        int length = trainArray.length();
        for (int i = 0; i < length; i++) {
            String trainNo,trainType,startSta,startStaType,endSta,endStaType,startTime,endTime,runTime;
            //依次获取每列车的信息
            JSONObject eachTrain = trainArray.getJSONObject(i);
            trainNo = eachTrain.getString(TRAIN_NO);
            trainType = eachTrain.getString(TRAIN_TYPE);
            startSta = eachTrain.getString(START_STA);
            startStaType = eachTrain.getString(START_STA_TYPE);
            endSta = eachTrain.getString(END_STA);
            endStaType = eachTrain.getString(END_STA_TYPE);
            startTime = eachTrain.getString(START_TIME);
            endTime = eachTrain.getString(END_TIME);
            runTime = eachTrain.getString(RUN_TIME);

            String[] priceType,trainPrice,priceGroup;
            JSONArray mutiPrices = eachTrain.getJSONArray(PRICE_LIST);
            int lengthp = mutiPrices.length();
            priceType = new String[mutiPrices.length()];
            trainPrice = new String[mutiPrices.length()];
            priceGroup = new String[mutiPrices.length()];
            for (int j = 0; j < lengthp; j++){
                JSONObject mutiPrice = mutiPrices.getJSONObject(j);
                priceType[j] = mutiPrice.getString(PRICE_TYPE);
                trainPrice[j] = mutiPrice.getString(TRAIN_PRICE);
                priceGroup[j] = "座位类型:"+priceType[j]+" 价格:"+trainPrice[j];
            }


            parsedTrainData[i] = "列车序号:"+trainNo+" 列车类型:"+trainType+"\n\n"+"起始站:"+startSta+" 起始站类型:"+startStaType+"\n\n"+"终点站:"+endSta+" 终点站类型:"+endStaType+"\n\n"+"发车时间:"+startTime+" 到站时间:"+endTime+" 行驶时间:"+runTime+"\n\n"+ Arrays.toString(priceGroup);
        }

        return parsedTrainData;
    }

    /**
     * Parse the JSON and convert it into ContentValues that can be inserted into our database.
     *
     * @param context         An application context, such as a service or activity context.
     * @param forecastJsonStr The JSON to parse into ContentValues.
     *
     * @return An array of ContentValues parsed from the JSON.
     */
    public static ContentValues[] getFullWeatherDataFromJson(Context context, String forecastJsonStr) {
        /** This will be implemented in a future lesson **/
        return null;
    }
}
