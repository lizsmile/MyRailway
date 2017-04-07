package com.example.liz.myrailway;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.RouteInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.example.liz.myrailway.utilities.SecondaryMenuUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    EditText startEditText;
    EditText endEditText;
    EditText timeEditText;
    ExpandableListView listView;
    Calendar calendar = Calendar.getInstance();
    private  List<String>  groupArray;//组列表
    private  List<List<String>> childArray;//子列表
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
        SDKInitializer.initialize(getApplicationContext());
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

        //点击日期编辑框的监听器
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartCityList();
            }
        });
        endEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEndCityList();
            }
        });
    }
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            updateTime();
        }
    };
    //更新时间框的方法
    private void updateTime(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.CHINA);
        timeEditText.setText(simpleDateFormat.format(calendar.getTime()));
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
    public void textSwitch(View view){
        String temp1 = startEditText.getText().toString();
        String temp2 = endEditText.getText().toString();
        startEditText.setText(temp2);
        endEditText.setText(temp1);
    }
    public void trainNoSearch(View view){
        Intent intent = new Intent(this,TrainNoSearchActivity.class);
        startActivity(intent);
    }
    public void buyPointSearch(View view){
        Intent intent = new Intent(this,BuyPointSearchActivity.class);
        startActivity(intent);
    }
    public void trainStationSearch(View view){
        Intent intent = new Intent(this,RouteActivity.class);
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

    String[] groupData = { "北京市","广东省","山东省","江苏省","河南省","上海市","河北省","浙江省","陕西省","湖南省","重庆市","福建省",
            "天津市","云南省","四川省","广西壮族自治区","安徽省","海南省","江西省","湖北省","山西省","辽宁省","台湾省","黑龙江","内蒙古自治区",
            "贵州省","甘肃省","青海省","新疆维吾尔自治区","西藏区","吉林省","宁夏回族自治区"};
    String[][] childData = {
            { "北京" },
            { "东莞","广州","中山","深圳","惠州","江门","珠海","汕头","佛山","湛江","河源","肇庆","清远","潮州","韶关","揭阳","阳江","梅州","云浮","茂名","汕尾"},
            { "济南","青岛","临沂","济宁","菏泽","烟台","淄博","泰安","潍坊","日照","威海","滨州","东营","聊城","德州","莱芜","枣庄"},
            {"苏州","徐州","盐城","无锡","南京","南通","连云港","常州","镇江","扬州","淮安","泰州","宿迁"},
            {"郑州","南阳","新乡","安阳","洛阳","信阳","平顶山","周口","商丘","开封","焦作","驻马店","濮阳","三门峡","漯河","许昌","鹤壁","济源"},
            {"上海"},
            {"石家庄","唐山","保定","邯郸","邢台","河北区","沧州","秦皇岛","张家口","衡水","廊坊","承德"},
            {"温州","宁波","杭州","台州","嘉兴","金华","湖州","绍兴","舟山","丽水","衢州"},
            {"西安","咸阳","宝鸡","汉中","渭南","安康","榆林","商洛","延安","铜川"},
            {"长沙","邵阳","常德","衡阳","株洲","湘潭","永州","岳阳","怀化","郴州","娄底","益阳","张家界","湘西"},
            {"重庆"},
            {"漳州","厦门","泉州","福州","莆田","宁德","三明","南平","龙岩"},
            {"天津"},
            {"昆明","红河","大理","文山","德宏","曲靖","昭通","楚雄","保山","玉溪","丽江","临沧","思茅","西双版纳","怒江","迪庆"},
            {"成都","绵阳","广元","达州","南充","德阳","广安","阿坝州","巴中","遂宁","内江","凉山州","攀枝花","乐山","自贡","泸州","雅安","宜宾","资阳","眉山","甘孜"},
            {"贵港","玉林","北海","南宁","柳州","桂林","梧州","钦州","来宾","河池","百色","贺州","崇左","防城港"},
            {"芜湖","合肥","六安","宿州","阜阳","安庆","马鞍山","蚌埠","淮北","淮南","宣城","黄山","铜陵","亳州","池州","巢湖","滁州"},
            {"三亚","海口","琼海","文昌","东方","昌江","陵水","乐东","保亭","五指山","澄迈","万宁","儋州","临高","白沙","定安","琼中","屯昌"},
            {"南昌","赣州","上饶","吉安","九江","新余","抚州","宜春","景德镇","萍乡","鹰潭"},
            {"武汉","宜昌","襄樊","荆州","恩施州","黄冈","孝感","十堰","咸宁","黄石","仙桃","天门","随州","荆门","潜江","鄂州","神农架"},
            {"太原","大同","运城","长治","晋城","忻州","临汾","吕梁","晋中","阳泉","朔州"},
            {"大连","沈阳","丹东","辽阳","葫芦岛","锦州","朝阳","营口","鞍山","抚顺","阜新","盘锦","本溪","铁岭"},
            {"台北","高雄","台中","新竹","基隆","台南","嘉义"},
            {"齐齐哈尔","哈尔滨","大庆","佳木斯","双鸭山","牡丹江","鸡西","黑河","绥化","鹤岗","伊春","大兴安岭","七台河"},
            {"赤峰","包头","通辽","呼和浩特","鄂尔多斯","乌海","呼伦贝尔","兴安","巴彦淖尔","乌兰察布","锡林郭勒","阿拉善"},
            {"贵阳","黔东南","黔南","遵义","黔西南","毕节","铜仁","安顺","六盘水"},
            {"兰州","天水","庆阳","武威","酒泉","张掖","陇南","白银","定西","平凉","嘉峪关","临夏","金昌","甘南"},
            {"西宁","海西","海东","海北","果洛","玉树","黄南"},
            {"乌鲁木齐","伊犁","昌吉","石河子","哈密","阿克苏","巴音郭楞","喀什","塔城","克拉玛依","和田","阿勒泰","吐鲁番","阿拉尔","博尔塔","五家渠","克孜勒苏","图木舒克"},
            {"拉萨","山南","林芝","日喀则","阿里","昌都","那曲"},
            {"吉林","长春","白山","延边州　 白城","松原","辽源","通化","四平"},
            {"银川","吴忠","中卫","石嘴山","固原"}};
    //选择出发城市
    public void selectStartCityList(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.city_listview);
        dialog.setTitle(R.string.select_city);
        listView = (ExpandableListView) dialog.findViewById(R.id.city_list);
        groupArray =new ArrayList<String>();
        childArray = new ArrayList<List<String>>();

        SecondaryMenuUtils secondaryMenuUtils = new SecondaryMenuUtils(MainActivity.this,listView,groupArray,childArray,groupData,childData);
        childArray.clear();
        groupArray.clear();
        secondaryMenuUtils.addDate(groupData);// 添加二级菜单数据
        secondaryMenuUtils.createExpandableListViewDialog(startEditText);
    }

    public void selectEndCityList(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.city_listview);
        dialog.setTitle(R.string.select_city);
        listView = (ExpandableListView) dialog.findViewById(R.id.city_list);
        groupArray =new ArrayList<String>();
        childArray = new ArrayList<List<String>>();

        SecondaryMenuUtils secondaryMenuUtils = new SecondaryMenuUtils(MainActivity.this,listView,groupArray,childArray,groupData,childData);
        childArray.clear();
        groupArray.clear();
        secondaryMenuUtils.addDate(groupData);// 添加二级菜单数据
        secondaryMenuUtils.createExpandableListViewDialog(endEditText);
    }
}
