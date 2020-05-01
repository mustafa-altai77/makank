package com.example.makank.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiClient {
    private static final String BASE_URL = "http://primarykeysd.com/makank/Anti_Covid19/public/api/";
    private ApiInterface apiInterface;
    private static ApiClient INSTANCE;

//    public ApiClient() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        apiInterface = retrofit.create(ApiInterface.class);
//    }
//
//    public static ApiClient getINSTANCE() {
//        if (null == INSTANCE) {
//            INSTANCE = new ApiClient();
//        }
//        return INSTANCE;
//    }
    public static Retrofit retrofit;
    /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */
    public static Retrofit getRetrofitClient() {
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit == null) {
            //Defining the Retrofit using Builder
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
                    .build();
        }
        return retrofit;
    }
}
