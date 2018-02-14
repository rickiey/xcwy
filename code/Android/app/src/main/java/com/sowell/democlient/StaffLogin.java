package com.sowell.democlient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sowell.democlient.utils.HttpUtils;
import com.sowell.democlient.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class StaffLogin extends AppCompatActivity {
    //    定义所需变量和组件
    private EditText staffnumber;
    private EditText staffpassword;
    private Button stafflogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);
        //初始化界面
        initView();
        Map<String, String> userInfo = Save.getUserInfo(this);
        Map<String, String> userloginInfo = Save.getLoginInfo(this);
        if (userInfo != null && userloginInfo != null) {
            staffnumber.setText(userInfo.get("mobile"));
            staffpassword.setText(userInfo.get("password"));
        }
    }

    private void initView() {
//        初始化各组件信息
        staffnumber = (EditText) findViewById(R.id.staff_number);
        staffpassword = (EditText) findViewById(R.id.staff_password);
        stafflogin = (Button) findViewById(R.id.btn_stafflogin);
        stafflogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {

        if (staffnumber.getText().toString().equals("") || (staffpassword.getText().toString().equals(""))) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入账号密码", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            final String mobile = staffnumber.getText().toString();
            final String password = staffpassword.getText().toString();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        //获取返回数据

                        final String result = HttpUtils.post(Constants.STAFF_LOGIN_URL, buildJson(mobile, password));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("1".equals(result)) {
                                    Toast mToast = Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT);
                                    mToast.show();
                                    //保存账号密码
                                    Save.saveUserInfo(StaffLogin.this, mobile, password);
                                    Save.saveLognInfo(StaffLogin.this, mobile, password);
                                    //跳转向下一个Activity发送数据
                                    Intent intent = new Intent(StaffLogin.this, EmployeesMainActivity.class);
                                    UserLogin.State.finish();
                                    startActivity(intent);
                                    finish();
                                } else if ("-1".equals(result)) {
                                    Toast mToast = Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG);
                                    mToast.show();
                                } else if ("0".equals(result)) {
                                    Toast mToast = Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG);
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

    private String buildJson(String mobile, String password) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobile);
            object.put("password", password);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
