package com.sowell.democlient;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class routePlanActivity extends AppCompatActivity {
    //    定义所需变量控件
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private double mLatitude;
    private double mLongtitude;
    private double endLatitude;
    private double endLongtitude;
    public RoutePlanSearch mSearcher;
    private Button mDb06ll;
    private Thread t = null;
    //导航相关
    public static List<Activity> activityList = new LinkedList<Activity>();
    private static final String APP_FOLDER_NAME = "xingchengwuyou";
    private String mSDCardPath = null;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private final static String authBaseArr[] =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
    private final static String authComArr[] = {Manifest.permission.READ_PHONE_STATE};
    private final static int authBaseRequestCode = 1;
    private final static int authComRequestCode = 2;
    private boolean hasInitSuccess = false;
    private boolean hasRequestComAuth = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityList.add(this);
//        初始化各变量控件
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_route_plan);
        mMapView = (MapView) findViewById(R.id.bmapView1);
        mBaiduMap = mMapView.getMap();
        View child = mMapView.getChildAt(1);
        if (child!=null&&(child instanceof ImageView
                ||child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15f);
        mBaiduMap.setMapStatus(msu);
        mDb06ll = (Button) findViewById(R.id.btn1);//导航按钮
        mSearcher = RoutePlanSearch.newInstance();//实例化路径规划

        //路线规划结果监听事件
        OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                //获取步行线路规划结果
            }

            public void onGetTransitRouteResult(TransitRouteResult result) {
                //获取公交换乘路径规划结果
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                //获取驾车线路规划结果//
                if (result == null) {
                    Toast.makeText(routePlanActivity.this, "404 not found", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 获取到驾车路线的结果集合
                List<DrivingRouteLine> lines = result.getRouteLines();
                DrivingRouteLine routeLine = lines.get(0);
                // 创建路线的覆盖物
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                // 把数据设置到覆盖物上
                overlay.setData(routeLine);
                // 把覆盖物添加到地图上
                overlay.addToMap();
                // 缩放到合适的距离
                overlay.zoomToSpan();
            }

            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
            }

            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            }
        };
        mSearcher.setOnGetRoutePlanResultListener(listener);
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 0x01;
                mHandler.sendMessage(msg);
            }
        });

        t.start();
        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                Log.i("crug", name);
            }
        }, 500);
        BNOuterLogUtil.setLogSwitcher(true);

        initListener();
        if (initDirs()) {
            initNavi();
        }
    }

    private void initListener() {
        mDb06ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (BaiduNaviManager.isNaviInited()) {
                    routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09LL);
                }
            }
        });
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    String authinfo = null;
    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    break;
                }
                default:
                    break;
            }
        }
    };
    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };

    public void showToastMsg(final String msg) {
        routePlanActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(routePlanActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean hasBasePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCompletePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void initNavi() {
        BNOuterTTSPlayerCallback ttsCallback = null;
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                routePlanActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(routePlanActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
//                Toast.makeText(routePlanActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
//                Toast.makeText(routePlanActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
//                Toast.makeText(routePlanActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }

        }, null, ttsHandler, ttsPlayStateListener);
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private BNRoutePlanNode.CoordinateType mCoordinateType = null;

    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        mCoordinateType = coType;
        if (!hasInitSuccess) {
//            Toast.makeText(routePlanActivity.this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }
        // 权限申请
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // 保证导航功能完备
            if (!hasCompletePhoneAuth()) {
                if (!hasRequestComAuth) {
                    hasRequestComAuth = true;
                    this.requestPermissions(authComArr, authComRequestCode);
                    return;
                } else {
                    Toast.makeText(routePlanActivity.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;

        switch (coType) {
            case BD09LL: {
                SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                float slatitude = sp.getFloat("mlatitude", 0);
                float slongtitude = sp.getFloat("mlongtitude", 0);
                float elatitude = sp.getFloat("endlatitude", 0);
                float elongtitude = sp.getFloat("endlongtitude", 0);
                sNode = new BNRoutePlanNode((double) slongtitude, (double) slatitude, "当前位置", null, coType);
                eNode = new BNRoutePlanNode((double) elongtitude, (double) elatitude, "终点位置", null, coType);
                break;
            }
            default:;
        }

        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            // 开发者可以使用旧的算路接口，也可以使用新的算路接口,可以接收诱导信息等
            // BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode),
                    eventListerner);
        }
    }

    BaiduNaviManager.NavEventListener eventListerner = new BaiduNaviManager.NavEventListener() {

        @Override
        public void onCommonEventCall(int what, int arg1, int arg2, Bundle bundle) {
            BNEventHandler.getInstance().handleNaviEvent(what, arg1, arg2, bundle);
        }
    };

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
             * 设置途径点以及resetEndNode会回调该接口
             */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            Intent intent = new Intent(routePlanActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(routePlanActivity.this, ",算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSetting() {
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);//
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);//
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        BNaviSettingManager.setIsAutoQuitWhenArrived(true);
        Bundle bundle = new Bundle();
        // 设置APPID
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "10000319");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
            Log.e("test_TTS", "stopTTS");
        }

        @Override
        public void resumeTTS() {
            Log.e("test_TTS", "resumeTTS");
        }

        @Override
        public void releaseTTSPlayer() {
            Log.e("test_TTS", "releaseTTSPlayer");
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
//            Log.e("test_TTS", "playTTSText" + "_" + speech + "_" + bPreempt);
            return 1;
        }

        @Override
        public void phoneHangUp() {
            Log.e("test_TTS", "phoneHangUp");
        }

        @Override
        public void phoneCalling() {
            Log.e("test_TTS", "phoneCalling");
        }

        @Override
        public void pauseTTS() {
            Log.e("test_TTS", "pauseTTS");
        }

        @Override
        public void initTTSPlayer() {
            Log.e("test_TTS", "initTTSPlayer");
        }

        @Override
        public int getTTSState() {
//            Log.e("test_TTS", "getTTSState");
            return 1;
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(routePlanActivity.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        } else if (requestCode == authComRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                }
            }
            routeplanToNavi(mCoordinateType);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    SharedPreferences endlocation = getSharedPreferences("data", MODE_PRIVATE);
                    endLatitude = endlocation.getFloat("endlatitude", 0);
                    endLongtitude = endlocation.getFloat("endlongtitude", 0);
                    SharedPreferences startlocation = getSharedPreferences("data", MODE_PRIVATE);
                    mLatitude = startlocation.getFloat("mlatitude", 0);
                    mLongtitude = startlocation.getFloat("mlongtitude", 0);
                    mSearcher.drivingSearch((new DrivingRoutePlanOption())
                            .from(PlanNode.withLocation(new LatLng(mLatitude, mLongtitude)))
                            .to(PlanNode.withLocation(new LatLng(endLatitude, endLongtitude))));
//                    System.out.print(endLatitude + endLongtitude);
                    break;
            }
        }
    };

    //绘制自己的DrivingRouteOverlay
//    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {
//
//        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//        }
//
//        @Override
//        public int getLineColor() {
//            return 0;
//        }
//
//        @Override
//        public List<BitmapDescriptor> getCustomTextureList() {
//            ArrayList<BitmapDescriptor> list = new ArrayList<BitmapDescriptor>();
//            //list.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_road_blue_arrow));
//            list.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_road_green_arrow));
//            //list.add(BitmapDescriptorFactory.fromAsset("Icon_road_yellow_arrow.png"));
//            //list.add(BitmapDescriptorFactory.fromResource(R.drawable.icon_road_red_arrow));
//            //list.add(BitmapDescriptorFactory.fromAsset("Icon_road_nofocus.png"));
//            return list;
//        }
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()
        mMapView.onDestroy();
        mMapView = null;
        mSearcher.destroy();
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
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()
        mMapView.onPause();
    }

}
