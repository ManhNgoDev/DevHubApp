package com.manhngo.devhubapp.data.remote.api

import com.manhngo.devhubapp.data.remote.dto.GitHubAccessTokenResponse
import com.manhngo.devhubapp.data.remote.dto.GitHubUserDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GitHubApiService {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String? = null
    ): GitHubAccessTokenResponse

    @GET("user")
    suspend fun getUserProfile(
        @Header("Authorization") authHeader: String
    ): GitHubUserDto
}
