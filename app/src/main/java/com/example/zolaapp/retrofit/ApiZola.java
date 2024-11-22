package com.example.zolaapp.retrofit;

import com.example.zolaapp.model.Friend;
import com.example.zolaapp.model.UserModel;
import com.example.zolaapp.model.Users;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiZola {
    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> signUp(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("uid") String uid
    );

    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> login(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("send_friend_request.php")
    @FormUrlEncoded
    Observable<UserModel> sendFriendRequest(
            @Field("sender_id") int senderId,
            @Field("receiver_id") int receiverId
    );

    @GET("get_users.php")
    Observable<Users> getUsers();

    @GET("logout.php")
    Observable<UserModel> logout();

    @GET("get_friends.php")
    Call<List<Friend>> getFriends(@Query("user_id") int userId);

    @GET("list_firend_request.php")
    Observable<Users> getFriendRequest(@Query("user_id") int userId);

    @POST("handle_friend_request.php")
    @FormUrlEncoded
    Observable<Users> handleFriendRequest(
            @Field("request_id") int userId,
            @Field("action") String action
    );
}
