package com.example.android.andela.service;

import com.example.android.andela.model.UserList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by UTYLO on 4/26/2017.
 *
 */
public interface RestApiService {

    @GET("/search/users")
    Call<UserList> getUserList(@Query("q") String filter);

}

