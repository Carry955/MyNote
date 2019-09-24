package com.app.MyNote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    ListView lv;
    LayoutInflater inflater;
    ArrayList<Cuns> array;
    MyDataBase mdb;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=(ListView) findViewById(R.id.lv_bwlList);
        imageButton=(ImageButton) findViewById(R.id.btnAdd);
        inflater=getLayoutInflater();
        //判断API并设置状态栏字体颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取窗口区域
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置显示为白色背景，黑色字体
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        mdb=new MyDataBase(this);
        array=mdb.getArray();
        MyAdapter adapter=new MyAdapter(inflater,array);
        lv.setAdapter(adapter);

        /*
         * 点击listView里面的item,用来修改日记
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(getApplicationContext(),NewNote.class);
                intent.putExtra("ids",array.get(position).getIds() );
                startActivity(intent);
                MainActivity.this.finish();
                overridePendingTransition(R.anim.in_from_main,R.anim.out_from_main);
            }
        });
        /*
         * 长点后来判断是否删除数据
         */
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                MyDialog mydialog = new MyDialog(MainActivity.this);
                mydialog.setTitle("删除便笺");
                mydialog.setMessage("确认要删除此便笺?");
                mydialog.setOnCancelclickListener("取消", new MyDialog.onCancelclickListener() {
                    @Override
                    public void onCancelClick() {

                    }
                });
                mydialog.setOnYesclickListener("删除", new MyDialog.onYesclickListener() {
                    @Override
                    public void onYesClick() {
                        mdb.toDelete(array.get(position).getIds());
                        array=mdb.getArray();
                        MyAdapter adapter=new MyAdapter(inflater,array);
                        lv.setAdapter(adapter);
                    }
                });
                mydialog.show();
                return true;
            }
        });
        /*
         * 按钮点击事件，用来新建日记
         */
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewNote.class);
                startActivity(intent);
                MainActivity.this.finish();
                overridePendingTransition(R.anim.in_from_main,R.anim.out_from_main);
            }
        });

        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    ((ImageButton)v).setBackground(getResources().getDrawable(R.mipmap.add_down));
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    ((ImageButton)v).setBackground(getResources().getDrawable(R.mipmap.add));
                }
                return false;
            }
        });
    }

    private Activity getActivity() {
        return this;
    }
}