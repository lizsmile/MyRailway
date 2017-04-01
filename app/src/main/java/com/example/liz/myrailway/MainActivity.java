package com.example.liz.myrailway;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {
    EditText startEditText;
    EditText endEditText;
    EditText timeEditText;
    //Button searchButton;

    private static final String LIFECYCLE_CALLBACKS_TEXT_START = "callbacksStart";
    private static final String LIFECYCLE_CALLBACKS_TEXT_END = "callbacksERnd";
    private static final String LIFECYCLE_CALLBACKS_TEXT_TIME = "callbacksTime";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startEditText = (EditText) findViewById(R.id.editTextStart);
        endEditText = (EditText) findViewById(R.id.editTextEnd);
        timeEditText = (EditText) findViewById(R.id.editTextTime);

        //当旋转屏幕时，已经填好的数据自动填充
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_START)) {
                String startCallBack = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_START);
                startEditText.setText(startCallBack);
            }
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_END)) {
                String endCallBack = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_END);
                startEditText.setText(endCallBack);
            }
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_TIME)) {
                String timeCallBack = savedInstanceState.getString(LIFECYCLE_CALLBACKS_TEXT_TIME);
                startEditText.setText(timeCallBack);
            }
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //button的点击事件方法
    public void buttonSearchOnClick(View view) {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        String start = startEditText.getText().toString();
        String end = endEditText.getText().toString();
        String time = timeEditText.getText().toString();
        intent.putExtra("intent_start", start);
        intent.putExtra("intent_end", end);
        intent.putExtra("intent_time", time);
        startActivity(intent);
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
            Intent settingsIntent = new Intent(this,SettingsActivity.class);
            startActivity(settingsIntent);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String start = startEditText.getText().toString();
        String end = endEditText.getText().toString();
        String time = timeEditText.getText().toString();
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_START, start);
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_END, end);
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_TIME, time);
    }
}
