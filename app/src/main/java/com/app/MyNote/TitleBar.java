package com.app.MyNote;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleBar extends RelativeLayout {
    private TextView title;
    private ImageView back;

    public TitleBar(Context context, AttributeSet attrs){
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(final Context context, AttributeSet attributeSet){
        View inflate = LayoutInflater.from(context).inflate(R.layout.main_titlebar, this);
        title = inflate.findViewById(R.id.titlebar_title);
        back = inflate.findViewById(R.id.titlebar_back);


        init(context, attributeSet);
    }

    public void init(Context context, AttributeSet attributeSet){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TitleBar);
        String titletxt = typedArray.getString(R.styleable.TitleBar_title);//标题
        int titleBarType = typedArray.getInt(R.styleable.TitleBar_type, 0);//标题栏类型,默认为10
        //赋值进去标题栏
        title.setText(titletxt);

        if (titleBarType == 0){
            title.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        }else if(titleBarType == 1){
            title.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }

    }
    public void setBackOnClickListener(OnClickListener l){
        back.setOnClickListener(l);
    }
}
