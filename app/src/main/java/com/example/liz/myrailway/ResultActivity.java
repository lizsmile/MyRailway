package com.example.liz.myrailway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity implements ResultAdapter.ListItemClickListener{
    private TextView resultTextView;
    private static final int NUM_LIST_ITEMS = 100;
    private ResultAdapter adapter;
    private RecyclerView recyclerView;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultTextView = (TextView)findViewById(R.id.textViewResult);
        recyclerView = (RecyclerView)findViewById(R.id.train_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //允许recycler view在UI上进行优化
        recyclerView.setHasFixedSize(true);
        adapter = new ResultAdapter(NUM_LIST_ITEMS,this);
        recyclerView.setAdapter(adapter);


        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            String result = intent.getStringExtra(intent.EXTRA_TEXT);
            resultTextView.setText(result);
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
            case R.id.action_refresh:
                adapter = new ResultAdapter(NUM_LIST_ITEMS, this);
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.action_settings:
                Toast.makeText(ResultActivity.this, "print settings", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //recycler view项被选中
    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (toast != null) {
            toast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + " clicked.";
        toast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

        toast.show();
    }
}
}
