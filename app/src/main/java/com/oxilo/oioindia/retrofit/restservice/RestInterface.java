package com.oxilo.oioindia.retrofit.restservice;


import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RestInterface {

    String BASE_URL = "https://oioindia.com/";


    @FormUrlEncoded
    @POST("api/add-del-fav.php")
    Call<ResponseBody> add_del_fav(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api/get-favorites.php")
    Call<ResponseBody> get_favorites(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api/login.php")
    Call<ResponseBody> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("api/getbusinessbysearch.php")
    Call<ResponseBody> search(@FieldMap Map<String, String> params);


}
