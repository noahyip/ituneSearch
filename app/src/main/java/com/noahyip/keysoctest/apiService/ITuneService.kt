package com.noahyip.keysoctest.apiService

import com.noahyip.keysoctest.model.ITuneSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITuneService {
    @GET("/search")
    fun search(@Query("term") keyword: String, @Query("offset") offset: Int, @Query("limit") limit: Int): Call<ITuneSearchResponse>

}