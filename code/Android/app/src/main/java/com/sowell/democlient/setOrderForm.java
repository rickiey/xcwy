package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class setOrderForm extends AppCompatActivity {
    //    定义所需变量组件信息
    private Button btn_submit, local;
    private EditText et_num;
    private EditText et_name;
    private EditText et_carnum;
    private EditText et_carkind;
    private EditText et_nowlocal;
    private EditText et_message;
    private TextView et_servicename;
    private TextView et_businessname;
    private TextView tv_price;
    public static setOrderForm SET_ORDER_THIS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SET_ORDER_THIS = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_order_form);
//        初始化各组件
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_num = (EditText) findViewById(R.id.et_num);
        et_name = (EditText) findViewById(R.id.et_name);
        et_carnum = (EditText) findViewById(R.id.et_carnum);
        et_carkind = (EditText) findViewById(R.id.et_carkind);
        et_nowlocal = (EditText) findViewById(R.id.et_nowlocal);
        et_message = (EditText) findViewById(R.id.et_message);
        et_servicename = (TextView) findViewById(R.id.servicename);
        et_businessname = (TextView) findViewById(R.id.business_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        local = (Button) findViewById(R.id.local);

        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp1 = getSharedPreferences("data", MODE_PRIVATE);
                String location = sp1.getString("location", "");
                et_nowlocal.setText(location);
            }
        });

        //获取自动填充的数据
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                    String mobile = sp.getString("mobile", "");
                    Log.e("获取的电话号码----------", mobile);
                    final String Inform = HttpUtils.post(Constants.getInform_URL, buildJson(mobile));
                    Log.e("返回的车主信息------", Inform);
                    JSONObject jbt = new JSONObject(Inform);
                    final String usename = jbt.getString("name");
                    Log.e("车主姓名---", usename);
                    final String mobilenum = jbt.getString("mobile");
                    final String usecar = jbt.getString("car");
                    final String usecarnum = jbt.getString("carnum");
                    SharedPreferences sp1 = getSharedPreferences("data", MODE_PRIVATE);
                    final String location = sp1.getString("location", "");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //设置文本内容
                            Intent intent = getIntent();
                            String type = intent.getStringExtra("buy_name");
                            String businessname = intent.getStringExtra("besiness_name");
                            double price = intent.getDoubleExtra("price", 0);
                            et_businessname.setText(businessname);
                            et_servicename.setText(type);
                            tv_price.setText(Double.toString(price));
                            et_name.setText(usename);
                            et_num.setText(mobilenum);
                            et_carkind.setText(usecar);
                            et_carnum.setText(usecarnum);
                            et_nowlocal.setText(location);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("设置文本框错误", "错误");
                }
            }
        });
        t.start();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (TextUtils.isEmpty(et_num.getText().toString()) || TextUtils.isEmpty(et_name.getText().toString()) || TextUtils.isEmpty(et_carnum.getText().toString()) || TextUtils.isEmpty(et_carkind.getText().toString()) || TextUtils.isEmpty(et_nowlocal.getText().toString())) {
                    Toast mToast = Toast.makeText(getApplicationContext(), "有信息未填写", Toast.LENGTH_LONG);
                    mToast.show();
                    return;
                }

                //检验是否有未输入
                final String usermobile = et_num.getText().toString();
                final String username = et_name.getText().toString();
                final String usercar = et_carkind.getText().toString();
                final String usercarnum = et_carnum.getText().toString();

                //输入检验
                if (!isMobileNO(usermobile)) {
                    Toast.makeText(getApplicationContext(), "手机号格式有错", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isCarnumNO(usercarnum)) {
                    Toast.makeText(getApplicationContext(), "车牌格式有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                tiaozhuan();
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

    public void tiaozhuan() {
        Intent intent1 = getIntent();
        String condition = intent1.getStringExtra("condition");
        String id = intent1.getStringExtra("id");
        String phone = intent1.getStringExtra("phone");
        Intent intent = new Intent(this, shengchengdingdan.class);
        intent.putExtra("condition", condition);
        intent.putExtra("usename", et_name.getText().toString().trim());
        intent.putExtra("usemobile", et_num.getText().toString().trim());
        intent.putExtra("usecar", et_carkind.getText().toString().trim());
        intent.putExtra("usecarnum", et_carnum.getText().toString().trim());
        intent.putExtra("nowlocal", et_nowlocal.getText().toString().trim());
        intent.putExtra("message", et_message.getText().toString().trim());
        intent.putExtra("price", tv_price.getText().toString().trim());
        intent.putExtra("businessname", et_businessname.getText().toString().trim());
        intent.putExtra("et_servicename", et_servicename.getText().toString().trim());
        intent.putExtra("id", id);
        intent.putExtra("phone", phone);
        SharedPreferences sp1 = getSharedPreferences("data", MODE_PRIVATE);
        final String location = sp1.getString("location", "");
        intent.putExtra("location", location);
        startActivity(intent);
    }

    //检验车牌是否输入正确
    public static boolean isCarnumNO(String carnumber) {
         /*
          车牌号格式：汉字 + A-Z + 5位A-Z或0-9
         （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
          */
        String carnumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        return carnumber.matches(carnumRegex);
    }

    //验证手机号是否正确
    public static boolean isMobileNO(String mobiles) {
        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[012356789]\\d{8}|17[0678]\\d{8}";
        return mobiles.matches(telRegex);
    }

}
