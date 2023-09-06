package com.example.testing.api;

import com.example.testing.model.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/users?page=2")
    Call<RequestBody> getApiResponse();
}
