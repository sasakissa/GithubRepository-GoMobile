package com.sasakissa.githubsample.entity

import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Int,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("description")
    val description: String?,
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stars: Int
) {

    data class Owner(
        @SerializedName("login")
        val login: String,
        @SerializedName("url")
        val url: String?
    )
}