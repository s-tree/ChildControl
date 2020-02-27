package com.jj.childmodel.adapters;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jj.childmodel.R;

import java.util.List;

public class MySpinnerAdapter implements SpinnerAdapter {
    private List<String> datas;

    public MySpinnerAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_spinner_item,null,false);
        }
        TextView textView = view.findViewById(R.id.text_item);
        textView.setText(datas.get(i));
        return view;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_spinner_item,null,false);
        }
        TextView textView = view.findViewById(R.id.text_item);
        textView.setText(datas.get(i));
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return datas.isEmpty();
    }
}