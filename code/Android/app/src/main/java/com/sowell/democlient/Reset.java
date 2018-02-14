package com.sowell.democlient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Reset extends AppCompatActivity {
//    定义所需变量及控件
    private EditText pass1, pass2;
    private Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
//        初始化控件
        pass1 = (EditText) findViewById(R.id.newpassword1);
        pass2 = (EditText) findViewById(R.id.newpassword2);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetpass();
            }
        });


    }

    public void resetpass() {

        if (pass1.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_LONG);
            mToast.show();
        } else if (pass2.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请确认密码", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            if (!pass1.getText().toString().equals(pass2.getText().toString())) {
                Toast mToast = Toast.makeText(getApplicationContext(), "两次输入密码不同", Toast.LENGTH_LONG);
                mToast.show();
            } else {
                if (judgepassword() && pass1.getText().toString().equals(pass2.getText().toString())) {
                    Intent intent = getIntent();
                    final String mobile = intent.getStringExtra("mobile");
                    final String password = pass1.getText().toString();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //HttpUtils.post(Constants.RESET_URL, buildJson(mobile,newpassword));
                                //获取返回数据
                                final String result = HttpUtils.post(Constants.RESET_URL, buildJson(mobile, password));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if ("1".equals(result)) {
                                            Toast mToast = Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_LONG);
                                            mToast.show();
                                            Intent intent = new Intent(Reset.this, MainActivity.class);
                                            startActivity(intent);
                                            UserLogin.State.finish();
                                            finish();
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
    }

    public boolean judgepassword() {
        String num = pass1.getText().toString();
        boolean jud = isPasswordNO(num);
        if (jud) {
            return true;
        } else {
            Toast mToast = Toast.makeText(getApplicationContext(), "密码格式有误", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }
    }

    public static boolean isPasswordNO(String password) {
        //字母开头，包括字母，数字和下划线，6-18位
        String passwordRegex = "[A-Za-z]{1}[A-Za-z0-9_]{5,17}";
        if (TextUtils.isEmpty(passwordRegex)) return false;
        else return password.matches(passwordRegex);
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
