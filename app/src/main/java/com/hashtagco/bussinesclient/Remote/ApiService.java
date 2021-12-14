package com.hashtagco.bussinesclient.Remote;


import com.hashtagco.bussinesclient.Model.MyResponse;
import com.hashtagco.bussinesclient.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService
{
    //Get message Notification
    @Headers(
            {
                    "Content-Type: application/json",
                    "Authorization: key=AAAAPlHxnig:APA91bEMcdDt_t-pf-rWkXNPPCpYnwY3ibxC-tYI0-GuHpaNO5L17I3_J3V1gtaN9V3T1Qb7NPoeL48uEgooPJp93c6Xo2UEzXGlCcA7x2NSZXrXUK8jNuwi-h-yRP8TAsDdWRFxIDP_"
            })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);


}
