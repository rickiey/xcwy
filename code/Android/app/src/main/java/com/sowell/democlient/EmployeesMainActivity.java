package com.sowell.democlient;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeesMainActivity extends AppCompatActivity {

    private TextView tishiTV;
    private Button unfinishOrderButton;
    private Button alterPasswordButton;
    private Button alterNameButton;
    private Button cancle;
    private NewOrderReceiver newOrderReceiver;
    private NotificationManager newOrder;
    static final int NOTIFICATION_ID = 0x123;
    private String orderDetails;
    //
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    //定位相关
    private LocationClient mLocationClient;
    private boolean firstLocation = true;
    private double mLatitude;
    private double mLongtitude;
    public BDLocationListener myListener = new EmployeesMainActivity.MyLocationListener();
    private Button myLocation, service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_employees_main);
        //百度地图地图显示注册
        mMapView = (MapView) findViewById(R.id.bmapView1);
        mBaiduMap = mMapView.getMap();
        View child = mMapView.getChildAt(1);
        if (child!=null&&(child instanceof ImageView
                ||child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15f);
        mBaiduMap.setMapStatus(msu);
        initLocation();
        //初始化组件控件
        unfinishOrderButton = (Button) findViewById(R.id.look_order);
        alterPasswordButton = (Button) findViewById(R.id.alterpassword);
        alterNameButton = (Button) findViewById(R.id.altername);
        cancle = (Button) findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("data2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                newOrder.cancel(NOTIFICATION_ID);
                Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                startActivity(intent);
                finish();

            }
        });
        tishiTV = (TextView) findViewById(R.id.tishi);
        tishiTV.setVisibility(View.INVISIBLE);
        newOrder = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        newOrderReceiver = new NewOrderReceiver();
        IntentFilter filter = new IntentFilter("com.sowell.democlien.NEW_ORDER");
        registerReceiver(newOrderReceiver, filter);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
//        //定位相关
//        try {
//            Method method=MainActivity.class.getDeclaredMethod("initLocation",new Class[]{});
//            method.setAccessible(true);
//            try {
//                method.invoke(new MainActivity(),new Object[]{});
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }

    //定位相关
    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            // 第一次定位时，将地图位置移动到当前位置
            if (firstLocation) {
                firstLocation = false;
                LatLng xy = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(xy);
                mBaiduMap.animateMapStatus(status);
                Toast.makeText(EmployeesMainActivity.this, location.getLocationDescribe(),
                        Toast.LENGTH_LONG).show();
                SharedPreferences startlocation = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = startlocation.edit();
                editor2.putFloat("mlatitude", (float) mLatitude);
                editor2.putFloat("mlongtitude", (float) mLongtitude);
                editor2.commit();
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(newOrderReceiver);
        newOrder.cancel(NOTIFICATION_ID);
        Intent intent = new Intent(getApplication(), MyService.class);
        stopService(intent);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()
        mMapView.onPause();
    }

    public class NewOrderReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.e("广播", "------------");
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String time = df.format(new Date());
            tishiTV.setText("您有新订单等待处理。              " + time);
            tishiTV.setVisibility(View.VISIBLE);
            orderDetails = intent.getStringExtra("orderdetails");
        }
    }

    public void look_order(View view) {
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        final String staffMobile = sp.getString("mobile", "");

        Thread t = new Thread(new Runnable() {
            //Boolean status = false;
            @Override
            public void run() {
                try {
                    final String res = HttpUtils.post(Constants.StaffCheckOrderServlet, buildJson(staffMobile));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    if (!res.equals("")) {
                        tishiTV.setVisibility(View.INVISIBLE);
//                        Log.e("res----", res);
                        newOrder.cancel(NOTIFICATION_ID);
                        Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                        intent.putExtra("orderdetails", res);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "没有待处理订单", Toast.LENGTH_SHORT).show();    }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        t.start();
    }

    public void alterPassword(View view) {
        Intent intent = new Intent(this, AlterPasswordActivity.class);
        startActivity(intent);
    }

    public void alterName(View view) {
        Intent intent = new Intent(this, AlterNameActivity.class);
        startActivity(intent);
    }

    public void neworder(View view) {
        newOrder.cancel(NOTIFICATION_ID);
        tishiTV.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("orderdetails", orderDetails);
        startActivity(intent);
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
