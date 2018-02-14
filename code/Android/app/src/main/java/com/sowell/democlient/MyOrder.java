package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyOrder extends AppCompatActivity {
//  定义所需参数变量
    private ListView mlistview;
    private List<Map<String, String>> list;
    private List<Map<String, String>> listNO;
    private List<Map<String, String>> listYES;
    private Map<String, String> map;
    private String Inform = null;
    private Button NO, all;
    private Button YES;
    private List<Map<String, String>> listOK;
    private OrderInfo orderinfo;
    public static MyOrder MYORDER_THIS = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MYORDER_THIS = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        mlistview = (ListView) findViewById(R.id.LV);
        listOK = new ArrayList<Map<String, String>>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                    String mobile = sp.getString("mobile", "");
//                    Log.e("获取的电话号码----------", mobile);
                    Inform = HttpUtils.post(Constants.getMyOrder_URL, buildJson(mobile));
//                    Log.e("----------------------", Inform);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        int cc = 0;
        String str1 = null;
        while (str1 == Inform || Inform.equals(null)) {
            cc++;
            try {
                Thread.currentThread().sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cc == 20) break;
        }
        str1 = Inform;
        List<OrderInfo> names = getInfosFormJson(Inform);
        //OrderInfo orderInfo=names.get(0);
        list = new ArrayList<Map<String, String>>();
        for (OrderInfo info : names) {
            map = new HashMap<String, String>();
            map.put("user_name", info.getUsename());
            map.put("user_car", info.getUsecar());
            map.put("user_mobile", info.getUsemobile());
            map.put("user_carnum", info.getUsecarnum());
            map.put("business_name", info.getBusinessname());
            map.put("business_phone", info.getBusiness_phone());
            map.put("type", info.getType());
            map.put("price", info.getPrice());
            map.put("address", info.getAddress());
            map.put("description", info.getDescription());
            map.put("timeend", info.getTimeend());
            map.put("timestart", info.getStarttime());
            map.put("state", info.getState());
            map.put("id", info.getId());
            map.put("bLocationx", info.getbLocationx());
            map.put("bLocationy", info.getbLocationy());
            list.add(map);
//            Log.e("----------------------", "转换list成功");
        }

        listOK = list;
        //初始化控件
        //创建一个Adapter实例
        MyBaseAdapter madapter = new MyBaseAdapter();
        mlistview.setAdapter(madapter);
//        Log.e("----------------------", "初始化成功");
        all = (Button) findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBaseAdapter madapter = new MyBaseAdapter();
                mlistview.setAdapter(madapter);
            }
        });

        //点击按钮
        NO = (Button) findViewById(R.id.NO);
        listNO = new ArrayList<Map<String, String>>();
        NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listNO.clear();
                for (Map<String, String> map : list) {
                    String state = map.get("state");
                    if (state.equals("0") || state.equals("1")) {
                        listNO.add(map);
                    }
                }
                listOK = listNO;
                //初始化控件
                //创建一个Adapter实例
                MyBaseAdapterNO madapter = new MyBaseAdapterNO();
                mlistview.setAdapter(madapter);
            }
        });

        YES = (Button) findViewById(R.id.YES);
        listYES = new ArrayList<Map<String, String>>();
        YES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listYES.clear();
                for (Map<String, String> map : list) {
                    String state = map.get("state");
                    if (state.equals("-1") || state.equals("2")) {
                        listYES.add(map);
                    }
                }
                listOK = listYES;
                MyBaseAdapterYES madapter = new MyBaseAdapterYES();
                mlistview.setAdapter(madapter);
            }
        });

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                map = listOK.get(position);
                String state = map.get("state");
                if (state.equals("0") || state.equals("1")) {
                    SharedPreferences endlocation = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = endlocation.edit();
                    editor1.putFloat("endlatitude", Float.parseFloat(map.get("bLocationx")));
                    editor1.putFloat("endlongtitude", Float.parseFloat(map.get("bLocationy")));
                    editor1.commit();
                    Intent intent = new Intent(MyOrder.this, NoOrder.class);
                    intent.putExtra("id", map.get("id"));
                    intent.putExtra("timestart", map.get("timestart"));
                    intent.putExtra("address", map.get("address"));
                    intent.putExtra("description", map.get("description"));
                    intent.putExtra("price", map.get("price"));
                    intent.putExtra("user_mobile", map.get("user_mobile"));
                    intent.putExtra("user_name", map.get("user_name"));
                    intent.putExtra("user_car", map.get("user_car"));
                    intent.putExtra("user_carnum", map.get("user_carnum"));
                    intent.putExtra("business_name", map.get("business_name"));
                    intent.putExtra("business_phone", map.get("business_phone"));
                    intent.putExtra("type", map.get("type"));
                    startActivity(intent);
                }

                if (state.equals("-1") || state.equals("2")) {
                    Intent intent = new Intent(MyOrder.this, YesOrder.class);
                    intent.putExtra("timeend", map.get("timeend"));
                    intent.putExtra("id", map.get("id"));
                    intent.putExtra("timestart", map.get("timestart"));
                    intent.putExtra("address", map.get("address"));
                    intent.putExtra("description", map.get("description"));
                    intent.putExtra("price", map.get("price"));
                    intent.putExtra("user_mobile", map.get("user_mobile"));
                    intent.putExtra("user_name", map.get("user_mobile"));
                    intent.putExtra("user_car", map.get("user_car"));
                    intent.putExtra("user_carnum", map.get("user_carnum"));
                    intent.putExtra("business_name", map.get("business_name"));
                    intent.putExtra("business_phone", map.get("business_phone"));
                    intent.putExtra("type", map.get("type"));
                    startActivity(intent);
                }
            }
        });
    }

    class MyBaseAdapter extends BaseAdapter {
        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MyOrder.this, R.layout.listitem, null);
            TextView businessname = (TextView) view.findViewById(R.id.businessname);
            TextView state = (TextView) view.findViewById(R.id.state);
            TextView type = (TextView) view.findViewById(R.id.type);
            TextView starttime = (TextView) view.findViewById(R.id.starttime);
            businessname.setText(list.get(position).get("business_name"));
            type.setText(list.get(position).get("type"));
            starttime.setText(list.get(position).get("timestart"));
            String state1 = list.get(position).get("state");
            if (state1.equals("-1")) {
                state.setText("商家拒绝");
            }
            if (state1.equals("0")) {
                state.setText("未有商家接单");
            }
            if (state1.equals("1")) {
                state.setText("商家正在处理");
            }
            if (state1.equals("2")) {
                state.setText("订单完成");
            }
            return view;
        }
    }

    class MyBaseAdapterNO extends BaseAdapter {
        public int getCount() {
            return listNO.size();
        }

        public Object getItem(int position) {
            return listNO.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MyOrder.this, R.layout.listitem, null);
            TextView businessname = (TextView) view.findViewById(R.id.businessname);
            TextView state = (TextView) view.findViewById(R.id.state);
            TextView type = (TextView) view.findViewById(R.id.type);
            TextView starttime = (TextView) view.findViewById(R.id.starttime);
            businessname.setText(listNO.get(position).get("business_name"));
            type.setText(listNO.get(position).get("type"));
            starttime.setText(listNO.get(position).get("timestart"));
            String state1 = listNO.get(position).get("state");
            if (state1.equals("0")) {
                state.setText("未有商家接单");
            }
            if (state1.equals("1")) {
                state.setText("商家正在处理");
            }
            return view;
        }
    }

    class MyBaseAdapterYES extends BaseAdapter {
        public int getCount() {
            return listYES.size();
        }

        public Object getItem(int position) {
            return listYES.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MyOrder.this, R.layout.listitem, null);
            TextView businessname = (TextView) view.findViewById(R.id.businessname);
            TextView state = (TextView) view.findViewById(R.id.state);
            TextView type = (TextView) view.findViewById(R.id.type);
            TextView starttime = (TextView) view.findViewById(R.id.starttime);
            businessname.setText("商家名称：" + listYES.get(position).get("business_name"));
            type.setText("服务类型：" + listYES.get(position).get("type"));
            starttime.setText("下单时间：" + listYES.get(position).get("timestart"));
            String state1 = listYES.get(position).get("state");
            if (state1.equals("-1")) {
                state.setText("商家拒绝");
            }
            if (state1.equals("2")) {
                state.setText("订单完成");
            }
            return view;
        }
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

    public static List<OrderInfo> getInfosFormJson(String json) {
        Gson gson = new Gson();
        Type listtype = new TypeToken<List<OrderInfo>>() {
        }.getType();
        List<OrderInfo> orderinfos = gson.fromJson(json, listtype);
        return orderinfos;
    }

}
