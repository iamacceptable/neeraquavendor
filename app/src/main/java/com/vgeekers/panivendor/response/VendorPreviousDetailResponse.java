
package com.vgeekers.panivendor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorPreviousDetailResponse extends CommonResponse {

    @SerializedName("totalOrders")
    @Expose
    private String totalOrders = "";
    @SerializedName("totalPayment")
    @Expose
    private String totalPayment = "";
    @SerializedName("freeBottles")
    @Expose
    private String freeBottles = "";

    public String getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(String totalOrders) {
        this.totalOrders = totalOrders;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getFreeBottles() {
        return freeBottles;
    }

    public void setFreeBottles(String freeBottles) {
        this.freeBottles = freeBottles;
    }
}
