package com.sowell.democlient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class UserLogin extends AppCompatActivity {
//  定义所需变量及组件
    private Button btn_staff, btn_regist, btn_forget, btn_login;
    private EditText et_number, et_password;
    public static UserLogin State = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        State = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        //初始化界面
        initView();
        Map<String, String> userInfo = Save.getUserInfo(this);
        Map<String, String> userLoginInfo = Save.getLoginInfo(this);
        if (userInfo != null && userLoginInfo != null) {
            et_number.setText(userInfo.get("mobile"));
            et_password.setText(userInfo.get("password"));
           /* Intent intent = new Intent(UserLogin.this, MainActivity.class);
            startActivity(intent);
            finish();*/
        }
    }

    private void initView() {

        //员工登录跳转
        btn_staff = (Button) findViewById(R.id.staff_btn);
        btn_staff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                jumpstaff();
                Intent intent = new Intent(getApplicationContext(), StaffLogin.class);
                startActivity(intent);
            }
        });
        //注册跳转
        btn_regist = (Button) findViewById(R.id.user_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                jumpregist();
            }
        });
        //忘记密码跳转
        btn_forget = (Button) findViewById(R.id.forget_btn);
        btn_forget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                jumpforget();
            }
        });
        //登录跳转
        btn_login = (Button) findViewById(R.id.btn_userlogin);
        et_number = (EditText) findViewById(R.id.user_number);
        et_password = (EditText) findViewById(R.id.user_password);
        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //登录处理
                if (et_number.getText().toString().equals("") || (et_password.getText().toString().equals(""))) {
                    Toast mToast = Toast.makeText(getApplicationContext(), "请输入账号密码", Toast.LENGTH_LONG);
                    mToast.show();
                } else {
                    final String mobile = et_number.getText().toString();
                    final String password = et_password.getText().toString();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //HttpUtils.post(Constants.LOGIN_URL, buildJson(mobile,password));
                                //获取返回数据
                                final String result = HttpUtils.post(Constants.LOGIN_URL, buildJson(mobile, password));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (result.equals("1")) {
//                                            Log.e("5555555555555",result);
                                            Toast mToast = Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG);
                                            mToast.show();
                                            //跳转向下一个Activity发送数据
                                            Intent intent = new Intent(UserLogin.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                            //保存账号密码
                                            Save.saveUserInfo(UserLogin.this, mobile, password);
                                            Save.saveLognInfo(UserLogin.this, mobile, password);
                                        } else if (result.equals("-1")) {
//                                            Log.e("5555555555555", result);
                                            Toast mToast = Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG);
                                            mToast.show();
                                        } else if (result.equals("0")) {
//                                            Log.e("5555555555555", result);
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
        });
    }

//    public void jumpstaff() {
//        Intent intent = new Intent(this, StaffLogin.class);
//        startActivity(intent);
//    }

    public void jumpregist() {
        Intent intent = new Intent(this, Regist.class);
        startActivity(intent);
    }

    public void jumpforget() {
        Intent intent = new Intent(this, Forget.class);
        startActivity(intent);
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
