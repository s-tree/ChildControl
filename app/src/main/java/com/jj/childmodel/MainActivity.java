package com.jj.childmodel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.jj.childmodel.adapters.MyRecyclerViewAdapter;
import com.jj.childmodel.adapters.MySpinnerAdapter;
import com.jj.childmodel.bean.WhiteTimeBean;
import com.jj.childmodel.menuadapter.MenuRecyclerAdapter;
import com.jj.childmodel.utils.SPUtil;
import com.jj.childmodel.utils.SleepSettingsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MenuRecyclerAdapter.OnMenuItemClick,CompoundButton.OnCheckedChangeListener{
    private Spinner time_spinner,sleep_spinner;
    private RecyclerView whiteRecyclerView;
    private SwitchCompat switchCompat;
    private List<String> usedList = Arrays.asList("10","20","30","40","50","60");
    private List<String> sleepList = Arrays.asList("1","5","10","15","20","30");
    private boolean isFirstKeepSelect = true;
    private boolean isFirstSleepSelect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        time_spinner = findViewById(R.id.time_spinner);
        sleep_spinner = findViewById(R.id.sleep_spinner);

        switchCompat = findViewById(R.id.switch_bt);
        switchCompat.setChecked(SPUtil.isEnable());
        switchCompat.setOnCheckedChangeListener(this);

        time_spinner.setAdapter(new MySpinnerAdapter(usedList));
        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isFirstKeepSelect){
                    isFirstKeepSelect = false;
                    return;
                }
                int time = Integer.parseInt(usedList.get(i));
                SPUtil.setKeepTime(time);
                SleepSettingsManager.postKeepTimeChanged(time);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        time_spinner.setSelection(usedList.indexOf(String.valueOf(SPUtil.getKeepTime())));

        sleep_spinner.setAdapter(new MySpinnerAdapter(sleepList));
        sleep_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isFirstSleepSelect){
                    isFirstSleepSelect = false;
                    return;
                }
                int time = Integer.parseInt(sleepList.get(i));
                SPUtil.setSleepTime(time);
                SleepSettingsManager.postSleepTimeChanged(time);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sleep_spinner.setSelection(sleepList.indexOf(String.valueOf(SPUtil.getSleepTime())));

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
        adapter.setOnMenuItemClick(this);
    }

    @Override
    public void onContentClick(View view) {
        Log.w("test_bug","onContentClick");
    }

    @Override
    public void onMenuClick(int position, String tag) {
        Log.w("test_bug","onMenuClick position = " + position + " tag = " + tag);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
        SPUtil.setEnable(enabled);
        SleepSettingsManager.postEnableChanged(enabled);
    }
}
