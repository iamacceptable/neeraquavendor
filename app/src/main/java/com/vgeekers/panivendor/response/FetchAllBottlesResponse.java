
package com.vgeekers.panivendor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchAllBottlesResponse {

    @SerializedName("bottles")
    @Expose
    private List<Bottle> bottles = null;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;

    public List<Bottle> getBottles() {
        return bottles;
    }

    public void setBottles(List<Bottle> bottles) {
        this.bottles = bottles;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
