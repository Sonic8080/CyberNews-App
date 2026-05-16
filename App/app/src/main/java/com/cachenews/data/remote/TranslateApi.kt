package com.cachenews.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslateApi {
    @GET("get")
    suspend fun translate(
        @Query("q") text: String,
        @Query("langpair") langPair: String
    ): TranslateResponse
}

data class TranslateResponse(
    @SerializedName("responseData")
    val responseData: ResponseData?,
    @SerializedName("responseStatus")
    val responseStatus: Int?
)

data class ResponseData(
    @SerializedName("translatedText")
    val translatedText: String?,
    @SerializedName("match")
    val match: Double?
)
