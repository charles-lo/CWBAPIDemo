package com.charles.weather.api;

import com.charles.autocachingconveter.Cacheable;
import com.charles.weather.api.model.Response;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface APIService {

    @Cacheable
    @GET("v1/rest/datastore/F-C0032-001")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Single<Response> fetchData(@QueryMap Map<String, Object> params);
}