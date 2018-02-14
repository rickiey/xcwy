package com.sowell.democlient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sowell.democlient.constants.Constants;
import com.sowell.democlient.utils.HttpUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TransForm extends AppCompatActivity {
    //    定义所选变量和组件
    private int[] icons = {R.drawable.backpicture};
    private int buyNum = 0;
    private String bLocationx;
    private String bLocationy;
    private double buyPrice = 0;
    private List<String> Servicechuang;
    private int cBoxOne = 0, cBoxTwo = 0;
    private ListView list;
    private List<JsonInfo> jsonInfos;
    private JsonInfo jsonInfo;
    private String str = "first", str1, name, phone, address, info, picture, id;
    private TextView buy_state;
    private TextView business_name;
    private TextView business_phone;
    private TextView business_address;
    private TextView business_info;
    private ImageView business_photo;
    private Intent serviceDetails;
    private Intent whichBussiness;
    private ImageButton business_location;
    private int state1 = 0, statet1 = 0, state2 = 0, statet2 = 0;
    public static TransForm State=null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans_form);
        init();
        fillData();
//        导航监听函数
        business_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransForm.this, routePlanActivity.class);
                startActivity(intent);
            }

            ;
        });
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ListView goodsInfo = (ListView) findViewById(R.id.goods_info);
//                jsonInfo = jsonInfos.get(position);
////                buy_state = (TextView) goodsInfo.getChildAt(position).findViewById(R.id.buy_state123);
//                serviceDetails = new Intent(getApplicationContext(), ServiceDetails.class);
//                serviceDetails.putExtra("buy_name", jsonInfo.getBName());
//                serviceDetails.putExtra("buy_state", jsonInfo.getState()  /*buy_state.getText().toString()*/);
//                serviceDetails.putExtra("buy_description", jsonInfo.getDescription());
//                startActivity(serviceDetails);
//            }
//        });
    }

    //初始化各组件变量信息
    private void init() {
        State=this;
        business_info = (TextView) findViewById(R.id.business_info);    //商家信息组件
        business_address = (TextView) findViewById(R.id.business_address);  //商家地址组件
        business_name = (TextView) findViewById(R.id.business_name);    //商家名称组件
        business_phone = (TextView) findViewById(R.id.business_phone);  //商家电话组件
        business_photo = (ImageView) findViewById(R.id.business_picture); //商家照片组件
        buy_state = (TextView) findViewById(R.id.buy_state);    //服务预约状态组件
        list = (ListView) findViewById(R.id.goods_info);    //商检显示列表组件
        business_location = (ImageButton) findViewById(R.id.address_button);    //商家位置组件
        whichBussiness = getIntent();   //获得上一层意图
        Servicechuang = new ArrayList<>();  //存储服务容器
        id = whichBussiness.getStringExtra("business_id");  //获得商家ID
        name = whichBussiness.getStringExtra("business_name");  //获得商家名称
        phone = whichBussiness.getStringExtra("business_phone");    //获得商家电话
        info = whichBussiness.getStringExtra("business_description");  //获得商家描述
        address = whichBussiness.getStringExtra("business_site");   //获得商家地址
        picture = whichBussiness.getStringExtra("business_photo");  //获得商家图片
        cBoxOne = whichBussiness.getIntExtra("cBoxOne", 0); //
        cBoxTwo = whichBussiness.getIntExtra("cBoxTwo", 0); //
        bLocationx = whichBussiness.getStringExtra("business_bLocationx");  //
        bLocationy = whichBussiness.getStringExtra("business_bLocationy");  //
        business_name.setText("商家名字：" + name);  //添加商家名称
        business_phone.setText("商家电话：" + phone);    //添加商家电话
        business_info.setText("商家描述：" + info);  //添加商家描述
        business_address.setText("商家地址：" + address);    //添加商家地址
        String url = Constants.IMG_RESOURCE_URL + "/" + picture;    //定义图片获得路径URL
        Picasso
                .with(getApplicationContext())
                .load(url)
                .into(business_photo);  //添加图片
        //存储商家经纬度用于导航
        SharedPreferences endlocation = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = endlocation.edit();
        editor1.putFloat("endlatitude", Float.parseFloat(bLocationx));
//        Log.e("asda___________",bLocationx);
        editor1.putFloat("endlongtitude", Float.parseFloat(bLocationy));
//        Log.e("asda___________",bLocationy);
        editor1.commit();
    }

    //  设置与服务器交互json文件并对所得json文件解析
    private void fillData() {
        Internet();
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
                Toast.makeText(TransForm.this, "解析失败", Toast.LENGTH_SHORT).show();
            } else {
                MyBaseAdapter mAdapter = new MyBaseAdapter();
                list.setAdapter(mAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //定义ListView的BaseAdapter适配器
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
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //将list_item.xml文件找出来并转化成View对象
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        getApplicationContext()).inflate(R.layout.list_item,
                        parent, false);
                holder = new ViewHolder();
                holder.mTextView1 = (TextView) convertView.findViewById(R.id.services_name);
                holder.mTextView2 = (TextView) convertView.findViewById(R.id.buy_state);
                holder.mTextView3 = (TextView) convertView.findViewById(R.id.services_description);
                holder.mButton1 = (Button) convertView.findViewById(R.id.Add);
                holder.mButton2 = (Button) convertView.findViewById(R.id.Dec);
                holder.imageView = (ImageView) convertView.findViewById(R.id.item_picture);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            jsonInfo = jsonInfos.get(position);
            holder.mTextView1.setText("服务名称：" + jsonInfo.getBName());
            holder.mTextView2.setText("" + jsonInfo.getState());
            holder.mTextView3.setText("商品价格：" + jsonInfo.getDescription());
            switch (jsonInfo.getBName()) {
                case "拖车":
                    holder.imageView.setBackgroundResource(R.drawable.trail);
                    break;
                case "汽车美容":
                    holder.imageView.setBackgroundResource(R.drawable.beauty);
                    break;
                case "送水":
                    holder.imageView.setBackgroundResource(R.drawable.water);
                    break;
                case "加油":
                    holder.imageView.setBackgroundResource(R.drawable.gasoline);
                    break;
                case "换胎":
                    holder.imageView.setBackgroundResource(R.drawable.tyre);
                    break;
                case "搭电":
                    holder.imageView.setBackgroundResource(R.drawable.electricity);
                    break;
                case "开锁":
                    holder.imageView.setBackgroundResource(R.drawable.lock);
                    break;
                case "汽车维修":
                    holder.imageView.setBackgroundResource(R.drawable.repair);
                    break;
            }

//            添加服务按钮监听，道路救援与服务预约不能同时选择
            holder.mButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View source) {
                    TextView buy_num = (TextView) findViewById(R.id.buy_num);
                    JsonInfo JI = jsonInfos.get(position);
                    if (cBoxOne > 0
                            && !(JI.getBName().equals("汽车美容"))
                            && !(JI.getBName().equals("汽车维修"))) {
                        if (holder.mTextView2.getText().equals("未预约")) {
                            JI.setState("已预约");
                            Servicechuang.add(JI.getBName());
                            holder.mTextView2.setText(JI.getState());
                            buyNum++;
                            buyPrice += Double.parseDouble(JI.getDescription());
                            buy_num.setText("    已选商品数：" + buyNum);
                        } else if (holder.mTextView2.getText().equals("已预约")) {
                            holder.mTextView2.setText(JI.getState());
                        } else {
                            holder.mTextView2.setText("已预约");
                        }
                    } else if (cBoxTwo > 0
                            && !(JI.getBName().equals("拖车"))
                            && !(JI.getBName().equals("加油"))
                            && !(JI.getBName().equals("搭电"))
                            && !(JI.getBName().equals("开锁"))
                            && !(JI.getBName().equals("送水"))
                            && !(JI.getBName().equals("换胎"))) {
                        if (holder.mTextView2.getText().equals("未预约")) {
                            JI.setState("已预约");
                            Servicechuang.add(JI.getBName());
                            holder.mTextView2.setText(JI.getState());
                            buyNum++;
                            buy_num.setText("    已选商品数：" + buyNum);
                        } else if (holder.mTextView2.getText().equals("已预约")) {
                            holder.mTextView2.setText(JI.getState());
                        } else {
                            holder.mTextView2.setText("已预约");
                        }
                    } else if (cBoxOne == 0 && cBoxTwo == 0) {
                        if ((JI.getBName().equals("汽车美容")
                                || JI.getBName().equals("汽车维修")) && state1 == 0) {
                            if (holder.mTextView2.getText().equals("未预约")) {
                                JI.setState("已预约");
                                buyNum++;
                                Servicechuang.add(JI.getBName());
                                holder.mTextView2.setText(JI.getState());
                                buy_num.setText("    已选商品数：" + buyNum);
                            } else if (holder.mTextView2.getText().equals("已预约")) {
                                holder.mTextView2.setText(JI.getState());
                            } else {
                                holder.mTextView2.setText("已预约");
                            }
                            statet1++;
                            state2 = 1;
                        } else if ((JI.getBName().equals("拖车")
                                || JI.getBName().equals("搭电")
                                || JI.getBName().equals("加油")
                                || JI.getBName().equals("开锁")
                                || JI.getBName().equals("送水")
                                || JI.getBName().equals("换胎")) && state2 == 0) {
                            if (holder.mTextView2.getText().equals("未预约")) {
                                JI.setState("已预约");
                                holder.mTextView2.setText(JI.getState());
                                buyNum++;
                                buyPrice += Double.parseDouble(JI.getDescription());
                                Servicechuang.add(JI.getBName());
                                buy_num.setText("    已选商品数：" + buyNum);
                            } else if (holder.mTextView2.getText().equals("已预约")) {
                                holder.mTextView2.setText(JI.getState());
                            } else {
                                holder.mTextView2.setText("已预约");
                            }
                            statet2++;
                            state1 = 1;
                        } else if (statet1 > 0 || statet2 > 0) {
                            Toast.makeText(TransForm.this, "道路救援服务和预约服务不能同时选择！！", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TransForm.this, "道路救援服务和预约服务不能同时选择！！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

//            添加服务按钮监听，道路救援与服务预约不能同时选择
            holder.mButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View source) {
                    TextView buy_num = (TextView) findViewById(R.id.buy_num);
                    JsonInfo JI = jsonInfos.get(position);
                    if (cBoxOne > 0
                            && !(JI.getBName().equals("汽车美容"))
                            && !(JI.getBName().equals("汽车维修"))) {
                        if (holder.mTextView2.getText().equals("已预约")) {
                            JI.setState("未预约");
                            holder.mTextView2.setText(JI.getState());
                            buyNum--;
                            for (int i = 0; i < Servicechuang.size(); i++) {
                                if (Servicechuang.get(i).equals(JI.getBName())) {
                                    Servicechuang.remove(i);
                                }
                            }
                            buyPrice -= Double.parseDouble(JI.getDescription());
                            buy_num.setText("    已选商品数：" + buyNum);
                        } else if (holder.mTextView2.getText().equals("未预约")) {
                            holder.mTextView2.setText(JI.getState());
                        } else {
                            holder.mTextView2.setText(JI.getState());
                        }
                    } else if (cBoxTwo > 0
                            && !(JI.getBName().equals("拖车"))
                            && !(JI.getBName().equals("加油"))
                            && !(JI.getBName().equals("搭电"))
                            && !(JI.getBName().equals("开锁"))
                            && !(JI.getBName().equals("送水"))
                            && !(JI.getBName().equals("换胎"))) {
                        if (holder.mTextView2.getText().equals("已预约")) {
                            JI.setState("未预约");
                            holder.mTextView2.setText(JI.getState());
                            buyNum--;
                            for (int i = 0; i < Servicechuang.size(); i++) {
                                if (Servicechuang.get(i).equals(JI.getBName())) {
                                    Servicechuang.remove(i);
                                }
                            }
                            buy_num.setText("    已选商品数：" + buyNum);
                        } else if (holder.mTextView2.getText().equals("已预约")) {
                            holder.mTextView2.setText(JI.getState());
                        } else {
                            holder.mTextView2.setText("已预约");
                        }
                    } else if (cBoxOne == 0 && cBoxTwo == 0) {
                        if ((JI.getBName().equals("汽车美容")
                                || JI.getBName().equals("汽车维修")) && state2 == 1) {
                            if (holder.mTextView2.getText().equals("已预约")) {
                                JI.setState("未预约");
                                holder.mTextView2.setText(JI.getState());
                                buyNum--;
                                for (int i = 0; i < Servicechuang.size(); i++) {
                                    if (Servicechuang.get(i).equals(JI.getBName())) {
                                        Servicechuang.remove(i);
                                    }
                                }
                                buy_num.setText("    已选商品数：" + buyNum);
                            } else if (holder.mTextView2.getText().equals("未预约")) {
                                holder.mTextView2.setText(JI.getState());
                            } else {
                                holder.mTextView2.setText("未预约");
                            }
                            statet1--;
                            if (statet1 == 0) state2 = 0;
                        } else if ((JI.getBName().equals("拖车")
                                || JI.getBName().equals("加油")
                                || JI.getBName().equals("搭电")
                                || JI.getBName().equals("开锁")
                                || JI.getBName().equals("送水")
                                || JI.getBName().equals("换胎")) && state1 == 1) {
                            if (holder.mTextView2.getText().equals("已预约")) {
                                JI.setState("未预约");
                                holder.mTextView2.setText(JI.getState());
                                buyNum--;
                                buyPrice -= Double.parseDouble(JI.getDescription());
                                for (int i = 0; i < Servicechuang.size(); i++) {
                                    if (Servicechuang.get(i).equals(JI.getBName())) {
                                        Servicechuang.remove(i);
                                    }
                                }
                                buy_num.setText("    已选商品数：" + buyNum);
                            } else if (holder.mTextView2.getText().equals("未预约")) {
                                holder.mTextView2.setText(JI.getState());
                            } else {
                                holder.mTextView2.setText("未预约");
                            }
                            statet2--;
                            if (statet2 == 0) state1 = 0;
                        } else if ((statet1 > 0 || statet2 > 0)) {
                            Toast.makeText(TransForm.this, "道路救援服务和预约服务不能同时选择！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView mTextView1;
            TextView mTextView2;
            TextView mTextView3;
            Button mButton1;
            Button mButton2;
            ImageView imageView;
        }

    }

    //    拨打商家电话按钮监听
    public void MakePhone(View source) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    //    商家详情界面跳转
    public void BusinessPicture(View source) {
        Intent businessDetail = new Intent(this, BusinessDetails.class);
        businessDetail.putExtra("business_name", name);
        businessDetail.putExtra("business_phone", phone);
        businessDetail.putExtra("business_photo", picture);
        businessDetail.putExtra("business_site", address);
        businessDetail.putExtra("business_description", info);
        businessDetail.putExtra("business_bLocationx", bLocationx);
        businessDetail.putExtra("business_bLocationy", bLocationy);
        startActivity(businessDetail);
    }

    //    建立与后台通讯json文件
    private String buildJson(String Id) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", Id);
            return object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    实现网络通讯函数
    private void Internet() {
        jsonInfo = null;
        jsonInfos = null;
//        Toast.makeText(TransForm.this, "Clicked...", Toast.LENGTH_SHORT).show();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str = HttpUtils.post(Constants.TransForm_URL
                            , buildJson(id));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    //    提交订单按钮监听函数
    public void Order(View v) {
        String servicechuang = "";
        String zhuangtai = "0";

        for (int i = 0; i < Servicechuang.size(); i++) {
            servicechuang += Servicechuang.get(i) + " ";
            if (Servicechuang.get(i).equals("拖车") ||
                    Servicechuang.get(i).equals("加油") ||
                    Servicechuang.get(i).equals("搭电") ||
                    Servicechuang.get(i).equals("开锁") ||
                    Servicechuang.get(i).equals("送水") ||
                    Servicechuang.get(i).equals("换胎")) {
                zhuangtai = "1";
            } else zhuangtai = "2";
        }

        Intent Orders = new Intent(this, setOrderForm.class);
        Orders.putExtra("buy_name", servicechuang);
        Orders.putExtra("besiness_name", name);
//        Log.e("传递的名字", name);
//        Log.e("buyprice-------------", Double.toString(buyPrice));
        Orders.putExtra("price", buyPrice);
        Orders.putExtra("condition", zhuangtai);
        Orders.putExtra("phone", phone);
        Orders.putExtra("id", id);

        if (zhuangtai.equals("0")) {
            Toast.makeText(getApplicationContext(), "未选择服务", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(Orders);
    }

}
