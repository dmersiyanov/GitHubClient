package com.dmersianov.githubclient.api;

import com.dmersianov.githubclient.pojo.LoginResponse;
import com.dmersianov.githubclient.pojo.ReposResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by dmersianov on 13/11/2017.
 */

public interface GitService {

    @GET("user")
    Flowable<LoginResponse> login(@Header("Authorization") String authkey);


    @GET("users/dmersiyanov/repos")
    Flowable<List<ReposResponse>> getRepos();



}
