package com.jj.childmodel.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jj.childmodel.R;
import com.jj.childmodel.bean.WhiteTimeBean;
import com.jj.childmodel.menuadapter.MenuRecyclerAdapter;
import com.jj.childmodel.menuadapter.MenuScrollView;
import com.jj.childmodel.utils.ScreenUtil;

import java.util.List;

public class MyRecyclerViewAdapter extends MenuRecyclerAdapter {
    private int TYPE_ADD = 0;
    private int TYPE_ITEM = 1;
    private int width = ScreenUtil.getScreenWidth();

    private List<WhiteTimeBean> timeBeanList;
    public MyRecyclerViewAdapter(List<WhiteTimeBean> timeBeans){
        this.timeBeanList = timeBeans;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_ADD;
        }
        return TYPE_ITEM;
    }

    @Override
    public BaseMenuRecyclerHolder getContentView(Context context, View scrollView,int viewType) {
        if(viewType == TYPE_ADD){
            return new EmptyHolder(LayoutInflater.from(scrollView.getContext()).inflate(R.layout.layout_item_add,null,false));
        }else{
            View v = LayoutInflater.from(context).inflate(R.layout.layout_white_list_item,null,false);
            MenuScrollView _scrollView = (MenuScrollView) scrollView;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = 0;
            params.bottomMargin = 1;
            params.width = width;
            _scrollView.addContent(v,width,0,0,0,0);
            return new ItemHolder(scrollView,onMenuClick);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMenuRecyclerHolder baseMenuRecyclerHolder, int i) {
        if(baseMenuRecyclerHolder instanceof EmptyHolder){
            return;
        }
        ItemHolder itemHolder = (ItemHolder) baseMenuRecyclerHolder;
        itemHolder.timerStart.setText("11:11");
        itemHolder.timerEnd.setText("22:22");
        itemHolder.bindMyView(i);
        itemHolder.setPosition(i);
    }

    @Override
    public int getItemCount() {
        return timeBeanList.size();
    }

    public static class ItemHolder extends BaseMenuRecyclerHolder{
        private TextView timerStart,timerEnd;

        public ItemHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            timerStart = itemView.findViewById(R.id.timerStart);
            timerEnd = itemView.findViewById(R.id.timerEnd);
            addView("删除", "del",onClickListener);
        }
    }

    public static class EmptyHolder extends BaseMenuRecyclerHolder{

        public EmptyHolder(View itemView) {
            super(itemView);
        }
    }
}
