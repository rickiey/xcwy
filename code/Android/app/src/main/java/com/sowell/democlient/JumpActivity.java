package com.sowell.democlient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class JumpActivity extends AppCompatActivity {
    private Button car, psw, cancle, orderinfomation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        //    初始化组件参数
        car = (Button) findViewById(R.id.mycar);
        psw = (Button) findViewById(R.id.changepassword);
        cancle = (Button) findViewById(R.id.cancle);
        orderinfomation = (Button) findViewById(R.id.orderinfomation);

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpActivity.this, Mycar.class);
                startActivity(intent);
            }
        });

        psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpActivity.this, Changepassword.class);
                startActivity(intent);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sp=getSharedPreferences("data2", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor=sp.edit();
//                editor.clear();
//                editor.commit();
                clear();
                Intent intent = new Intent(JumpActivity.this, UserLogin.class);
                MainActivity.State.finish();
                startActivity(intent);
                finish();
            }
        });

        orderinfomation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JumpActivity.this, MyOrder.class);
                startActivity(intent);
            }
        });
    }

    protected void clear() {
        // TODO Auto-generated method stub
        SharedPreferences sp = getSharedPreferences("data2", Context.MODE_PRIVATE);
//        Log.e("134513_________", sp.getAll().get("mobile").toString());
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
