package com.sowell.democlient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class AlterNameActivity extends AppCompatActivity {
    Button btConfirm;
    TextView tvName;
    EditText edName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_name);

        btConfirm = (Button) findViewById(R.id.confirm);
        tvName = (TextView) findViewById(R.id.nametv);
        edName = (EditText) findViewById(R.id.nameed);
    }

    public void Confirm(View view) {

        final String name = edName.getText().toString();
        SharedPreferences sp1 = getSharedPreferences("data", MODE_PRIVATE);
        final String mobile = sp1.getString("mobile", "");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    final String res = HttpUtils.post(Constants.AlterName, buildJson(mobile, name));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (res.equals("1")) {
                                Toast.makeText(AlterNameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (res.equals("0")) {
                                Toast.makeText(AlterNameActivity.this, "新旧姓名一样", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AlterNameActivity.this, "不知名错误", Toast.LENGTH_SHORT).show();
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

    private String buildJson(String mobile, String name) {
        JSONObject object = new JSONObject();
        try {
            object.put("mobile", mobile);
            object.put("name", name);

            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

