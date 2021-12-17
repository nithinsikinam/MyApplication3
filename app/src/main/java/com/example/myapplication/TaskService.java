package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskService {
    //@FormUrlEncoded
    @POST("Editor.php")
    Call<nson> sendpost(
            @Body nson json,//,@Field("Channel") String Channel
            @Query("Channel") String user
    );
    @POST("Joiner.php")
    Call<join> sendpost2(
            @Body join json,//,@Field("Channel") String Channel
            @Query("idUser") String user
    );
}
