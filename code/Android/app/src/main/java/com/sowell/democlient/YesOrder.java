package com.sowell.democlient;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YesOrder extends AppCompatActivity {
    //    创建自定义参数和组件
    private Intent intent;
    private TextView carnum, timeend, ordernum, businessphone;
    private TextView usename;
    private TextView usemobile;
    private TextView nowlocal;
    private TextView usecar;
    private TextView message;
    private TextView formtime;
    private Button submit;
    private TextView businessname1;
    private TextView type;
    private TextView price1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_order);
//        初始化组件
        submit = (Button) findViewById(R.id.submit);
        usename = (TextView) findViewById(R.id.usename);
        carnum = (TextView) findViewById(R.id.carnum);
        usemobile = (TextView) findViewById(R.id.usemobile);
        nowlocal = (TextView) findViewById(R.id.nowlocal);
        usecar = (TextView) findViewById(R.id.usecar);
        message = (TextView) findViewById(R.id.message);
        formtime = (TextView) findViewById(R.id.formtime);
        timeend = (TextView) findViewById(R.id.timeend);
        businessname1 = (TextView) findViewById(R.id.businessname);
        businessphone = (TextView) findViewById(R.id.businessphone);
        ordernum = (TextView) findViewById(R.id.ordernum);
        type = (TextView) findViewById(R.id.type);
        price1 = (TextView) findViewById(R.id.price);
        Intent intent = getIntent();
        String usename1 = intent.getStringExtra("user_name");
        String carnum1 = intent.getStringExtra("user_carnum");
        String usecar1 = intent.getStringExtra("user_car");
        String usemobile1 = intent.getStringExtra("user_mobile");
        String nowlocal1 = intent.getStringExtra("address");
        String message1 = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String businessname = intent.getStringExtra("business_name");
        String timeend1 = intent.getStringExtra("timeend");
        String servicename = intent.getStringExtra("type");
        String timestart = intent.getStringExtra("timestart");
        //设置页面内容
        usename.setText("车主姓名：" + usename1);
        usecar.setText("车        型：" + usecar1);
        usemobile.setText("车主电话：" + usemobile1);
        carnum.setText("车牌号码：" + carnum1);
        nowlocal.setText("故障地点：" + nowlocal1);
        message.setText("车主留言：" + message1);
        formtime.setText("订单时间：" + timestart);
        type.setText("服务类型：" + servicename);
        businessname1.setText("商家名称：" + businessname);
        price1.setText("价       钱：" + price);
        timeend.setText("结束时间：" + timeend1);
        ordernum.setText("订单编号：" + intent.getStringExtra("id"));
        businessphone.setText("商家电话：" + intent.getStringExtra("business_phone"));
    }

    public void CallPhone(View view) {
        Intent intent = getIntent();
        String phone = intent.getStringExtra("business_phone");
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent1);
    }
    
}
