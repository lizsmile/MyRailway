package com.example.liz.myrailway;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liz.myrailway.utilities.NetworkUtils;
import com.example.liz.myrailway.utilities.OpenResultJson;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ResultActivity extends AppCompatActivity implements ResultAdapter.ResultAdapterOnclickHandler {
    private TextView errorMessage;
    private ProgressBar loadingBar;
    private ResultAdapter adapter;
    private RecyclerView recyclerView;
    private Button dateSelect;
    private TextView startToEnd;
    String start;
    String end;
    String time;
    String[] params;
    Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        start = intent.getStringExtra("intent_start");
        end = intent.getStringExtra("intent_end");
        time = intent.getStringExtra("intent_time");
        params = new String[]{start, end, time};
        errorMessage = (TextView) findViewById(R.id.error_message_display);
        recyclerView = (RecyclerView) findViewById(R.id.train_numbers);
        loadingBar = (ProgressBar) findViewById(R.id.loading_indicator);
        dateSelect = (Button) findViewById(R.id.present_date_btn);
        startToEnd = (TextView)findViewById(R.id.from_to_textview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //允许recycler view在UI上进行优化
        recyclerView.setHasFixedSize(true);
        adapter = new ResultAdapter(this);
        recyclerView.setAdapter(adapter);
        dateSelect.setText(time);
        startToEnd.setText(start+"至"+end);
        loadTrainData();

    }

    private void loadTrainData() {
        showTrainDataView();
        new trainQueryTask().execute(params);
        Log.v("task", params[0]);
    }

    //点击recycler view
    @Override
    public void onClick(String singleTrainMessage) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("detail", singleTrainMessage);
        startActivity(intent);
        Log.v("onclick","result activity");
    }

    //加载成功时的方法
    private void showTrainDataView() {
        //不显示错误信息
        errorMessage.setVisibility(View.INVISIBLE);
        //显示列车表信息
        recyclerView.setVisibility(View.VISIBLE);
    }

    //加载失败时的方法
    private void showError() {
        //不显示错误信息
        errorMessage.setVisibility(View.VISIBLE);
        //显示列车表信息
        recyclerView.setVisibility(View.INVISIBLE);
    }

    public class trainQueryTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String[] param) {
            if (param.length == 0) {
                return null;
            }
            URL searchUrl = NetworkUtils.buildUrl(param[0], param[1], param[2]);
            try {
                String trainSearchResults = NetworkUtils.getJsonResult01(searchUrl);
                Log.v("json", trainSearchResults);
                String[] trainData = OpenResultJson.getSimpleTrainStringsFromJson(trainSearchResults);
                return trainData;
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
        protected void onPostExecute(String[] trainData) {
            loadingBar.setVisibility(View.INVISIBLE);
            if (trainData != null) {
                showTrainDataView();
                adapter.setTrainData(trainData);
            } else {
                showError();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //菜单项被选中
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
//            case R.id.action_refresh:
//                adapter.setTrainData(null);
//                recyclerView.setAdapter(adapter);
//                return true;
            case R.id.action_settings:
                Toast.makeText(ResultActivity.this, "print settings", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void yesterdaySearch(View view) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //首先将日期-1 day
        try {
            Date date = simpleDateFormat.parse(time);
            Date dateYesterday = new Date(date.getTime() - 24 * 60 * 60 * 1000);
            String timeYesterday = simpleDateFormat.format(dateYesterday);
            params = new String[]{start, end, timeYesterday};
            loadTrainData();
        } catch (ParseException e) {
            Log.v("parse exception", "date");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void tomorrowSearch(View view) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //首先将日期-1 day
        try {
            Date date = simpleDateFormat.parse(time);
            Date dateTomorrow = new Date(date.getTime() + 24 * 60 * 60 * 1000);
            String timeTomorrow = simpleDateFormat.format(dateTomorrow);
            params = new String[]{start, end, timeTomorrow};
            loadTrainData();
        } catch (ParseException e) {
            Log.v("parse exception", "date");
        }
    }

    public void changeDate(View view) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };
        new DatePickerDialog(ResultActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.CHINA);
        dateSelect.setText(simpleDateFormat.format(calendar.getTime()));
    }
}

