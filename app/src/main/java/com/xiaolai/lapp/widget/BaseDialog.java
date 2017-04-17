package com.xiaolai.lapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import com.xiaolai.lapp.R;
import com.xiaolai.lapp.callback.OnButtonClickListener;

import java.security.KeyStore;

/**
 * Created by laizhibin on 2017/3/23.
 */
public class BaseDialog extends Dialog{

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{
        private Context context;
        private OnButtonClickListener mListener;
        private boolean dismissButton = false;
        private boolean showOneButton = false;
        private String sure,cancel;
        private int gravity = Gravity.CENTER;
        private View childView;
        private int left=120, top=0, right=120, bottom=0;
        private boolean canceledOnTouchOutside = true;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder dismissButton(){
            this.dismissButton = true;
            return this;
        }

        public Builder showOneButton(){
            this.showOneButton = true;
            return this;
        }

        public Builder showOneButton(String sure){
            this.sure = sure;
            this.showOneButton = true;
            return this;
        }

        public Builder showAllButton(String cancel, String sure){
            this.cancel = cancel;
            this.sure = sure;
            return this;
        }

        public Builder setGravity(int gravity){
            this.gravity = gravity;
            return this;
        }

        public Builder setOnButtonClickListener(OnButtonClickListener mListener) {
            this.mListener = mListener;
            return this;
        }

        public Builder setContentView(View childView){
            this.childView = childView;
            return this;
        }

        public Builder setPadding(int left, int top, int right, int bottom){
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public BaseDialog create(){
            final BaseDialog dialog = new BaseDialog(context, R.style.BaseDialogTheme);
            View view = View.inflate(context, R.layout.dialog_base, null);
            dialog.setContentView(view);
            Window window = dialog.getWindow();
            if(left!=0 || top!=0 || right!=0 || bottom!=0) {
                window.getDecorView().setPadding(left,top,right,bottom);
            }
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = gravity;
            window.setAttributes(lp);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            if(mListener != null){
                view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onCancel();
                    }
                });
                view.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onSure();
                    }
                });
            }
            if(dismissButton){
                view.findViewById(R.id.btn_cancel).setVisibility(View.GONE);
                view.findViewById(R.id.btn_sure).setVisibility(View.GONE);
            }else if(showOneButton){
                view.findViewById(R.id.btn_cancel).setVisibility(View.GONE);
            }
            ((Button)view.findViewById(R.id.btn_cancel)).setText(cancel);
            ((Button)view.findViewById(R.id.btn_sure)).setText(sure);
            if(childView != null){
                ((FrameLayout)view.findViewById(R.id.fl_container)).addView(childView);
            }
            return dialog;
        }
    }

}
