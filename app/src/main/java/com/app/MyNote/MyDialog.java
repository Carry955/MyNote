package com.app.MyNote;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyDialog extends Dialog {

    private Button dia_yes, dia_cancel;
    private TextView dia_title, dia_message;
    private String titlestr, messagestr;
    private String cancelStr, yesStr;

    private onCancelclickListener cancelonclickListener;
    private onYesclickListener yesonclickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydialog);

        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
        //初始化界面数据
        initData();
    }
        public void setOnCancelclickListener(String str, onCancelclickListener oncancelclickListener) {
            if (str != null) {
                cancelStr = str;
            }
            this.cancelonclickListener = oncancelclickListener;
        }
        public void setOnYesclickListener(String str, onYesclickListener onyesclickListener) {
            if (str != null) {
                yesStr = str;
            }
            this.yesonclickListener = onyesclickListener;
        }

        public MyDialog(Context context) {
            super(context, R.style.MyDialog);
        }

        private void initView(){
            dia_title = findViewById(R.id.dialog_title);
            dia_message = findViewById(R.id.dialog_message);
            dia_yes = findViewById(R.id.yes);
            dia_cancel = findViewById(R.id.cancel);
        }
        public void setTitle(String title){
            titlestr = title;
        }
        public void setMessage(String message){
            messagestr = message;
        }
        public interface onCancelclickListener{
            public void onCancelClick();
        }
        public interface onYesclickListener{
            public void onYesClick();
        }

        private void initEvent() {
            //设置确定按钮被点击后，向外界提供监听
            dia_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (yesonclickListener != null) {
                        yesonclickListener.onYesClick();
                    }
                    dismiss();
                }
            });
            //设置取消按钮被点击后，向外界提供监听
            dia_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cancelonclickListener != null) {
                        cancelonclickListener.onCancelClick();
                    }
                    dismiss();
                }
            });
        }

        //初始化界面控件的显示数据
        private void initData() {
            //如果用户自定了title和message
            if (titlestr != null) {
                dia_title.setText(titlestr);
            }
            if (messagestr!= null) {
                dia_message.setText(messagestr);
            }
            //如果设置按钮的文字
            if (yesStr != null) {
                dia_yes.setText(yesStr);
            }
            if (cancelStr != null) {
                dia_cancel.setText(cancelStr);
            }
        }
}
