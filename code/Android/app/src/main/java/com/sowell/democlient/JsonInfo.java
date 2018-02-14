package com.sowell.democlient;

/**
 * Created by Administrator on 2017/7/29.
 */

//存储json对象信息

public class JsonInfo {

//    定义json数据接收对象

    private String id;
    private String tyre;
    private String lock;
    private String water;
    private String electricity;
    private String gasoline;
    private String trailer;
    private String beauty;
    private String repair;
    private String state = "未预约";
    private String bname;
    private String name;
    private String photo;
    private String phone;
    private String site;
    private String description;
    private String score;
    private String person;
    private String bLocationx;
    private String bLocationy;
    private float distance;

    public void setDistance(float mLocationx, float mlocationy) {
        this.distance = (float) Math.sqrt(
                (Float.parseFloat(bLocationx) - mLocationx)
                        * (Float.parseFloat(bLocationx) - mLocationx)
                        + (Float.parseFloat(bLocationy) - mlocationy)
                        * (Float.parseFloat(bLocationy) - mlocationy));
    }

    public float getDistance() {
        return distance;
    }

    public void setbLocationx(String bLocationx) {
        this.bLocationx = bLocationx;
    }

    public String getbLocationx() {
        return bLocationx;
    }

    public void setbLocationy(String bLocationy) {
        this.bLocationy = bLocationy;
    }

    public String getbLocationy() {
        return bLocationy;
    }

    public JsonInfo() {
        this.state = "未预约";
    }

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }

    public String getTyre() {
        return tyre;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBName(String bname) {
        this.bname = bname;
    }

    public String getBName() {
        return bname;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSite() {
        return site;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPerson() {
        return person;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getLock() {
        return lock;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getWater() {
        return water;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getElectricity() {
        return electricity;
    }

    public void setGasoline(String gasoline) {
        this.gasoline = gasoline;
    }

    public String getGasoline() {
        return gasoline;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setBeauty(String beauty) {
        this.beauty = beauty;
    }

    public String getBeauty() {
        return beauty;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String getRepair() {
        return repair;
    }
}