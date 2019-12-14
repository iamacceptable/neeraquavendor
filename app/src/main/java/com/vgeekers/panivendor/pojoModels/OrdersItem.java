package com.vgeekers.panivendor.pojoModels;

public class OrdersItem {
    private String OrderImage;
    private String OrderDate;
    private String OrderTime;
    private String OrderMobile;
    private String OrderAddress;

    public String getOrderImage() {
        return OrderImage;
    }

    public void setOrderImage(String orderImage) {
        OrderImage = orderImage;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getOrderMobile() {
        return OrderMobile;
    }

    public void setOrderMobile(String orderMobile) {
        OrderMobile = orderMobile;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public OrdersItem(String orderImage, String orderDate, String orderTime, String orderMobile, String orderAddress) {
        OrderImage = orderImage;
        OrderDate = orderDate;
        OrderTime = orderTime;
        OrderMobile = orderMobile;
        OrderAddress = orderAddress;
    }
}
