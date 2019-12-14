package com.vgeekers.panivendor.retrofit;

import com.vgeekers.panivendor.pani_services.PaniServices;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {

    private RetrofitApi() {
        /**/
    }

    private static final String BASE_URL = "https://www.winspiration.co/demo/pani/apiVendor/";

    private static PaniServices sPaniServices = null;

    public static PaniServices getPaniServicesObject() {
        if (null == sPaniServices) {
            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            sPaniServices = retrofit.create(PaniServices.class);
        }
        return sPaniServices;
    }
}
