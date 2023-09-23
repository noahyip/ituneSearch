package com.noahyip.keysoctest.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.noahyip.keysoctest.RetrofitClient
import com.noahyip.keysoctest.model.ITuneSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel() {
    var iTuneSearchResponse = MutableLiveData<ITuneSearchResponse>()

    fun search(keyword: String) {
        keyword.trim().replace(" ", "+")
        RetrofitClient().getImgurService().search(keyword, 20, 20).enqueue(object : Callback<ITuneSearchResponse?> {

            override fun onResponse(call: Call<ITuneSearchResponse?>, response: Response<ITuneSearchResponse?>) {
                response.body()?.let {
                    if (it.resultCount <= 0) {

                    } else {
                        iTuneSearchResponse.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<ITuneSearchResponse?>, t: Throwable) {
                call.cancel()
            }
        })
    }
}