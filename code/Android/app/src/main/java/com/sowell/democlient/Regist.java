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

public class Regist extends AppCompatActivity {
    //    定义所需变量及控件信息
    private Button btn_code, regist;
    private EditText number, password1, password2, code, name, car, carnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
//        初始化各控件变量
        btn_code = (Button) findViewById(R.id.passnumber);
        btn_code.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendcode();
            }
        });
        number = (EditText) findViewById(R.id.user_regist_number);
        password1 = (EditText) findViewById(R.id.user_regist_password1);
        password2 = (EditText) findViewById(R.id.user_regist_password2);
        code = (EditText) findViewById(R.id.code);
        name = (EditText) findViewById(R.id.user_namec);
        car = (EditText) findViewById(R.id.car);
        carnumber = (EditText) findViewById(R.id.car_num);
        regist = (Button) findViewById(R.id.regist);

        regist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispose();

            }
        });

        number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean lostFocus) {
                if (lostFocus) {
                } else {
                    if (number.getText().length() != 11) {
                        Toast mToast1 = Toast.makeText(getApplicationContext(), "手机号有误", Toast.LENGTH_LONG);
                        mToast1.show();
                    } else judgenumber();
                }
            }
        });

        password1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean lostFocus) {
                if (lostFocus) {
                } else {
                    if (password1.getText().length() < 6) {
                        Toast mToast1 = Toast.makeText(getApplicationContext(), "密码过短", Toast.LENGTH_LONG);
                        mToast1.show();
                    } else judgepassword();
                }
            }
        });

        password2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean lostFocus) {
                if (lostFocus) {
                } else {
                    if (!password1.getText().toString().equals(password2.getText().toString())) {
                        Toast mToast1 = Toast.makeText(getApplicationContext(), "两次输入密码不同", Toast.LENGTH_LONG);
                        mToast1.show();
                    }
                }
            }
        });

        code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean lostFocus) {
                if (lostFocus) {
                } else {
                    if (code.getText().length() != 6) {
                        Toast mToast1 = Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_LONG);
                        mToast1.show();
                    }
                }
            }
        });

//        carnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean lostFocus) {
//                if (lostFocus){}else {
//                   judgecarnum();
//                    }
//                }
//        });
    }

    //验证码处理
    public void sendcode() {
        if (judgenumber()) {
            Toast mToast = Toast.makeText(getApplicationContext(), "验证码已发送", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            Toast mToast = Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_LONG);
            mToast.show();
        }
    }

    //注册处理
    public void dispose() {

        if (number.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入账号", Toast.LENGTH_LONG);
            mToast.show();
        } else if (password1.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_LONG);
            mToast.show();
        } else if (password2.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请确认密码", Toast.LENGTH_LONG);
            mToast.show();
        } else if (code.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_LONG);
            mToast.show();
        } else if (name.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_LONG);
            mToast.show();
        } else if (car.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入车辆类型", Toast.LENGTH_LONG);
            mToast.show();
        } else if (carnumber.getText().toString().equals("")) {
            Toast mToast = Toast.makeText(getApplicationContext(), "请输入车牌号码", Toast.LENGTH_LONG);
            mToast.show();
        } else {
            if (password1.getText().toString().equals(password2.getText().toString()) &&
                    code.getText().length() == 6 &&
                    judgenumber() &&
                    judgepassword()) {

                //数据发送处理
                final String mobile = number.getText().toString();
                final String password = password1.getText().toString();
                final String mname = name.getText().toString();
                final String mcar = car.getText().toString();
                final String carnum = carnumber.getText().toString();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //HttpUtils.post(Constants.REGISTER_URL, buildJson(mobile,password,mname,mcar,carnum));
                            //获取返回数据
                            final String result = HttpUtils.post(Constants.REGISTER_URL, buildJson(mobile, password, mname, mcar, carnum));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("1")) {
//                                        Log.e("------------", result);
                                        Save.saveUserInfo(Regist.this, mobile, password);
                                        Toast mToast = Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG);
                                        mToast.show();
                                        //跳转向下一个Activity发送数据
                                        Intent intent = new Intent(Regist.this, MainActivity.class);
                                        startActivity(intent);
                                        UserLogin.State.finish();
                                        finish();
                                    } else if (result.equals("0")) {
//                                        Log.e("------------", result);
                                        Toast mToast = Toast.makeText(getApplicationContext(), "账号已存在", Toast.LENGTH_LONG);
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
            } else {
                Toast mToast = Toast.makeText(getApplicationContext(), "信息有误，注册失败", Toast.LENGTH_LONG);
                mToast.show();
            }
        }
    }

    //手机号正则检验
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[012356789]\\d{8}|17[0678]\\d{8}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    public static boolean isPasswordNO(String password) {
        //字母开头，包括字母，数字和下划线，6-18位
        String passwordRegex = "[A-Za-z]{1}[A-Za-z0-9_]{5,17}";
        if (TextUtils.isEmpty(passwordRegex)) return false;
        else return password.matches(passwordRegex);
    }

//    public static boolean isCarnumberNO(String carnumber) {
//         /*
//          车牌号格式：汉字 + A-Z + 5位A-Z或0-9
//         （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
//          */
//        String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
//        if (TextUtils.isEmpty(carnumber)) return false;
//        else return carnumber.matches(carnumRegex);
//    }

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

    public boolean judgepassword() {
        String num = password1.getText().toString();
        boolean jud = isPasswordNO(num);
        if (jud) {
            return true;
        } else {
            Toast mToast = Toast.makeText(getApplicationContext(), "密码格式有误", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }
    }

//    public boolean judgecarnum(){
//        String num=carnumber.getText().toString();
//        boolean jud=isCarnumberNO(num);
//        if (jud == true){return true;}else {
//            Toast mToast = Toast.makeText(getApplicationContext(), "车牌号有误", Toast.LENGTH_LONG);
//            mToast.show();
//            return false;
//        }
//    }

    private String buildJson(String mobile, String password, String mname, String mcar, String carnum) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobile);
            object.put("password", password);
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