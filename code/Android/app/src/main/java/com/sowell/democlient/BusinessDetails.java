package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sowell.democlient.constants.Constants;
import com.squareup.picasso.Picasso;

public class BusinessDetails extends AppCompatActivity {
    private Intent intent;
    private String name, phone, address, info, picture;
    private TextView business_name;
    private TextView business_phone;
    private TextView business_address;
    private TextView business_info;
    private TextView business_score;
    private ImageView business_photo;
    private ImageButton business_location;
    private String bLocationx1;
    private String bLocationy1;
    private float bLocationx;
    private float bLocationy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_details);
//        对数据进行初始化
        init();
//        对数据进行处理
        fillData();
//        导航按钮监听函数
        business_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BusinessDetails.this, routePlanActivity.class);
                startActivity(intent);
            }

            ;
        });
    }

    private void init() {
        intent = getIntent();
        business_info = (TextView) findViewById(R.id.business_info);
        business_address = (TextView) findViewById(R.id.business_address);
        business_name = (TextView) findViewById(R.id.business_name);
        business_phone = (TextView) findViewById(R.id.business_phone);
        business_photo = (ImageView) findViewById(R.id.business_picture);
        business_location = (ImageButton) findViewById(R.id.baddress);
        name = intent.getStringExtra("business_name");
        phone = intent.getStringExtra("business_phone");
        info = intent.getStringExtra("business_description");
        address = intent.getStringExtra("business_site");
        picture = intent.getStringExtra("business_photo");
        bLocationx1 = intent.getStringExtra("business_bLocationx");
        bLocationx = Float.parseFloat(bLocationx1);
        bLocationy1 = intent.getStringExtra("business_bLocationy");
        bLocationy = Float.parseFloat(bLocationy1);
    }

    private void fillData() {
        business_name.setText("商家名称：" + name);
        business_phone.setText("商家电话：" + phone);
        business_info.setText("商家信息：" + info);
        business_address.setText("商家地址：" + address);
        String url = Constants.IMG_RESOURCE_URL + "/" + picture;
        Picasso
                .with(getApplicationContext())
                .load(url)
                .into(business_photo);
        SharedPreferences endlocation = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = endlocation.edit();
        editor1.putFloat("endlatitude", (float) bLocationx);
        editor1.putFloat("endlongtitude", (float) bLocationy);
        editor1.commit();
    }

//联系商家按钮监听函数
    public void MakePhone(View source) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }
}
