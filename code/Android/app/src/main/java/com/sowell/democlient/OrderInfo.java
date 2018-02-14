package com.sowell.democlient;

/**
 * Created by lenovo on 2017/8/8.
 */


//解析json文件所需各变量信息

public class OrderInfo {
    //    定义各变量
    private String user_carnum;
    private String user_name;
    private String user_mobile;
    private String address;
    private String user_car;
    private String timestart;
    private String business_name;
    private String type;
    private String price;
    private String description;
    private String state;
    private String timeend;
    private String id;
    private String business_phone;
    private String bLocationx;
    private String bLocationy;

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

    public String getUsecarnum() {
        return user_carnum;
    }

    public void setUsecarnum(String usecarnum) {
        this.user_carnum = usecarnum;
    }

    public String getUsename() {
        return user_name;
    }

    public void setUsename(String usename) {
        this.user_name = usename;
    }

    public String getUsemobile() {
        return user_mobile;
    }

    public void setUsemobile(String usemobile) {
        this.user_mobile = usemobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String site) {
        this.address = site;
    }

    public String getUsecar() {
        return user_car;
    }

    public void setUsecar(String usecar) {
        this.user_car = usecar;
    }

    public String getStarttime() {
        return timestart;
    }

    public void setStarttime(String starttime) {
        this.timestart = starttime;
    }

    public String getBusinessname() {
        return business_name;
    }

    public void setBusinessname(String businessname) {
        this.business_name = businessname;
    }

    public String getType() {
        return type;
    }

    public void setServicename(String servicename) {
        this.type = servicename;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String message) {
        this.description = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getBusiness_phone() {
        return business_phone;
    }

    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }

}
