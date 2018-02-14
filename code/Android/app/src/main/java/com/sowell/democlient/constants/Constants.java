package com.sowell.democlient.constants;

import okhttp3.MediaType;

/**
 * Created by bruce on 2017/7/27.
 */

public class Constants {
    public final static String BASE_URL="http://192.168.32.33:8080/car/";
    //User
    public final static String LOGIN_URL =          BASE_URL + "UserLoginServlet";
    public final static String REGISTER_URL =       BASE_URL + "UserRegisterServlet";
    public final static String FORGET_URL =         BASE_URL + "ForgetPasswordServlet";
    public final static String RESET_URL =           BASE_URL + "ResetPasswordServlet";
    public final static String CHANGE_CAR_URL =     BASE_URL + "UserAlterInformationServlet";
    public final static String CHANGE_PASSWORD_URL = BASE_URL + "UserAlterPasswordServlet";
    public final static String WhichBusiness_URL=   BASE_URL + "QueryBusinessServlet";
    public final static String TransForm_URL=       BASE_URL + "QueryServiceDetailServlet";
    public final static String setOrderForm_URL=    BASE_URL + "UserSubmitOrderServlet";
    public final static String getInform_URL=       BASE_URL + "UserCheckOrderServlet";
    //Staff
    public final static String STAFF_LOGIN_URL =    BASE_URL + "StaffLoginServlet";
    public static final  String AlterPassword=      BASE_URL + "StaffAlterPasswordServlet";
    public static final  String AlterName=          BASE_URL + "StaffAlterNameServlet";
    public static final String StaffCheckOrderServlet = BASE_URL + "StaffCheckOrderServlet";
    public static final  String StaffFinishOrderServlet = BASE_URL + "StaffFinishOrderServlet";
    public static final String Cancel_URL = BASE_URL +"UserCancelOrderServlet";

    public final static String getMyOrder_URL=BASE_URL +"UserCheckAllOrderServlet";
    public final static String IMG_RESOURCE_URL = BASE_URL+"upload";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
}
