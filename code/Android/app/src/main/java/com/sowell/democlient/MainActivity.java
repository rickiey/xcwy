package com.sowell.democlient;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;


public class MainActivity extends AppCompatActivity {
//    地图相关
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    //定位相关
    private LocationClient mLocationClient;
    private boolean firstLocation = true;
    private double mLatitude;
    private double mLongtitude;
    private ImageButton myLocation, service;
    //检索相关
    private EditText textPoi;
    private PoiSearch mPoiSearch;
    public String cityName;
    //方向监听相关
    private float mCurrentAccracy;//当前的精度
    private MyOrientationListener myOrientationListener;//方向监听
    private float mXDirection;
    private BitmapDescriptor mIconLocation;//方向图标
    public BDLocationListener myListener = new MyLocationListener();
    private ImageButton lukuang;
    private ImageButton tiao;
    private ImageButton mapType;
    private Button searchPoi;
    public boolean mapTypeSatellite = false;
    private boolean lukuangstate = false;
    private String mylocation;
    public static MainActivity State=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        State = this;
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView1);
        mBaiduMap = mMapView.getMap();

        View child = mMapView.getChildAt(1);
        if (child!=null&&(child instanceof ImageView
        ||child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15f);
        mBaiduMap.setMapStatus(msu);
        myLocation = (ImageButton) findViewById(R.id.btn_2);//我的位置按钮
        textPoi = (EditText) findViewById(R.id.et_poisearch);
        searchPoi = (Button) findViewById(R.id.btn_search);//搜索按钮
        //覆盖物相关
        lukuang = (ImageButton) findViewById(R.id.btn_lukuang); //初始化路况按钮
        mapType = (ImageButton) findViewById(R.id.btn_1); //初始化地图类型按钮
        mPoiSearch = PoiSearch.newInstance();//实例化poi检索
        tiao = (ImageButton) findViewById(R.id.tiao);

        tiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JumpActivity.class);
                startActivity(intent);

            }
        });

        //poi检索结果监听事件
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(MainActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                    mBaiduMap.clear();
                    MyPoiOverlay poiOverlay = new MyPoiOverlay(mBaiduMap);
                    poiOverlay.setData(result);// 设置POI数据
                    mBaiduMap.setOnMarkerClickListener(poiOverlay);
                    poiOverlay.addToMap();// 将所有的overlay添加到地图上
                    poiOverlay.zoomToSpan();
                }
                //在onGetPoiResult回调中将PoiOverlay的子类的对象添加到地图上 并设置点击事件
                //重写PoiOverlay子类的 onPoiClick方法 设置点击事件
                LatLng latLng = new LatLng(mLatitude, mLongtitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult result) {
                //获取Place详情页检索结果
                if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(MainActivity.this, "抱歉，未找到结果",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 正常返回结果的时候，此处可以获得很多相关信息
                    final LatLng location = result.getLocation();//该poi的经纬度
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("名称：" + result.getName() + "\n" + "电话：" + result.getTelephone() + "\n" + "地址:" + result.getAddress());
                    builder.setTitle("详细信息");
                    builder.setPositiveButton("去这里", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            SharedPreferences endlocation = getSharedPreferences("data", MODE_PRIVATE);
                            SharedPreferences.Editor editor1 = endlocation.edit();
                            editor1.putFloat("endlatitude", (float) location.latitude);
                            editor1.putFloat("endlongtitude", (float) location.longitude);
                            editor1.commit();
                            Intent intent = new Intent(MainActivity.this, routePlanActivity.class);
                            startActivity(intent);
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            }
        };

        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        initLocation();
        initOritationListener();
        mapType();
        centertomyLocation();
        /*
        路况功能
                 */
        lukuang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lukuangstate)
                    lukuangstate = true;
                else lukuangstate = false;
                mBaiduMap.setTrafficEnabled(lukuangstate);
            }

        });

        searchPoi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPoiSearch.searchNearby((new PoiNearbySearchOption()).radius(100000)//周边检索范围100km
                        .location(new LatLng(mLatitude, mLongtitude))//cityName
                        .keyword(textPoi.getText().toString())//textPoi.getText().toString()
                        .pageNum(1));

            }

        });

        //跳转商家
        service = (ImageButton) findViewById(R.id.service);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WhichBusiness.class);
                startActivity(intent);
            }
        });
    }

    //自定义覆盖物的点击
    private class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap arg0) {
            super(arg0);
        }

        @Override
        public boolean onPoiClick(int arg0) {
            super.onPoiClick(arg0);
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(arg0);
            // 检索poi详细信息
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption()
                    .poiUid(poiInfo.uid));

            return true;
        }
    }

    //地图类型切换
    private void mapType() {
        mapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapTypeSatellite == false) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    mapTypeSatellite = true;
                } else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    mapTypeSatellite = false;
                }
            }
        });
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

    //定位到我的位置
    private void centertomyLocation() {
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(mLatitude, mLongtitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
            }
        });
    }

    // 实现实位回调监听
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置获取到的方向信息，顺时针0-360
                    .direction(mXDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mCurrentAccracy = location.getRadius();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 更新经纬度
            cityName = location.getCity();
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            // 第一次定位时，将地图位置移动到当前位置
            if (firstLocation) {
                firstLocation = false;
                LatLng xy = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(xy);
                mBaiduMap.animateMapStatus(status);
                Toast.makeText(MainActivity.this, location.getLocationDescribe(),
                        Toast.LENGTH_LONG).show();
                mylocation = location.getLocationDescribe();
                //存储当前位置信息
                SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("location", mylocation);
                editor.commit();
                SharedPreferences startlocation = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = startlocation.edit();
                editor2.putFloat("mlatitude", (float) mLatitude);
                editor2.putFloat("mlongtitude", (float) mLongtitude);
                editor2.commit();
            }
        }

    }

    //初始化方向传感器
    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mXDirection = x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection)
                                .latitude(mLatitude)
                                .longitude(mLongtitude).build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                        // 使用默认方向图标
                        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mIconLocation);
                        mBaiduMap.setMyLocationConfiguration(config);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()
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
        //开启方向传感器
        myOrientationListener.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        //停止方向传感器
        myOrientationListener.stop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()
        mMapView.onPause();
    }
}




