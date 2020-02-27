package com.jj.childmodel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import com.jj.childmodel.adapters.MyRecyclerViewAdapter;
import com.jj.childmodel.adapters.MySpinnerAdapter;
import com.jj.childmodel.bean.WhiteTimeBean;
import com.jj.childmodel.menuadapter.MenuRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Spinner time_spinner,sleep_spinner;
    private RecyclerView whiteRecyclerView;
    private List<String> usedList = Arrays.asList("10","20","30","40","50","60");
    private List<String> sleepList = Arrays.asList("1","5","10","15","20","30");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        time_spinner = findViewById(R.id.time_spinner);
        sleep_spinner = findViewById(R.id.sleep_spinner);

        time_spinner.setAdapter(new MySpinnerAdapter(usedList));
        sleep_spinner.setAdapter(new MySpinnerAdapter(sleepList));

        List<WhiteTimeBean> timeBeans = new ArrayList<>();
        timeBeans.add(new WhiteTimeBean());
        timeBeans.add(new WhiteTimeBean());
        timeBeans.add(new WhiteTimeBean());
        timeBeans.add(new WhiteTimeBean());
        timeBeans.add(new WhiteTimeBean());

        whiteRecyclerView = findViewById(R.id.whiteRecyclerView);
        whiteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(timeBeans);
        whiteRecyclerView.setAdapter(adapter);
        adapter.setOnMenuItemClick(new MenuRecyclerAdapter.OnMenuItemClick() {
            @Override
            public void onContentClick(View view) {
                Log.w("test_bug","onContentClick");
            }

            @Override
            public void onMenuClick(int position, String tag) {
                Log.w("test_bug","onMenuClick position = " + position + " tag = " + tag);
            }
        });
    }

}
