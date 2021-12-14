package com.hashtagco.bussinesclient.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


//Get Your Location Address
public interface IGoogleServicecs
{

    @GET
    Call<String>getAddressName(@Url String url);

}
