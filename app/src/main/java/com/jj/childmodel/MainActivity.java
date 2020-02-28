package com.jj.childmodel;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.jj.childmodel.adapters.MyRecyclerViewAdapter;
import com.jj.childmodel.adapters.MySpinnerAdapter;
import com.jj.childmodel.bean.WhiteTimeBean;
import com.jj.childmodel.menuadapter.MenuRecyclerAdapter;
import com.jj.childmodel.orm.DBManager;
import com.jj.childmodel.utils.SPUtil;
import com.jj.childmodel.utils.SleepSettingsManager;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements MenuRecyclerAdapter.OnMenuItemClick,CompoundButton.OnCheckedChangeListener,
        View.OnClickListener,TimePickerDialog.OnTimeSetListener{
    private Spinner time_spinner,sleep_spinner;
    private RecyclerView whiteRecyclerView;
    private SwitchCompat switchCompat;
    private List<String> usedList = Arrays.asList("10","20","30","40","50","60");
    private List<String> sleepList = Arrays.asList("1","5","10","15","20","30");
    private boolean isFirstKeepSelect = true;
    private boolean isFirstSleepSelect = true;
    private TimePickerDialog timePickerDialog;
    private WhiteTimeBean whiteTimeBean;
    private MyRecyclerViewAdapter whiteAdapter;

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

        List<WhiteTimeBean> timeBeans = DBManager.getWhitelistBeans();
        timeBeans.add(0,new WhiteTimeBean());

        whiteRecyclerView = findViewById(R.id.whiteRecyclerView);
        whiteRecyclerView.setHasFixedSize(true);
        whiteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        whiteAdapter = new MyRecyclerViewAdapter(timeBeans,this);
        whiteRecyclerView.setAdapter(whiteAdapter);
        whiteAdapter.setOnMenuItemClick(this);
    }

    @Override
    public void onContentClick(View view) {
        Log.w("test_bug","onContentClick");
        if(view.getId() == R.id.addMain){
            addNewWhite();
        }
    }

    @Override
    public void onMenuClick(int position, String tag) {
        Log.w("test_bug","onMenuClick position = " + position + " tag = " + tag);
        if(TextUtils.equals(tag,MyRecyclerViewAdapter.TAG_DEL)){
            whiteAdapter.removeData(position);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean enabled) {
        SPUtil.setEnable(enabled);
        SleepSettingsManager.postEnableChanged(enabled);
    }

    @Override
    public void onClick(View view) {
    }

    private void addNewWhite(){
        whiteTimeBean = new WhiteTimeBean();
        showTimerPickDialog("选择起始时间");
    }

    private void showTimerPickDialog(String title){
        if(timePickerDialog == null){
            timePickerDialog = new TimePickerDialog(this,this, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true);
        }
        timePickerDialog.setTitle(title);
        if(timePickerDialog.isShowing()){
            return;
        }
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if(whiteTimeBean.startHour == -1){
            whiteTimeBean.startHour = hour;
            whiteTimeBean.startMinute = minute;
            Observable.timer(200, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            showTimerPickDialog("请选择结束时间");
                        }
                    });
        }else{
            whiteTimeBean.endHour = hour;
            whiteTimeBean.endMinute = minute;
            DBManager.addWhiteBean(whiteTimeBean);
            whiteAdapter.addNewData(whiteTimeBean);
        }
    }
}
