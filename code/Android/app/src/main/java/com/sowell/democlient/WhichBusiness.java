package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;


public class WhichBusiness extends AppCompatActivity {
    //    定义所需变量及组件
    private String Trail, Beauty, Water, Gasoline,
            Tyre, Electricity, Lock, Repair;
    private ListView list;
    private List<JsonInfo> jsonInfos;
    private List<JsonInfo> chjsonInfos;
    private JsonInfo jsonInfo;
    private LinearLayout loading;
    private int[] icons = {R.drawable.backpicture};
    private int FS = 0;
    private List<String> cBox;
    private int cBoxOne = 0;
    private int cBoxTwo = 0;
    private CheckBox trail;
    private CheckBox beauty;
    private CheckBox water;
    private CheckBox gasoline;
    private CheckBox tyre;
    private CheckBox electricity;
    private CheckBox lock;
    private CheckBox repair;
    private String str = "first", str1;
    private float mLocationx, mLocationy;
    private SharedPreferences startlocation;
    public static WhichBusiness State=null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.which_business);
        inItView();
        fillData();

//        ListView监听函数实现跳转到商家服务界面
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), TransForm.class);
                jsonInfo = jsonInfos.get(position);
//                参数传递
                intent.putExtra("business_id", jsonInfo.getId());
                intent.putExtra("business_name", jsonInfo.getBName());
                intent.putExtra("business_phone", jsonInfo.getPhone());
                intent.putExtra("business_photo", jsonInfo.getPhoto());
                intent.putExtra("business_site", jsonInfo.getSite());
                intent.putExtra("business_bLocationx", jsonInfo.getbLocationx());
                intent.putExtra("business_bLocationy", jsonInfo.getbLocationy());
                intent.putExtra("cBoxOne", cBoxOne);
                intent.putExtra("cBoxTwo", cBoxTwo);
                intent.putExtra("business_description", jsonInfo.getDescription());
                WhichBusiness.this.startActivity(intent);
            }
        });

//        checkbox监听函数，实现页面商家刷新
        trail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    beauty.setClickable(false);
                    repair.setClickable(false);
                    //              Internet(cBox);
                    fillData();
                    cBoxOne++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxOne--;
                    //       Internet(cBox);
                    fillData();
                    if (cBoxOne == 0) {
                        beauty.setClickable(true);
                        repair.setClickable(true);
                    }
                }
            }
        });

        //        checkbox监听函数，实现页面商家刷新
        tyre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    System.out.println(buttonView.getText().toString());
                    beauty.setClickable(false);
                    repair.setClickable(false);
                    //        Internet(cBox);
                    fillData();
                    cBoxOne++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxOne--;
                    //           Internet(cBox);
                    fillData();
                    if (cBoxOne == 0) {
                        beauty.setClickable(true);
                        repair.setClickable(true);
                    }
                }
            }
        });

        //        checkbox监听函数，实现页面商家刷新
        water.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    beauty.setClickable(false);
                    repair.setClickable(false);
                    //              Internet(cBox);
                    fillData();
                    cBoxOne++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxOne--;
                    //           Internet(cBox);
                    fillData();
                    if (cBoxOne == 0) {
                        beauty.setClickable(true);
                        repair.setClickable(true);
                    }
                }
            }
        });

        //        checkbox监听函数，实现页面商家刷新
        electricity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    beauty.setClickable(false);
                    repair.setClickable(false);
                    //          Internet(cBox);
                    fillData();
                    cBoxOne++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxOne--;
                    //         Internet(cBox);
                    fillData();
                    if (cBoxOne == 0) {
                        beauty.setClickable(true);
                        repair.setClickable(true);
                    }
                }
            }
        });

        //        checkbox监听函数，实现页面商家刷新
        gasoline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    beauty.setClickable(false);
                    repair.setClickable(false);
                    //           Internet(cBox);
                    fillData();
                    cBoxOne++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxOne--;
                    //            Internet(cBox);
                    fillData();
                    if (cBoxOne == 0) {
                        beauty.setClickable(true);
                        repair.setClickable(true);
                    }
                }
            }
        });

        //        checkbox监听函数，实现页面商家刷新
        lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    beauty.setClickable(false);
                    repair.setClickable(false);
                    //             Internet(cBox);
                    fillData();
                    cBoxOne++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxOne--;
                    //            Internet(cBox);
                    fillData();
                    if (cBoxOne == 0) {
                        beauty.setClickable(true);
                        repair.setClickable(true);
                    }
                }
            }
        });

        //        checkbox监听函数，实现页面商家刷新
        beauty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    trail.setClickable(false);
                    water.setClickable(false);
                    electricity.setClickable(false);
                    gasoline.setClickable(false);
                    lock.setClickable(false);
                    tyre.setClickable(false);
                    //            Internet(cBox);
                    fillData();
                    cBoxTwo++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxTwo--;
                    //            Internet(cBox);
                    fillData();
                    if (cBoxTwo == 0) {
                        trail.setClickable(true);
                        water.setClickable(true);
                        electricity.setClickable(true);
                        gasoline.setClickable(true);
                        lock.setClickable(true);
                        tyre.setClickable(true);
                    }
                }
            }
        });

        //        checkbox监听函数，实现页面商家刷新
        repair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    cBox.add(buttonView.getText().toString());
                    System.out.println(buttonView.getText().toString());
                    trail.setClickable(false);
                    water.setClickable(false);
                    electricity.setClickable(false);
                    gasoline.setClickable(false);
                    lock.setClickable(false);
                    tyre.setClickable(false);
                    //            Internet(cBox);
                    fillData();
                    cBoxTwo++;
                } else {
                    for (int i = 0; i < cBox.size(); i++)
                        if (cBox.get(i).equals(buttonView.getText().toString())) {
                            cBox.remove(i);
                        }
                    cBoxTwo--;
                    //             Internet(cBox);
                    fillData();
                    if (cBoxTwo == 0) {
                        trail.setClickable(true);
                        water.setClickable(true);
                        electricity.setClickable(true);
                        gasoline.setClickable(true);
                        lock.setClickable(true);
                        tyre.setClickable(true);
                    }
                }
            }
        });
    }

//    组件及参数初始化
    private void inItView() {
        State = this;
        loading = (LinearLayout) findViewById(R.id.business_show);//商家条目组件
        list = (ListView) findViewById(R.id.first_business_name);//商家集组件
        trail = (CheckBox) findViewById(R.id.trail);//拖车服务CheckBox
        beauty = (CheckBox) findViewById(R.id.beauty);//汽车美容CheckBox
        tyre = (CheckBox) findViewById(R.id.tyre);//换胎服务CheckBox
        water = (CheckBox) findViewById(R.id.water);//送水服务CheckBox
        electricity = (CheckBox) findViewById(R.id.electricity);//送电服务CheckBox
        lock = (CheckBox) findViewById(R.id.lock);//开锁服务CheckBox
        gasoline = (CheckBox) findViewById(R.id.gasoline);//加油服务CheckBox
        repair = (CheckBox) findViewById(R.id.repair);//汽车维修CheckBox
        cBox = new ArrayList<>();//存储勾选服务容器
//        存储当前经纬度信息
        startlocation = getSharedPreferences("data", MODE_PRIVATE);
        mLocationx = (float) startlocation.getFloat("mlatitude", 0);
        mLocationy = (float) startlocation.getFloat("mlongtitude", 0);
    }

//    实现与后台通行解析所得json文件
    private void fillData() {
        Internet(cBox);
        int cc = 0;
        while (str1 == str || str.equals("first")) {
            cc++;
            try {
                Thread.currentThread().sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cc == 20) break;
        }
        str1 = str;
        try {
//            System.out.println(str + "str");
            jsonInfos = JSonParse.getInfosFromJSON(str1);
            if (jsonInfos == null) {
//                Toast.makeText(WhichBusiness.this, "解析失败", Toast.LENGTH_SHORT).show();
            } else {
                chjsonInfos = jsonInfos;
                jsonInfos = ChJsonInfos(jsonInfos);
                MyBaseAdapter mAdapter = new MyBaseAdapter();
                list.setAdapter(mAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    对所得商家位置信息与当前位置信息进行处理，按照远近排序
    private List<JsonInfo> ChJsonInfos(List<JsonInfo> jsonInfoList) {
        for (int i = 0; i < jsonInfoList.size(); i++)
            jsonInfoList.get(i).setDistance(mLocationx, mLocationy);
        ComparatorUser comparatorUser = new ComparatorUser();
        Collections.sort(jsonInfoList, comparatorUser);
        return jsonInfoList;
    }

//    构造与后台通信json文件
    private String buildJson(String trail, String gasoline,
                             String electricity, String water,
                             String lock, String beauty,
                             String tyre, String repair) {
        JSONObject object = new JSONObject();
        try {
            object.put("trail", trail);
            object.put("gasoline", gasoline);
            object.put("electricity", electricity);
            object.put("water", water);
            object.put("lock", lock);
            object.put("beauty", beauty);
            object.put("tyre", tyre);
            object.put("repair", repair);
            object.put("mLocationx", String.valueOf(mLocationx));
            object.put("mLocationy", String.valueOf(mLocationy));
//            System.out.println(object.toString() + "buildJson");
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

//    实现联网函数
    private void Internet(List<String> services) {
        jsonInfo = null;
        jsonInfos = null;
        Trail = "SERVICE";
        Beauty = "SERVICE";
        Water = "SERVICE";
        Gasoline = "SERVICE";
        Tyre = "SERVICE";
        Electricity = "SERVICE";
        Lock = "SERVICE";
        Repair = "SERVICE";
        for (int i = 0; i < cBox.size(); i++) {
            switch (cBox.get(i).toString()) {
                case "拖车":
                    Trail = "trail";
                    break;
                case "汽车美容":
                    Beauty = "beauty";
                    break;
                case "送水":
                    Water = "water";
                    break;
                case "送油":
                    Gasoline = "gasoline";
                    break;
                case "换胎":
                    Tyre = "tyre";
                    break;
                case "送电":
                    Electricity = "electricity";
                    break;
                case "开锁":
                    Lock = "lock";
                    break;
                case "汽车维修":
                    Repair = "repair";
                    break;
            }
        }
//        Toast.makeText(WhichBusiness.this, "Clicked...", Toast.LENGTH_SHORT).show();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str = HttpUtils.post(Constants.WhichBusiness_URL
                            , buildJson(Trail, Gasoline,
                                    Electricity, Water,
                                    Lock, Beauty, Tyre, Repair));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

//    ListView的baseAdapter适配器
    class MyBaseAdapter extends BaseAdapter {
        //得到item的总数
        public int getCount() {
            //返回ListView Item条目的总数
            return jsonInfos.size();
        }

        //得到item代表的对象
        public Object getItem(int position) {
            return null;
        }

        //得到item的i
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //将list_item.xml文件找出来并转化成View对象
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.business_item, parent, false);
                holder = new ViewHolder();
                holder.mTextView1 = (TextView) convertView.findViewById(R.id.item_name);
                holder.mTextView2 = (TextView) convertView.findViewById(R.id.item_descrption);
                holder.mTextView3 = (TextView) convertView.findViewById(R.id.item_site);
                holder.mTextView4 = (TextView) convertView.findViewById(R.id.item_distance);
                holder.imageView = (ImageView) convertView.findViewById(R.id.item_picture);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            jsonInfo = jsonInfos.get(position);
            System.out.println("wojinlaile");
            holder.mTextView1.setText("商家名称：" + jsonInfo.getBName());
            holder.mTextView2.setText("商家描述：" + jsonInfo.getDescription());
            holder.mTextView3.setText("商家地址：" + jsonInfo.getSite());
            holder.mTextView4.setText("距离：" + Float.parseFloat(format("%.3f", jsonInfo.getDistance())) * 100 + "km");
//            Log.e("fsdfss___________", String.valueOf(jsonInfo.getDistance()));
            String url = Constants.IMG_RESOURCE_URL + "/" + jsonInfo.getPhoto();//构造获得图片URL
            Picasso
                    .with(getApplicationContext())
                    .load(url)
                    .into(holder.imageView);
            return convertView;
        }

        class ViewHolder {
            TextView mTextView1;
            TextView mTextView2;
            TextView mTextView3;
            TextView mTextView4;
            ImageView imageView;
        }
    }

}
