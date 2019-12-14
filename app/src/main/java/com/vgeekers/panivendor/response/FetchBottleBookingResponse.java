
package com.vgeekers.panivendor.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FetchBottleBookingResponse {

    @SerializedName("booking")
    @Expose
    private List<Booking> booking = null;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
