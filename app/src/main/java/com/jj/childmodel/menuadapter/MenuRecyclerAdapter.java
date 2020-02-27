package com.jj.childmodel.menuadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/8/6.
 */
public abstract class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.BaseMenuRecyclerHolder> {

    OnMenuItemClick onMenuItemClick;

    public void setOnMenuItemClick(OnMenuItemClick onMenuItemClick) {
        this.onMenuItemClick = onMenuItemClick;
    }

    public MenuRecyclerAdapter() {
    }

    @Override
    public BaseMenuRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MenuScrollView scrollView = new MenuScrollView(parent.getContext());
        BaseMenuRecyclerHolder baseRecyclerHolder = getContentView(parent.getContext(), scrollView, viewType);
        baseRecyclerHolder.setHandler(onContentClick);
        return baseRecyclerHolder;
    }

    public abstract BaseMenuRecyclerHolder getContentView(Context context, View scrollView, int viewType);

    public static class BaseMenuRecyclerHolder extends RecyclerView.ViewHolder {

        public BaseMenuRecyclerHolder(View itemView) {
            super(itemView);
        }

        public void setHandler(View.OnClickListener click) {
            if (itemView instanceof MenuScrollView) {
                MenuScrollView myScrollView = (MenuScrollView) itemView;
                myScrollView.getMyContentView().setOnClickListener(click);
            }
        }

        public void bindMyView(int position) {
            if (MenuScrollView.selectPosition == position) {
                showMenu();
            } else {
                dissMenu();
            }
        }

        public void showMenu() {
            MenuScrollView myScrollView = (MenuScrollView) itemView;
            myScrollView.showMenu();
        }

        public void dissMenu() {
            MenuScrollView myScrollView = (MenuScrollView) itemView;
            myScrollView.dissMenu();
        }

        public void addView(String text, Object tag, View.OnClickListener onClickListener) {
            MenuScrollView myScrollView = (MenuScrollView) itemView;
            myScrollView.addMenu(text, tag, onClickListener);
        }

        public void addView(String text, Object tag, int tvColor, int bgColor,View.OnClickListener onClickListener) {
            MenuScrollView myScrollView = (MenuScrollView) itemView;
            myScrollView.addMenu(text, tag, tvColor, bgColor, onClickListener);
        }

        public void setMenuVisibble(String tag, int visible) {
            MenuScrollView myScrollView = (MenuScrollView) itemView;
            myScrollView.setMenuVisible(tag, visible);
        }

        public void setPosition(int position) {
            MenuScrollView myScrollView = (MenuScrollView) itemView;
            myScrollView.setItemPosition(position);
        }
    }

    public interface OnMenuItemClick {

        void onContentClick(View view);

        void onMenuClick(int position, String tag);
    }

    private View.OnClickListener onContentClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onMenuItemClick == null) {
                return;
            }
            onMenuItemClick.onContentClick(view);
        }
    };

    public View.OnClickListener onMenuClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onMenuItemClick == null) {
                return;
            }
            View parent = (View) view.getParent();
            int position = (int) parent.getTag();
            onMenuItemClick.onMenuClick(position, (String) view.getTag());
        }
    };
}
