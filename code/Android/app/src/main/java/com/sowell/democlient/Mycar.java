package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Mycar extends AppCompatActivity {
//    定义布局文件所需组件
    private EditText name, car, carnum, usemobile;
    private Button change;
    public static Mycar State=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        State = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycar);
//        初始化各布局文件
        name = (EditText) findViewById(R.id.user_name);
        car = (EditText) findViewById(R.id.user_car);
        carnum = (EditText) findViewById(R.id.user_carnum);
        usemobile = (EditText) findViewById(R.id.user_mobile);
        change = (Button) findViewById(R.id.change);
        name.setFocusable(false);
        name.setFocusableInTouchMode(false);
        car.setFocusable(false);
        car.setFocusableInTouchMode(false);
        carnum.setFocusable(false);
        carnum.setFocusableInTouchMode(false);
        usemobile.setFocusableInTouchMode(false);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                    String mobile = sp.getString("mobile", "");
                    Log.e("获取的电话号码----------", mobile);
                    final String Inform = HttpUtils.post(Constants.getInform_URL, buildJson(mobile));
                    JSONObject jbt = new JSONObject(Inform);
                    final String usename = jbt.getString("name");
                    Log.e("车主姓名---", usename);
                    final String mobilenum = jbt.getString("mobile");
                    final String usecar = jbt.getString("car");
                    final String usecarnum = jbt.getString("carnum");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            name.setText(usename);
                            car.setText(usecar);
                            carnum.setText(usecarnum);
                            usemobile.setText(mobilenum);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Mycar.this, Changecar.class);
                startActivity(intent);

            }
        });
    }

    public String buildJson(String mobile) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobile);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
