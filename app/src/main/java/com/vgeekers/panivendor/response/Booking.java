
package com.vgeekers.panivendor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @SerializedName("b_id")
    @Expose
    private String bId;
    @SerializedName("b_brand")
    @Expose
    private String bBrand;
    @SerializedName("b_image")
    @Expose
    private String bImage;
    @SerializedName("u_hno")
    @Expose
    private String uHno;
    @SerializedName("u_locality")
    @Expose
    private String uLocality;
    @SerializedName("u_city")
    @Expose
    private String uCity;
    @SerializedName("u_state")
    @Expose
    private String uState;
    @SerializedName("u_mobile")
    @Expose
    private String uMobile;
    @SerializedName("b_date")
    @Expose
    private String bDate;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getBId() {
        return bId;
    }

    public void setBId(String bId) {
        this.bId = bId;
    }

    public String getBBrand() {
        return bBrand;
    }

    public void setBBrand(String bBrand) {
        this.bBrand = bBrand;
    }

    public String getBImage() {
        return bImage;
    }

    public void setBImage(String bImage) {
        this.bImage = bImage;
    }

    public String getUHno() {
        return uHno;
    }

    public void setUHno(String uHno) {
        this.uHno = uHno;
    }

    public String getULocality() {
        return uLocality;
    }

    public void setULocality(String uLocality) {
        this.uLocality = uLocality;
    }

    public String getUCity() {
        return uCity;
    }

    public void setUCity(String uCity) {
        this.uCity = uCity;
    }

    public String getUState() {
        return uState;
    }

    public void setUState(String uState) {
        this.uState = uState;
    }

    public String getUMobile() {
        return uMobile;
    }

    public void setUMobile(String uMobile) {
        this.uMobile = uMobile;
    }

    public String getBDate() {
        return bDate;
    }

    public void setBDate(String bDate) {
        this.bDate = bDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
