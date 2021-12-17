package com.example.myapplication;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("upload_document.php")
    Call<ResponsePOJO> uploadDocument(
            @Field("PDF") String encodedPDF,
            @Field("title") String title,
            @Field("type") String type,
            @Field("channel") String channel

    );

}
