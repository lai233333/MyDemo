package com.xiaolai.lapp.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.R;

/**
 * Created by laizhibin on 2017/3/24.
 */
public class ListPopWindow extends PopupWindow {

    private Context mContext;
    private RecyclerView mListView;
    private BaseQuickAdapter mAdapter;


    public ListPopWindow(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public void setAdapter(BaseQuickAdapter<String, BaseViewHolder> adapter){
        mAdapter = adapter;
        mListView.setLayoutManager(new LinearLayoutManager(mContext));
        mListView.setAdapter(mAdapter);
    }

    private void init()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_list, null);
        setContentView(view);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);
        mListView = (RecyclerView) view.findViewById(R.id.rv_list);
    }

}
