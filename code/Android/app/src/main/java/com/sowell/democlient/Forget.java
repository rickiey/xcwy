package com.sowell.democlient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sowell.democlient.utils.HttpUtils;
import com.sowell.democlient.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;


public class Forget extends AppCompatActivity {
    private EditText number, et_code;
    private Button btn_code, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
//        初始化activity组件控件
        number = (EditText) findViewById(R.id.forget_user_number);
        et_code = (EditText) findViewById(R.id.forget_code);
        btn_code = (Button) findViewById(R.id.passnumber);
        next = (Button) findViewById(R.id.next);
        btn_code.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendcode();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                forget();
            }
        });
    }

    public static boolean isMobileNO(String mobiles) {
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[012356789]\\d{8}|17[0678]\\d{8}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    public boolean judgenumber() {
        String num = number.getText().toString();
        boolean jud = isMobileNO(num);
        if (jud) {
            return true;
        } else {
            Toast mToast = Toast.makeText(getApplicationContext(), "手机号有误", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }
    }

    public void sendcode() {
        if (number.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            final String mobile = number.getText().toString();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //HttpUtils.post(Constants.FORGET_URL, buildJson(mobile));
                        //获取返回数据
                        final String result = HttpUtils.post(Constants.FORGET_URL, buildJson(mobile));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if ("1".equals(result)) {
                                    Toast mToast = Toast.makeText(getApplicationContext(), "验证码已发送", Toast.LENGTH_LONG);
                                    mToast.show();
                                } else if ("0".equals(result)) {
                                    Toast mToast = Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG);
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

    public void forget() {
        if (et_code.getText().length() == 6 && judgenumber()) {
            Intent intent = new Intent(Forget.this, Reset.class);
            intent.putExtra("mobile", number.getText().toString().trim());
            startActivity(intent);
            finish();
        } else {
            Toast mToast = Toast.makeText(getApplicationContext(), "信息错误", Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    private String buildJson(String mobile) {
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
