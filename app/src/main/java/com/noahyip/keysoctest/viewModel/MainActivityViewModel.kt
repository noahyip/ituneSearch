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
    var keyword: String = ""
    var offset: Int = 0

    fun search(keyword: String) {
        this.keyword = keyword.trim().replace(" ", "+")
        offset = 0
        RetrofitClient().getImgurService().search(keyword, offset, 20)
            .enqueue(object : Callback<ITuneSearchResponse?> {
                override fun onResponse(
                    call: Call<ITuneSearchResponse?>,
                    response: Response<ITuneSearchResponse?>
                ) {
                    response.body()?.let {
                        if (it.resultCount > 0) {
                            iTuneSearchResponse.value = response.body()
                        } else {
                            iTuneSearchResponse.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<ITuneSearchResponse?>, t: Throwable) {
                    call.cancel()
                }
            })
    }

    fun loadMore() {
        offset += 20
        RetrofitClient().getImgurService().search(keyword, offset, 20)
            .enqueue(object : Callback<ITuneSearchResponse?> {
                override fun onResponse(
                    call: Call<ITuneSearchResponse?>,
                    response: Response<ITuneSearchResponse?>
                ) {
                    response.body()?.let { more ->
                        if (more.resultCount > 0) {
                            iTuneSearchResponse.value?.resultCount?.plus(more.resultCount)?:0
                            iTuneSearchResponse.value?.results?.addAll(more.results)
                            //Notify filed value change
                            iTuneSearchResponse.value = iTuneSearchResponse.value
                        }
                    }
                }

                override fun onFailure(call: Call<ITuneSearchResponse?>, t: Throwable) {
                    call.cancel()
                }
            })
    }
}