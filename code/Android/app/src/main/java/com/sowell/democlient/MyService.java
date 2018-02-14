package com.sowell.democlient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static android.os.SystemClock.sleep;

public class MyService extends Service {
    //    定义所需参数变量
    private Thread t;
    static final int NOTIFICATION_ID = 0x123;
    private NotificationManager newOrder;
    Boolean status;
    // private Context context=this.getApplicationContext();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
//        Log.e("MyService", "onCreate");
        super.onCreate();
        // final String staffMobile = "1311111111";
        SharedPreferences sp1 = getSharedPreferences("data", MODE_PRIVATE);
        final String staffMobile = sp1.getString("mobile", "");
        newOrder = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        status = false;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!status) {
//                    Log.e("------------", "ddd");
                    try {
//                        Log.e("------------", "lll");
                        final String res = HttpUtils.post(Constants.StaffCheckOrderServlet, buildJson(staffMobile));
                        // Log.e("----",res);
                        if (!res.equals("")) {
                            status = true;
//                            Log.e("status", status.toString());
                            JSONObject obj = new JSONObject(res);
                            Intent intent = new Intent(getApplicationContext(), OrderDetailsActivity.class);
                            intent.putExtra("orderdetails", res);
                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                            Notification notify = new Notification.Builder(getApplicationContext())
                                    .setAutoCancel(true)
                                    .setTicker("行程无忧新消息")
                                    .setSmallIcon(R.drawable.logo)//通知栏图标   正式时应该用应用图标
                                    .setContentTitle("新订单")
                                    .setContentText("收到新订单，请处理")
                                    .setDefaults(Notification.DEFAULT_SOUND)
                                    .setDefaults(Notification.DEFAULT_LIGHTS)
                                    .setWhen(System.currentTimeMillis())
                                    .setContentIntent(pi).getNotification();
                            newOrder.notify(NOTIFICATION_ID, notify);

                            Intent intent1 = new Intent();
                            intent1.setAction("com.sowell.democlien.NEW_ORDER");
                            intent1.putExtra("orderdetails", res);
                            sendBroadcast(intent1);
                            stopSelf();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sleep(5000);
//                        boolean b = obj.getBoolean("result");
                }

            }
        });
        t.start();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        status = true;
//        Log.e("ddddd", "--onDestroy--");
        super.onDestroy();
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



