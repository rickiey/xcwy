package com.sowell.democlient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Changecar extends AppCompatActivity {
    private EditText name, car, carnumber;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changecar);
        name = (EditText) findViewById(R.id.change_name);
        car = (EditText) findViewById(R.id.change_car);
        carnumber = (EditText) findViewById(R.id.change_carnumber);
        save = (Button) findViewById(R.id.change_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals("")) {
                    Toast mToast = Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_LONG);
                    mToast.show();
                } else if (car.getText().toString().equals("")) {
                    Toast mToast = Toast.makeText(getApplicationContext(), "请输入车辆类型", Toast.LENGTH_LONG);
                    mToast.show();
                } else if (carnumber.getText().toString().equals("")) {
                    Toast mToast = Toast.makeText(getApplicationContext(), "请输入车牌号码", Toast.LENGTH_LONG);
                    mToast.show();
                } else {
                    SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                    final String mobile = sp.getString("mobile", "");
                    final String mname = name.getText().toString();
                    final String mcar = car.getText().toString();
                    final String carnum = carnumber.getText().toString();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //HttpUtils.post(Constants.REGISTER_URL, buildJson(mobile,password,mname,mcar,carnum));
                                //获取返回数据
                                final String result = HttpUtils.post(Constants.CHANGE_CAR_URL, buildJson(mobile, mname, mcar, carnum));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (result.equals("1")) {
//                                            Log.e("------------", result);
                                            Toast mToast = Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG);
                                            mToast.show();
                                            //跳转向下一个Activity发送数据
                                            Mycar.State.finish();
                                            finish();
                                        } else if (result.equals("0")) {
                                            Log.e("------------", result);
                                            Toast mToast = Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG);
                                            mToast.show();
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
            }
        });
    }

    private String buildJson(String mobile, String mname, String mcar, String carnum) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobile);
            object.put("name", mname);
            object.put("car", mcar);
            object.put("carnum", carnum);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
