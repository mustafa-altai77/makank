package com.example.makank.data.network;

import androidx.annotation.FloatRange;

import com.example.makank.data.model.City;
import com.example.makank.data.model.Disease;
import com.example.makank.data.model.Filresponse;
import com.example.makank.data.model.Local;
import com.example.makank.data.model.Member;
import com.example.makank.data.model.News;
import com.example.makank.data.model.Person;
import com.example.makank.data.model.Request;
import com.example.makank.data.model.State;
import com.example.makank.data.model.Statistc;

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
    Call<List<Request>> getRequst(@Path("id") String id);

    @GET("cases")
    Call<Statistc> getCases();
    @GET("person/{id}/group?data=true")
    Call<List<Member>> getMygroup(@Path("id") String id);

    @GET("person/{id}/saw?data=true")
    Call<List<Member>> getMyseen(@Path("id") String id);
    @GET("person/{id}/?data=true")
    Call<Person> getMyData(@Path("id") String id);

    @POST("person")
    Call<Person> getUserRegi(@Body Person person);
    @GET("person/{id}/diseases?data=true")
    Call<List<Disease>> getMydisease(@Path("id") String id);
    @FormUrlEncoded
    @POST("person/{id}/diseases")
    Call<Disease> getDiseaseRegi(@Path("id") String id,
                                 @Field("disease_id[]") ArrayList<Integer> des
    );

    @POST("person/{id}/saw")
    Call<Person> getSaw(@Path("id") String result);

    @POST("person/{id}/saw")
    @FormUrlEncoded
    Call<Member> addSeen(@Path("id") String my_id,
                         @Field("seen_person_id") String member_id,
                         @Field("lan") double lan,
                         @Field("lat") double lat);

    @FormUrlEncoded
    @POST("person/{id}/group")
    Call<Member> addMem(@Path("id") String my_id,
                        @Field("member_id") String member_id);

    @FormUrlEncoded
    @POST("person/{id}/notifications")
    Call<Person> sendNotifi(@Path("id") String my_id,
                            @Field("postion_description") String local,
                            @Field("status_description") String notifi);
    @Multipart
    @POST("person/{id}/volunteer")
    Call<Filresponse>upload(@Path("id") String user_id,
                            @Part("person_id") String id,

                            @Part MultipartBody.Part file);
    @FormUrlEncoded
    @POST("person/{id}/grouprequest")
    Call<Request> getRequest(@Path("id") String id,
                             @Field("owner_id") int owner_id,
                             @Query("accept") Boolean accept);
}