package com.raka.repositorieslistgithub.data.model.response

import com.google.gson.annotations.SerializedName

data class DataLoginResponse(

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("hasContract")
    val hasContract: String? = null,

    @field:SerializedName("dealerId")
    val dealerId: String? = null,

    @field:SerializedName("dealerName")
    val dealerName: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("nik")
    val nik: String? = null,

    @field:SerializedName("responseDescription")
    val responseDescription: String? = null,

    @field:SerializedName("dob")
    val dob: String? = null,

    @field:SerializedName("scope")
    val scope: String? = null,

    @field:SerializedName("customerId")
    val customerId: String? = null,

    @field:SerializedName("forceChangePassword")
    val forceChangePassword: String? = null,

    @field:SerializedName("expires_in")
    val expiresIn: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)