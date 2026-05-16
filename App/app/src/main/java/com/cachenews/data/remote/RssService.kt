package com.cachenews.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {
    @GET
    suspend fun fetchFeed(@Url url: String): ResponseBody
}
