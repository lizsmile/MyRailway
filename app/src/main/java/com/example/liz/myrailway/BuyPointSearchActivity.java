package com.example.liz.myrailway;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liz.myrailway.utilities.NetworkUtilsTicket;
import com.example.liz.myrailway.utilities.OpenTicketPointJson;

import java.net.URL;

public class BuyPointSearchActivity extends AppCompatActivity {
    private EditText province,city,county;
    private TextView errorMessage;
    private TextView ticketList;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_point_search);
        province = (EditText) findViewById(R.id.province_edit);
        city = (EditText) findViewById(R.id.city_edit);
        county = (EditText) findViewById(R.id.county_edit);

        errorMessage = (TextView) findViewById(R.id.error_message_display);
        ticketList = (TextView)findViewById(R.id.ticket_list);
        loadingBar = (ProgressBar) findViewById(R.id.loading_indicator);
    }

    public void ticketPoint(View view) {
        loadTrainData();
    }
    private void loadTrainData() {
        String provinceStr = province.getText().toString();
        String cityStr = city.getText().toString();
        String countyStr = county.getText().toString();
        showTrainDataView();
        new ticketQueryTask().execute(provinceStr,cityStr,countyStr);
    }

    //加载成功时的方法
    private void showTrainDataView() {
        //不显示错误信息
        errorMessage.setVisibility(View.INVISIBLE);
        //显示列车表信息
        ticketList.setVisibility(View.VISIBLE);
    }

    //加载失败时的方法
    private void showError() {
        //不显示错误信息
        errorMessage.setVisibility(View.VISIBLE);
        //显示列车表信息
        ticketList.setVisibility(View.INVISIBLE);
    }

    public class ticketQueryTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] param) {
            if (param.length == 0) {
                return null;
            }
            String province = param[0];
            String city = param[1];
            String county = param[2];
            URL searchUrl = NetworkUtilsTicket.buildUrl(province,city,county);
            try {
                String ticketPointResults = NetworkUtilsTicket.getJsonResultTicket(searchUrl);
                Log.v("json", ticketPointResults);
                String ticketData = OpenTicketPointJson.getSimpleTicketPointFromJson(ticketPointResults);
                return ticketData;
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
        protected void onPostExecute(String ticketData) {
            loadingBar.setVisibility(View.INVISIBLE);
            if (ticketData != null) {
                showTrainDataView();
                ticketList.setText(ticketData);
            } else {
                showError();
            }
        }
    }
}
