package com.example.liz.myrailway;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liz.myrailway.utilities.NetworkUtils;
import com.example.liz.myrailway.utilities.OpenResultJson;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText startEditText;
    EditText endEditText;
    EditText timeEditText;
    //Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startEditText = (EditText) findViewById(R.id.editTextStart);
        endEditText = (EditText) findViewById(R.id.editTextEnd);
        timeEditText = (EditText) findViewById(R.id.editTextTime);


    }
    //button的点击事件方法
    public void buttonSearchOnClick(View view) {
        stationToStationSearch();
    }

    //传入菜单资源
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //用正确的菜单项调用
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "print settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    public class trainQueryTask extends AsyncTask<URL,Void,String>{
        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String location = params[0];
            String start = startEditText.getText().toString();
            String end = endEditText.getText().toString();
            String time = timeEditText.getText().toString();

            URL searchUrl = NetworkUtils.buildUrl(start,end,time);
            try {
                String githubSearchResults = NetworkUtils.getJsonResult01(searchUrl);
                String[] parsedTrainData = OpenResultJson.getSimpleTrainStringsFromJson(MainActivity.this, githubSearchResults);
                return parsedTrainData;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")){
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra(intent.EXTRA_TEXT, s);
                startActivity(intent);
            }
        }
    }

    //利用EditText内容来构建URL,查询并返回json的string形式
    private void stationToStationSearch() {
        String start = startEditText.getText().toString();
        String end = endEditText.getText().toString();
        String time = timeEditText.getText().toString();

        URL trainSearchQuery = NetworkUtils.buildUrl(start,end,time);
        new trainQueryTask().execute(trainSearchQuery);
    }
}
