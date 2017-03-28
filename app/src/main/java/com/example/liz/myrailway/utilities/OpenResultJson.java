package com.example.liz.myrailway.utilities;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by lenovo on 2017/3/28.
 */

public class OpenResultJson {
    public static String[] getSimpleTrainStringsFromJson(Context context, String trainJsonStr) throws JSONException {

        //每一辆列车的信息是array里的一个元素
        final String TRAIN_LIST = "list";

        final String TRAIN_NO = "train_no";
        final String TRAIN_TYPE = "train_type";
        final String START_STA = "start_staion";
        final String START_STA_TYPE = "start_station_type";
        final String END_STA = "end_station";
        final String END_STA_TYPE = "end_station_type";

        final String START_TIME = "leave_time";
        final String END_TIME = "arrived_time";
        final String RUN_TIME = "run_time";
        final String PRICE_LIST = "price_list";
        final String PRICE_TYPE = "price_type";
        final String TRAIN_PRICE = "price";

        final String OWM_MESSAGE_CODE = "cod";

        /* String array to hold each day's weather String */
        String[] parsedTrainData = null;

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

        JSONArray trainArray = TrainJson.getJSONArray(TRAIN_LIST);

        parsedTrainData = new String[trainArray.length()];
        //关于时间的语句，后面再写
//        long localDate = System.currentTimeMillis();
//        long utcDate = SunshineDateUtils.getUTCDateFromLocal(localDate);
//        long startDay = SunshineDateUtils.normalizeDate(utcDate);

        int length = trainArray.length();
        for (int i = 0; i < length; i++) {
            String trainNo,trainType,startSta,startStaType,endSta,endStaType,startTime,endTime,runTime,priceType,trainPrice;
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


            /*
             * We ignore all the datetime values embedded in the JSON and assume that
             * the values are returned in-order by day (which is not guaranteed to be correct).
             */
//            dateTimeMillis = startDay + SunshineDateUtils.DAY_IN_MILLIS * i;
//            date = SunshineDateUtils.getFriendlyDateString(context, dateTimeMillis, false);

            JSONObject mutiPrice = eachTrain.getJSONArray(PRICE_LIST).getJSONObject(10);
            priceType = mutiPrice.getString(PRICE_TYPE);
            trainPrice = mutiPrice.getString(PRICE_TYPE);

            /*
             * Temperatures are sent by Open Weather Map in a child object called "temp".
             *
             * Editor's Note: Try not to name variables "temp" when working with temperature.
             * It confuses everybody. Temp could easily mean any number of things, including
             * temperature, temporary and is just a bad variable name.
             */
            parsedTrainData[i] = trainNo+trainType+startSta+startStaType+endSta+endStaType+startTime+endTime+runTime+priceType+trainPrice;
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
