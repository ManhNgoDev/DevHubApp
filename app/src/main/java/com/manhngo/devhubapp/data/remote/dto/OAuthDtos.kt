package com.manhngo.devhubapp.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Model hứng kết quả trả về khi trao đổi Auth Code lấy Access Token
 */
data class GitHubAccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("token_type")
    val tokenType: String?,
    @SerializedName("scope")
    val scope: String?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("error_description")
    val errorDescription: String?
)

/**
 * Model hứng thông tin người dùng GitHub (Profile)
 */
data class GitHubUserDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("public_repos")
    val publicRepos: Int,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("html_url")
    val htmlUrl: String?
)
