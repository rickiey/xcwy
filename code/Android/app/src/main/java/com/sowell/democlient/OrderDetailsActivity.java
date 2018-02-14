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

public class OrderDetailsActivity extends AppCompatActivity {
    //  初始化所需参数控件
    private String orderNum;
    private String userName;
    private String userMobile;
    private String timeStart;
    private String address;
    private String userCar;
    private String userCarNum;
    private String price;
    private String description;
    private String type;
    private String bLocationx;
    private String bLocationy;

    private TextView licNumText;
    private TextView orderNameText;
    private TextView phoneNumText;
    private TextView orderTimeText;
    private TextView addressText;
    private TextView modelText;
    private TextView orderNumText;
    private TextView serviceText;
    private TextView noteText;
    private TextView priceText;
    private TextView finishedTimeTextView;

    private Button mCallButton;
    private Button mNavigationButton;
    private Button mFinishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
//      初始化各控件
        licNumText = (TextView) findViewById(R.id.licnum);
        orderNameText = (TextView) findViewById(R.id.ordername);
        phoneNumText = (TextView) findViewById(R.id.phonenum);
        orderTimeText = (TextView) findViewById(R.id.ordertime);
        addressText = (TextView) findViewById(R.id.address);
        modelText = (TextView) findViewById(R.id.model);
        orderNumText = (TextView) findViewById(R.id.ordernum);
        serviceText = (TextView) findViewById(R.id.service);
        noteText = (TextView) findViewById(R.id.note);
        priceText = (TextView) findViewById(R.id.price);

        mCallButton = (Button) findViewById(R.id.call);
        mNavigationButton = (Button) findViewById(R.id.navigation);
        mFinishButton = (Button) findViewById(R.id.finish);
        Intent intent = getIntent();
        String res = intent.getStringExtra("orderdetails");

        try {
            JSONObject obj = new JSONObject(res);
            orderNum = obj.getString("id");
            bLocationx = obj.getString("x");
            bLocationy = obj.getString("y");
            userCarNum = obj.getString("user_carnum");
            userName = obj.getString("user_name");
            timeStart = obj.getString("timestart");
            userMobile = obj.getString("user_mobile");
            address = obj.getString("address");
            userCar = obj.getString("user_car");
            type = obj.getString("type");
            price = obj.getString("price");
            description = obj.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        licNumText.setText("车牌号码：" + userCarNum);
        orderNameText.setText("下单人：" + userName);
        phoneNumText.setText("手机号码：" + userMobile);
        orderTimeText.setText("下单时间：" + timeStart);
        addressText.setText("故障地点：" + address);
        modelText.setText("车辆型号：" + userCar);
        orderNumText.setText("订单号：" + orderNum);
        serviceText.setText("所需服务：" + type);
        noteText.setText("故障描述:" + description);
        priceText.setText("价格：" + price);
        Intent intent1 = new Intent(getApplicationContext(), MyService.class);
        stopService(intent1);
    }

    //联系车主按钮监听，拨打电话
    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.EMPTY.parse("tel:" + userMobile));
        startActivity(intent);
    }

    //导航按钮监听
    public void nav(View view) {
        SharedPreferences endlocation = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = endlocation.edit();
        editor1.putFloat("endlatitude", Float.parseFloat(bLocationx));
        editor1.putFloat("endlongtitude", Float.parseFloat(bLocationy));
        editor1.commit();
        Intent orderDetail = new Intent(this, routePlanActivity.class);
        startActivity(orderDetail);
    }

    //完成按钮监听，修改订单状态为已完成
    public void finish(View view) {
        final String id = orderNum;
        SharedPreferences sp1 = getSharedPreferences("data", MODE_PRIVATE);
        final String mobile = sp1.getString("mobile", "");//员工手机号
//        Log.e("订单号", id);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String res = HttpUtils.post(Constants.StaffFinishOrderServlet, buildJson(mobile, id));
//                        JSONObject obj = new JSONObject(res);
//                        boolean b = obj.getBoolean("result");
//                    System.out.println(res);
//                    Log.i("------------", res);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (res.equals("1")) {
                                Toast.makeText(OrderDetailsActivity.this, "订单已完成", Toast.LENGTH_SHORT).show();
//                                SharedPreferences sp = getSharedPreferences("orderStatus",MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sp.edit();
//                                editor.putString("status","完成");
                                Intent intent = new Intent(getApplicationContext(), MyService.class);
                                startService(intent);
                                finish();
                            } else if (res.equals("0")) {
                                Toast.makeText(OrderDetailsActivity.this, "发生错误", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(OrderDetailsActivity.this, "不知名错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    private String buildJson(String mobile, String id) {
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

}
