package com.noahyip.keysoctest.model

import com.google.gson.annotations.SerializedName

data class ITuneSearchResponse(
    @SerializedName("resultCount") var resultCount: Int = 0,
    @SerializedName("results") var results: ArrayList<ITuneSearchResult> = arrayListOf(),
    var hasMore: Boolean = true
)

