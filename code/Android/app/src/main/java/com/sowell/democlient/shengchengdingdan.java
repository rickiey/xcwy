package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class shengchengdingdan extends AppCompatActivity {
    //设置引用
    private Intent intent;
    private TextView carnum;
    private TextView usename;
    private TextView usemobile;
    private TextView nowlocal;
    private TextView usecar;
    private TextView message;
    private TextView formtime;
    private Button submit;
    private TextView businessname1, businessphone1;
    private TextView type;
    private TextView price1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengchengdingdan);
        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = df.format(new Date());
        //建立映射
//        Log.e("zz", "");
        submit = (Button) findViewById(R.id.submit);
        usename = (TextView) findViewById(R.id.usename);
        carnum = (TextView) findViewById(R.id.carnum);
        usemobile = (TextView) findViewById(R.id.usemobile);
        nowlocal = (TextView) findViewById(R.id.nowlocal);
        usecar = (TextView) findViewById(R.id.usecar);
        message = (TextView) findViewById(R.id.message);
        formtime = (TextView) findViewById(R.id.formtime);
        businessname1 = (TextView) findViewById(R.id.businessname);
        type = (TextView) findViewById(R.id.type);
        price1 = (TextView) findViewById(R.id.price);
        businessphone1 = (TextView) findViewById(R.id.businessphone);
        Intent intent = getIntent();
        //获取页面信息
        String usename1 = intent.getStringExtra("usename");
        String carnum1 = intent.getStringExtra("usecarnum");
        String usecar1 = intent.getStringExtra("usecar");
        String usemobile1 = intent.getStringExtra("usemobile");
        String nowlocal1 = intent.getStringExtra("nowlocal");
        String message1 = intent.getStringExtra("message");
        String price = intent.getStringExtra("price");
        String businessname = intent.getStringExtra("businessname");
        String servicename = intent.getStringExtra("et_servicename");
        String phone = intent.getStringExtra("phone");
        //设置页面内容
        businessphone1.setText("商家电话：" + phone);
        usename.setText("车主姓名：" + usename1);
        usecar.setText("车        型：" + usecar1);
        usemobile.setText("车主电话：" + usemobile1);
        carnum.setText("车牌号码：" + carnum1);
        nowlocal.setText("故障地点：" + nowlocal1);
        message.setText("车主留言：" + message1);
        formtime.setText("订单时间：" + time);
        type.setText("服务类型：" + servicename);
        businessname1.setText("商家名称：" + businessname);
        price1.setText("价       钱：" + price);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Intent intent = getIntent();
                            //获取页面信息
                            String usename1 = intent.getStringExtra("usename");
                            String carnum1 = intent.getStringExtra("usecarnum");
                            String usecar1 = intent.getStringExtra("usecar");
                            String usemobile1 = intent.getStringExtra("usemobile");
                            String nowlocal1 = intent.getStringExtra("nowlocal");
                            String message1 = intent.getStringExtra("message");
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String time = df.format(new Date());
                            String businessname = intent.getStringExtra("businessname");
                            String servicename = intent.getStringExtra("et_servicename");
                            String price = intent.getStringExtra("price");
                            String conditon = intent.getStringExtra("condition");
                            String id = intent.getStringExtra("id");
                            String phone = intent.getStringExtra("phone");
                            SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                            Float x1 = sp.getFloat("endlatitude", 0);
                            Float y1 = sp.getFloat("endlongtitude", 0);
                            String bLocationx = x1.toString();
                            String bLocationy = y1.toString();
                            Float x = sp.getFloat("mlatitude", 0);
                            Float y = sp.getFloat("mlongtitude", 0);
                            String X = x.toString();
                            String Y = y.toString();

                            final String request = HttpUtils.post(Constants.setOrderForm_URL, buildJson(carnum1, usename1
                                    , usemobile1, nowlocal1, usecar1, message1
                                    , businessname, servicename, price, conditon, id, phone
                                    , X, Y, bLocationx, bLocationy));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (request.equals("1")) {
                                        Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                                        tiaozhuan();
                                    } else {
                                        Toast mToast = Toast.makeText(getApplicationContext(), "发送失败，请检查网络", Toast.LENGTH_LONG);
                                        mToast.show();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("asd", "asd");
                        }
                    }
                });
                t.start();
            }
        });
    }

    private String buildJson(String usecarnum, String usename
            , String usemobile, String nowlocal, String usecar, String message1
            , String businessname, String servicename, String price, String condition
            , String id, String phone, String X, String Y, String bLocationx, String bLocationy) {
        JSONObject object = new JSONObject();
        try {
            object.put("user_carnum", usecarnum);
            object.put("user_name", usename);
            object.put("user_mobile", usemobile);
            object.put("address", nowlocal);
            object.put("user_car", usecar);
            object.put("description", message1);
            object.put("business_name", businessname);
            object.put("business_id", id);
            object.put("business_phone", phone);
            object.put("type", servicename);
            object.put("price", price);
            object.put("condition", condition);
            object.put("X", X);
            object.put("Y", Y);
            object.put("bLocationx", bLocationx);
            object.put("bLocationy", bLocationy);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void tiaozhuan() {
        MainActivity.State.finish();
        WhichBusiness.State.finish();
        TransForm.State.finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        setOrderForm.SET_ORDER_THIS.finish();
        finish();
    }

    public void quxiao(View v) {
        setOrderForm.SET_ORDER_THIS.finish();
        finish();
    }

    public void CallPhone(View view) {
        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent1);
    }

}
