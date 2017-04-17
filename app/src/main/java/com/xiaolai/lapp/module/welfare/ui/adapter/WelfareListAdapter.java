package com.xiaolai.lapp.module.welfare.ui.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaolai.lapp.R;
import com.xiaolai.lapp.bean.ImageShowBean;
import com.xiaolai.lapp.common.Constant;
import com.xiaolai.lapp.utils.ImageLoadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class WelfareListAdapter<T extends ImageShowBean> extends BaseQuickAdapter<T , BaseViewHolder> {

    private boolean edit;
    private Map<ImageShowBean, Boolean> map;
    private Map<ImageShowBean, Integer> heights;

    public WelfareListAdapter(int layoutResId) {
        super(layoutResId);
        map = new HashMap();
        heights = new HashMap();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final T imageBean) {
        ImageView imageView = viewHolder.getView(R.id.iv_welfare_image);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        if(heights.containsKey(imageBean)){
            params.height = heights.get(imageBean);
        }else {
            heights.put(imageBean, (int)(250 + Math.random() * 200));
            params.height = heights.get(imageBean);
        }
        imageView.setLayoutParams(params);
        ImageLoadUtil.loadCenterCrop(imageBean.getImgPath(), imageView);
        if(TextUtils.isEmpty(imageBean.getImageDesc())) {
            //(最小值+Math.random()*(最大值-最小值+1))
            viewHolder.setText(R.id.tv_welfare_desc, Constant.poetry.get((int) (0 + Math.random() * (Constant.poetry.size() - 1 - 0 + 1))));
        }else{
            viewHolder.setText(R.id.tv_welfare_desc, imageBean.getImageDesc());
        }
        if(edit){
            viewHolder.setVisible(R.id.cb_select, true);
            viewHolder.setBackgroundRes(R.id.ll_bg, R.color.background_color);
            viewHolder.setChecked(R.id.cb_select,map.containsKey(imageBean)?map.get(imageBean):false);
        }else{
            viewHolder.setVisible(R.id.cb_select, false);
            viewHolder.setBackgroundRes(R.id.ll_bg,R.drawable.ripple_item_bg);
            viewHolder.setChecked(R.id.cb_select,false);
        }
        ((CheckBox)viewHolder.getView(R.id.cb_select)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleCheckedChanged(imageBean,isChecked);
            }
        });
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
        if(!edit){
            map.clear();
        }
        notifyDataSetChanged();
    }
    public boolean isEdit() {
        return edit;
    }

    public void toggleItemChecked(int position, BaseViewHolder holder) {
        boolean isChecked = map.containsKey(getData().get(position))?map.get(getData().get(position)):false;
        holder.setChecked(R.id.cb_select, !isChecked);
        handleCheckedChanged(getData().get(position), !isChecked);
        // 如果用 notifyItemChanged()，会有一闪的情况
//        notifyItemChanged(position);
    }

    private void handleCheckedChanged(ImageShowBean bean, boolean b) {
        map.put(bean, b);
    }

    public void setAllSelect(boolean allSelect){
        for(ImageShowBean imageBean : getData()){
            if(allSelect){
                map.put(imageBean, true);
            }else{
                map.put(imageBean, false);
            }
        }
    }

    public int getSelectCount(){
        int count = 0;
        for(ImageShowBean imageBean : map.keySet()){
            if(map.get(imageBean)){
                count++;
            }
        }
        return count;
    }

    public List<ImageShowBean> getSelectData(){
        List<ImageShowBean> list = new ArrayList<>();
        for(ImageShowBean imageBean : map.keySet()){
            if(map.get(imageBean)){
                list.add(imageBean);
            }
        }
        return list;
    }
}
