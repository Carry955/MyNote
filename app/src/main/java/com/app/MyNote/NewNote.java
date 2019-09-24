package com.app.MyNote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class NewNote extends AppCompatActivity {

    TextView Bar_title, time;
    EditText ed1,ed2;
    ImageButton imageButton;
    ImageView backButton;
    MyDataBase myDatabase;
    Cuns cun;
    int ids;
    String old_time, times;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_new_note);
        ed1= findViewById(R.id.editText1);
        ed2= findViewById(R.id.editText2);
        imageButton= findViewById(R.id.saveButton);
        Bar_title = findViewById(R.id.titlebar_title);
        backButton = findViewById(R.id.titlebar_back);
        time = findViewById(R.id.time);
        myDatabase=new MyDataBase(this);

        //判断API并设置状态栏字体颜色
        getActivity().getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取窗口区域
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置显示为白色背景，黑色字体
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        times = formatter.format(curDate);
        Intent intent=this.getIntent();
        ids=intent.getIntExtra("ids", 0);
        //默认为0，不为0,则为修改数据时跳转过来的
        if(ids!=0){
            cun=myDatabase.getTiandCon(ids);
            ed1.setText(cun.getTitle());
            ed2.setText(cun.getContent());
            Bar_title.setText(cun.getTitle());
            old_time = cun.getTimes();
            time.setText(old_time);
        }else{
            time.setText(times);
        }
        //按钮按下弹起改变颜色
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    ((ImageButton)v).setBackground(getResources().getDrawable(R.mipmap.save_down));
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    ((ImageButton)v).setBackground(getResources().getDrawable(R.mipmap.save));
                }
                return false;
            }
        });

        //保存按钮的点击事件，他和返回按钮是一样的功能，所以都调用isSave()方法；
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSave();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ids!=0){
                    time.setText(old_time+" | "+s.length()+"字");
                }else{
                    time.setText(times+" | "+s.length()+"字");
                }
            }
        });
        SoftHideKeyBoardUtil.assistActivity(this);
    }

    private Activity getActivity() {
        return this;
    }

    //返回按钮调用的方法
    @Override
    public void onBackPressed() {
        String title= ed1.getText().toString();
        String content= ed2.getText().toString();
        //是要修改数据
        if(ids!=0){
            cun=new Cuns(title,ids, content, old_time);
            myDatabase.toUpdate(cun);
            Intent intent=new Intent(NewNote.this,MainActivity.class);
            startActivity(intent);
            NewNote.this.finish();
            overridePendingTransition(R.anim.in_from_new,R.anim.out_form_new);
        }
        //新建日记
        else{
            if(title.equals("")&&content.equals("")){
                Intent intent=new Intent(NewNote.this,MainActivity.class);
                startActivity(intent);
                NewNote.this.finish();
                overridePendingTransition(R.anim.in_from_new,R.anim.out_form_new);
            }
            else{
                cun=new Cuns(title,content,times);
                myDatabase.toInsert(cun);
                Intent intent=new Intent(NewNote.this,MainActivity.class);
                startActivity(intent);
                NewNote.this.finish();
                overridePendingTransition(R.anim.in_from_new,R.anim.out_form_new);
            }

        }
    }

    private void isSave(){
        String title=ed1.getText().toString();
        String content=ed2.getText().toString();
        //要修改数据
        if(ids!=0){
            cun=new Cuns(title,ids, content, old_time);
            myDatabase.toUpdate(cun);
            Intent intent=new Intent(NewNote.this,MainActivity.class);
            startActivity(intent);
            NewNote.this.finish();
            overridePendingTransition(R.anim.in_from_new,R.anim.out_form_new);
        }
        //新建日记
        else{
            cun=new Cuns(title,content,times);
            myDatabase.toInsert(cun);
            Intent intent=new Intent(NewNote.this, MainActivity.class);
            startActivity(intent);
            NewNote.this.finish();
            overridePendingTransition(R.anim.in_from_new,R.anim.out_form_new);
        }
    }

}