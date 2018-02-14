package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class NoOrder extends AppCompatActivity {
//    定义所需参数变量
    private Intent intent;
    private TextView carnum, ordernum, businessphone;
    private TextView usename;
    private TextView usemobile;
    private TextView nowlocal;
    private TextView usecar;
    private TextView message;
    private TextView formtime;
    private Button cancel;
    private Button cance2;
    private TextView businessname1;
    private TextView type;
    private TextView price1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_order);
//        初始化各参数变量
        cancel = (Button) findViewById(R.id.cancel);
        cance2 = (Button) findViewById(R.id.cance2);
        usename = (TextView) findViewById(R.id.usename);
        carnum = (TextView) findViewById(R.id.carnum);
        usemobile = (TextView) findViewById(R.id.usemobile);
        nowlocal = (TextView) findViewById(R.id.nowlocal);
        usecar = (TextView) findViewById(R.id.usecar);
        message = (TextView) findViewById(R.id.message);
        formtime = (TextView) findViewById(R.id.formtime);
        businessname1 = (TextView) findViewById(R.id.businessname);
        businessphone = (TextView) findViewById(R.id.businessphone);
        ordernum = (TextView) findViewById(R.id.ordernum);
        type = (TextView) findViewById(R.id.type);
        price1 = (TextView) findViewById(R.id.price);
        intent = getIntent();
        String usename1 = intent.getStringExtra("user_name");
        String carnum1 = intent.getStringExtra("user_carnum");
        String usecar1 = intent.getStringExtra("user_car");
        String usemobile1 = intent.getStringExtra("user_mobile");
        String nowlocal1 = intent.getStringExtra("address");
        String message1 = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String businessname = intent.getStringExtra("business_name");
        String businessphone1 = intent.getStringExtra("business_phone");
        String timestart = intent.getStringExtra("timestart");
        final String id = intent.getStringExtra("id");
        String servicename = intent.getStringExtra("type");
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
        businessphone.setText("商家电话：" + businessphone1);
        ordernum.setText("订单编号：" + intent.getStringExtra("id"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                            String mobile = sp.getString("mobile", "");
//                            Log.e("获取的电话", mobile);
//                            Log.e("获取的ID", id);
                            final String Inform = HttpUtils.post(Constants.Cancel_URL, buildJson(mobile, id));
//                            Log.e("取消订单返回信息", Inform);
                            if (Inform.equals("1")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(NoOrder.this, MyOrder.class);
                                        startActivity(intent);
                                        MyOrder.MYORDER_THIS.finish();
                                        finish();
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "检查网络", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                            Log.e("取消订单失败", "");
                        }
                    }
                });
                t.start();
            }
        });

        cance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoOrder.this, routePlanActivity.class);
                startActivity(intent);
            }
        });
    }

    public String buildJson(String mobile, String id) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobile);
            object.put("id", id);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void CallPhone(View view) {
        Intent intent = getIntent();
        String phone = intent.getStringExtra("business_phone");
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent1);
    }

}
