package com.sowell.democlient;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/8/11.
 */

//对得到商家进行按距离排序class

public class ComparatorUser implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        JsonInfo distance1 = (JsonInfo) o1;
        JsonInfo distance2 = (JsonInfo) o2;
        int flag = String.valueOf(distance1
                .getDistance())
                .compareTo(String.valueOf(distance2.getDistance()));
        if (flag == 0) {
            return distance1.getId().compareTo(distance2.getId());
        } else return flag;
    }
}
