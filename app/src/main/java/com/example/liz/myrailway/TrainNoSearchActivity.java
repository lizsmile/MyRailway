package com.example.liz.myrailway;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liz.myrailway.utilities.NetworkUtilsNo;
import com.example.liz.myrailway.utilities.OpenResultNoJson;

import java.net.URL;

public class TrainNoSearchActivity extends AppCompatActivity {
    private EditText trainNo;
    private TextView errorMessage;
    private TextView stationList;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_no_search);
        trainNo = (EditText) findViewById(R.id.train_no);
        errorMessage = (TextView) findViewById(R.id.error_message_display);
        stationList = (TextView)findViewById(R.id.train_no_text);
        loadingBar = (ProgressBar) findViewById(R.id.loading_indicator);
    }

    public void trainNo(View view) {
        loadTrainData();
    }
    private void loadTrainData() {
        String trainQuery = trainNo.getText().toString();
        showTrainDataView();
        new trainNoQueryTask().execute(trainQuery);
    }

    //加载成功时的方法
    private void showTrainDataView() {
        //不显示错误信息
        errorMessage.setVisibility(View.INVISIBLE);
        //显示列车表信息
        stationList.setVisibility(View.VISIBLE);
    }

    //加载失败时的方法
    private void showError() {
        //不显示错误信息
        errorMessage.setVisibility(View.VISIBLE);
        //显示列车表信息
        stationList.setVisibility(View.INVISIBLE);
    }

    public class trainNoQueryTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {
            if (param.length == 0) {
                return null;
            }
            String train = param[0];
            URL searchUrl = NetworkUtilsNo.buildUrl(train);
            try {
                String trainNoSearchResults = NetworkUtilsNo.getJsonResultNo(searchUrl);
                Log.v("json", trainNoSearchResults);
                String trainNoData = OpenResultNoJson.getSimpleTrainStringsFromJson(trainNoSearchResults);
                return trainNoData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String trainData) {
            loadingBar.setVisibility(View.INVISIBLE);
            if (trainData != null) {
                showTrainDataView();
                stationList.setText(trainData);
            } else {
                showError();
            }
        }
    }
}
