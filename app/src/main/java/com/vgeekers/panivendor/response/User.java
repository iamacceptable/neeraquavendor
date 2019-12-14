
package com.vgeekers.panivendor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("v_id")
    @Expose
    private String uId;
    @SerializedName("v_name")
    @Expose
    private String uName;
    @SerializedName("v_mobile")
    @Expose
    private String uMobile;
    @SerializedName("v_hno")
    @Expose
    private String uHno;
    @SerializedName("v_locality")
    @Expose
    private String uLocality;
    @SerializedName("v_state")
    @Expose
    private String uState;
    @SerializedName("v_city")
    @Expose
    private String uCity;
    @SerializedName("v_active")
    @Expose
    private String uActive;

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUMobile() {
        return uMobile;
    }

    public void setUMobile(String uMobile) {
        this.uMobile = uMobile;
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

    public String getUState() {
        return uState;
    }

    public void setUState(String uState) {
        this.uState = uState;
    }

    public String getUCity() {
        return uCity;
    }

    public void setUCity(String uCity) {
        this.uCity = uCity;
    }

    public String getUActive() {
        return uActive;
    }

    public void setUActive(String uActive) {
        this.uActive = uActive;
    }

}
