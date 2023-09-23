package com.noahyip.keysoctest.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noahyip.keysoctest.R
import com.noahyip.keysoctest.adapter.ITuneSearchResultAdapter
import com.noahyip.keysoctest.databinding.ActivityMainBinding
import com.noahyip.keysoctest.model.ITuneSearchResponse
import com.noahyip.keysoctest.viewModel.MainActivityViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding.viewModel = viewModel

        initView()
    }

    private fun initView() {
        binding.rvResult.layoutManager = LinearLayoutManager(this)
        val adapter = ITuneSearchResultAdapter(this)
        binding.rvResult.adapter = adapter
        binding.viewModel?.iTuneSearchResponse?.observe(this, Observer {
            adapter.setData(it)
        })

        binding.rvResult.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                //Load more result when user reach to bottom of the recycle view
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == binding.viewModel?.iTuneSearchResponse?.value?.results?.size) {
                    viewModel.loadMore()
                }
            }
        })
    }

    fun search(v: View) {
        if (binding.etSearch.text.toString().isBlank()) {
            Toast.makeText(this, getString(R.string.error_empty_edit_text), Toast.LENGTH_LONG).show()
        } else {
            viewModel.search(binding.etSearch.text.toString())
        }
    }

    fun filter(v: View) {
        //TODO
    }
}