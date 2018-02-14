package com.sowell.democlient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ServiceDetails extends AppCompatActivity {
    //    定义所需变量和控件
    private String buyState, serviceName, serviceDescription;
    Intent transForm;
    private TextView buy_state;
    private TextView service_name;
    private TextView service_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_details);
        init();
        fillData();
    }

    //初始化各控件信息
    private void init() {
        transForm = getIntent();    //获得上层意图信息
        buyState = transForm.getStringExtra("buy_state");   //存储服务预约信息变量，“已预约”和“未预约”
        serviceName = transForm.getStringExtra("buy_name");     //服务名称
        serviceDescription = transForm.getStringExtra("buy_description");   //服务描述
        buy_state = (TextView) findViewById(R.id.buy_state_sd); //初始化服务状态栏
        service_name = (TextView) findViewById(R.id.service_name);  //初始化服务名称栏
        service_info = (TextView) findViewById(R.id.service_info);     //初始化服务信息栏
    }

//    设置页面展示信息
    private void fillData() {
        service_name.setText("服务名称：" + serviceName);    //服务名称设置
        service_info.setText("服务详情：" + serviceDescription); //服务描述设置
//        服务状态设置
        if (buyState.equals("未预约")) {
            buy_state.setText("未预约");
        } else if (buyState.equals("已预约")) {
            buy_state.setText("已预约");
        }
    }

}
