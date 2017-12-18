package com.queenievatcha.otoro1;

import java.util.Map;

/**
 * Created by Queenievatcha on 16-Dec-17.
 */

public class Order {
    String id;
    String date;
    String name;
    String address;
    String phoneNum;
    String paymentMethod;
    String totalPrice;
    Map<String,Food> menu;

    public Order() {
    }

    public Order(String id, String date, String name, String address, String phoneNum, String paymentMethod, String totalPrice, Map<String, Food> menu) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.menu = menu;
    }
}
    