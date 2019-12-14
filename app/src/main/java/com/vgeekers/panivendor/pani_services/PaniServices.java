package com.vgeekers.panivendor.pani_services;

import com.vgeekers.panivendor.response.CategoryResponse;
import com.vgeekers.panivendor.response.CityResponse;
import com.vgeekers.panivendor.response.CommonResponse;
import com.vgeekers.panivendor.response.FetchAllStatesResponse;
import com.vgeekers.panivendor.response.FetchBottleBookingResponse;
import com.vgeekers.panivendor.response.LocalityResponse;
import com.vgeekers.panivendor.response.LoginResponse;
import com.vgeekers.panivendor.response.UserProfileResponse;
import com.vgeekers.panivendor.response.VendorPreviousDetailResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PaniServices {

    @GET("fetch_all_states.php")
    Call<FetchAllStatesResponse> getAllStates();

    @POST("sign_up.php")
    @FormUrlEncoded
    Call<CommonResponse> getSignUpResponse(@Field("name") String userName, @Field("mobile") String mobileNumber, @Field("password") String password, @Field("hno") String address, @Field("state") String state, @Field("city") String city, @Field("locality") String locality);

    @POST("update_profile.php")
    @FormUrlEncoded
    Call<CommonResponse> getUpdateProfileResponse(@Field("vendorId") String userId, @Field("name") String userName, @Field("mobile") String mobileNumber, @Field("hno") String address, @Field("state") String state, @Field("city") String city, @Field("locality") String locality);

    @POST("orders.php")
    @FormUrlEncoded
    Call<VendorPreviousDetailResponse> getTransactionsDetails(@Field("vendorId") String vendorId, @Field("from") String from, @Field("to") String to, @Field("category") String category);

    @POST("login.php")
    @FormUrlEncoded
    Call<LoginResponse> getLoginResponse(@Field("mobile") String mobileNumber, @Field("password") String password);

    @POST("forgot_password.php")
    @FormUrlEncoded
    Call<CommonResponse> getForgotPasswordResponse(@Field("mobile") String mobileNumber, @Field("password") String password);

    @POST("fetch_all_cities.php")
    @FormUrlEncoded
    Call<CityResponse> getCitiesResponse(@Field("state") String state);

    @GET("fetch_categories.php")
    Call<CategoryResponse> getCategories();

    @POST("fetch_all_localities.php")
    @FormUrlEncoded
    Call<LocalityResponse> getLocalitiesResponse(@Field("city") String city);

    @POST("fetch_profile.php")
    @FormUrlEncoded
    Call<UserProfileResponse> getUserProfileResponse(@Field("vendorId") String userId);

    @POST("fetch_booking.php")
    @FormUrlEncoded
    Call<FetchBottleBookingResponse> getBottleRequestsList(@Field("vendorId") String vendorId);

    @POST("booking_acceptance.php")
    @FormUrlEncoded
    Call<CommonResponse> doBookingConfirmation(@Field("bId") String bottleId, @Field("status") String status);
}
