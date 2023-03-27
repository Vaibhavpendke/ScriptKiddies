package com.example.scriptkiddies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ReyrofitAPI {
    @GET
    Call<MsgModal> getMessage(@Url String url);
}
