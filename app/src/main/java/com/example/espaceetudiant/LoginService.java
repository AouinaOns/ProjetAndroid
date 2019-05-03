package com.example.espaceetudiant;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService {
    @POST("/isi/oauth2/token")
    Call<AccessToken> loginUser(@Body Login login);
/*    @GET("/api/1.0/isi/cases/start-cases")
    Call<ResponseBody> getSecret(@Header("Authorization") String authHeader);*/

    @GET("api/1.0/isi/case/start-cases")
    Call<List<Process>> getProcess(@Header("Authorization") String authHeader);

    @GET("api/1.0/isi/project/{pro_uid}/activity/{tas_uid}/steps")
    Call<ResponseBody> get_id_forum(@Header("Authorization") String authHeader,
                                @Path("pro_uid") String pro_uid,
                                @Path("tas_uid") String tas_uid);


    @GET("api/1.0/isi/project/{pro_uid}/dynaform/{step_uid_obj}")
    Call<ResponseBody> getforum(@Header("Authorization") String authHeader,
                                @Path("pro_uid") String pro_uid,
                                @Path("step_uid_obj") String tas_uid);
    @POST("api/1.0/isi/cases")
    Call<NewProcessRep> postCase(@Header("Authorization") String authToken,
                                 @Body NewProcess body);

    @GET("api/1.0/isi/cases/draft")
    Call<List<DraftProcessItem>> getDraft(@Header("Authorization") String authToken);




}
