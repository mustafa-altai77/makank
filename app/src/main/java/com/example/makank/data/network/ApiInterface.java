package com.example.makank.data.network;

import androidx.annotation.FloatRange;

import com.example.makank.data.model.City;
import com.example.makank.data.model.Details;
import com.example.makank.data.model.Disease;
import com.example.makank.data.model.Filresponse;
import com.example.makank.data.model.Hospital;
import com.example.makank.data.model.Local;
import com.example.makank.data.model.Member;
import com.example.makank.data.model.News;
import com.example.makank.data.model.Person;
import com.example.makank.data.model.Request;
import com.example.makank.data.model.SendNumber;
import com.example.makank.data.model.State;
import com.example.makank.data.model.Statistc;
import com.example.makank.data.model.Verify;

import java.util.ArrayList;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("state/{id}/cities?data=true")
    Call<List<City>> getCity(@Path("id") String id);

    @GET("cities/{id}/locals?data=true")
    Call<List<Local>> getLocal(@Path("id") String id);

    @GET("state?data=true")
    Call<List<State>> getState();

    @GET("diseases?data=true")
    Call<List<Disease>> getDisease();

    @GET("news?data=true")
    Call<List<News>> getNews();

    @GET("person/{id}/grouprequest?data=true")
    Call<List<Request>> getRequst(@Header ("Authorization") String token,
                                  @Path("id") String id);

    @GET("caseStates?data=true")
    Call<List<Statistc>> getCases();
    @GET("person/{id}/group?data=true")
    Call<List<Member>> getMygroup(@Header ("Authorization") String token,
                                  @Path("id") String id);

    @GET("person/{id}/saw?data=true")
    Call<List<Member>> getMyseen(@Header ("Authorization") String token,
                                 @Path("id") String id);
    @GET("detials")
    Call<Details> getMyData(@Header("Authorization") String token);

    @POST("person")
    Call<Person> getUserRegi(@Header("Authorization") String token,
                             @Body Person person);
    @GET("person/{id}/diseases?data=true")
    Call<List<Disease>> getMydisease(@Path("id") String id,
    @Header ("Authorization") String token);
    @FormUrlEncoded
    @POST("person/{id}/diseases")
    Call<Disease> getDiseaseRegi(
                                 @Path("id") String id,
                                 @Field("disease_id[]") ArrayList<Integer> des,
                                 @Header ("Authorization") String token
    );

    @GET("person/{id}")
    Call<Person> getSaw(@Header ("Authorization") String token,
                        @Path("id") String result);
    @GET("hospitals?data=true")
    Call<List<Hospital>> getHospitals();

    @POST("person/{id}/saw")
    @FormUrlEncoded
    Call<Member> addSeen(@Header ("Authorization") String token,
                         @Path("id") String my_id,
                         @Field("seen_person_id") int person_id,
                         @Field("lan") double lan,
                         @Field("lat") double lat);

    @FormUrlEncoded
    @POST("person/{id}/group")
    Call<Member> addMem(@Header ("Authorization") String token,
                        @Path("id") String my_id,
                        @Field("member_id") int member_id);

    @FormUrlEncoded
    @POST("person/{id}/notifications")
    Call<Person> sendNotifi(@Header ("Authorization") String token,
                            @Path("id") String my_id,
                            @Field("postion_description") String local,
                            @Field("status_description") String notifi,
                            @Field("phone") String phone);

    @Multipart
    @POST("person/{id}/volunteer")
    Call<Filresponse>upload(@Header ("Authorization") String token,
                            @Path("id") String user_id,
                            @Part MultipartBody.Part file);
    @FormUrlEncoded
    @POST("person/{id}/grouprequest")
    Call<Request> getRequest(@Header ("Authorization") String token,
                             @Path("id") String id,
                             @Field("owner_id") int owner_id,
                             @Query("accept") Boolean accept);
    @FormUrlEncoded
    @POST("register")
    Call<SendNumber> sendNum(@Field("phone_number") String mobile);
    @FormUrlEncoded
    @POST("verify")
    Call<Verify> verfiy(@Field("code") String code);
}