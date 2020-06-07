package com.example.myfirstapplication;


import retrofit2.Call;
        import retrofit2.http.GET;

public interface PaysAPI {
    @GET("countries.json")
    Call<RestPaysResponse> getPaysResponse();

    @GET("/api/v2/ability")
    Call<RestPaysResponse> getAbility();
}

