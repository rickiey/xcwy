package com.sowell.democlient;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/26.
 */

//ItemInfo用于存储服务图片数量等信息

public class ItemInfo implements Serializable {
    //    初始化参数
    private String goodsInfo;
    private ImageView goodsPicture;
    private boolean check;
    private int goodsNum;

    public ItemInfo(String goodsInfo, ImageView goodsPicture, boolean check, int goodsNum) {
        this.goodsInfo = goodsInfo;
        this.goodsPicture = goodsPicture;
        this.check = check;
        this.goodsNum = goodsNum;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setImageView(ImageView goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public ImageView getImageView() {
        return goodsPicture;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean getCheck() {
        return check;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getGoodsNum() {
        return goodsNum;
    }
}
