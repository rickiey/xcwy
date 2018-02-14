package com.sowell.democlient;

import android.content.SharedPreferences;
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

import static com.sowell.democlient.Regist.isPasswordNO;

public class Changepassword extends AppCompatActivity {
    private EditText old, new1, new2;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        old = (EditText) findViewById(R.id.old_password);
        new1 = (EditText) findViewById(R.id.new_password1);
        new2 = (EditText) findViewById(R.id.new_password2);
        save = (Button) findViewById(R.id.password_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clecked1();
            }
        });
        new2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean lostFocus) {
                if (lostFocus) {
                } else {
                    if (!new1.getText().toString().equals(new2.getText().toString())) {
                        Toast mToast1 = Toast.makeText(getApplicationContext(), "两次输入密码不同", Toast.LENGTH_LONG);
                        mToast1.show();
                    }
                }
            }
        });
    }

    public void clecked1() {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        String oldpsw = sp.getString("password", "");
//        Log.e("222222222", oldpsw);
        if (old.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入原密码", Toast.LENGTH_LONG);
            mToast.show();
        } else if (new1.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入新密码", Toast.LENGTH_LONG);
            mToast.show();
        } else if (new2.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请确认密码", Toast.LENGTH_LONG);
            mToast.show();
        } else if (!oldpsw.equals(old.getText().toString())) {
            Toast mToast = Toast.makeText(getApplicationContext(), "原密码错误", Toast.LENGTH_LONG);
            mToast.show();
        } else if (oldpsw.equals(new1.getText().toString())) {
            Toast mToast = Toast.makeText(getApplicationContext(), "新旧密码不能相同", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            if (new1.getText().toString().equals(new2.getText().toString()) &&
                    judgepassword() &&
                    !oldpsw.equals(new1.getText().toString()) &&
                    oldpsw.equals(old.getText().toString())) {
//                Log.e("0000000000", "1111111");
                SharedPreferences sp1 = getSharedPreferences("data", MODE_PRIVATE);
                final String mobile = sp1.getString("mobile", "");
//                Log.e("1111111111", mobile);
                final String password = new1.getText().toString();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //HttpUtils.post(Constants.REGISTER_URL, buildJson(mobile,password,mname,mcar,carnum));
                            //获取返回数据
//                            Log.e("xxxxxxxx", "ccccccc");
//                            Log.e("mobile", mobile);
//                            Log.e("password", password);
                            final String result = HttpUtils.post(Constants.CHANGE_PASSWORD_URL, buildJson(mobile, password));
//                            Log.e("dddd", result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Log.e("ssssssssss", "aaaaaaaaaaaaa");
                                    if (result.equals("1")) {
//                                        Log.e("------------", result);
                                        Toast mToast = Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG);
                                        mToast.show();
                                        //跳转向下一个Activity发送数据
                                        finish();
                                    } else if (result.equals("0")) {
//                                        Log.e("------------", result);
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


    }

    public boolean judgepassword() {
        String num = new1.getText().toString();
        boolean jud = isPasswordNO(num);
        if (jud) {
            return true;
        } else {
            Toast mToast = Toast.makeText(getApplicationContext(), "密码格式有误", Toast.LENGTH_LONG);
            mToast.show();
            return false;
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