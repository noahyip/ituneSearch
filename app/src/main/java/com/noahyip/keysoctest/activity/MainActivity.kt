package com.noahyip.keysoctest.activity

import android.R.attr.value
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noahyip.keysoctest.R
import com.noahyip.keysoctest.adapter.ITuneSearchResultAdapter
import com.noahyip.keysoctest.databinding.ActivityMainBinding
import com.noahyip.keysoctest.utils.LocaleUtils
import com.noahyip.keysoctest.viewModel.MainActivityViewModel
import com.noahyip.searchgallery.adapter.ITuneFilterAdapter
import java.util.Locale


class MainActivity : AppCompatActivity() {

    companion object {
        val mediaTypeList = arrayListOf(
            "all",
            "movie",
            "podcast",
            "music",
            "musicVideo",
            "audiobook",
            "shortFilm",
            "tvShow",
            "software",
            "ebook"
        )
        val countryList = arrayListOf("US", "HK", "CN")

    }

    private lateinit var langList: Array<String>
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
        langList = arrayOf(
            getString(com.noahyip.keysoctest.R.string.text_traditional_chinese),
            getString(R.string.text_simplified_chinese),
            getString(R.string.english)
        )

        initCountryFilterRecyclerView()
        initMediaTypeFilterRecyclerView()
        initSearchResultRecyclerView()
    }

    private fun initCountryFilterRecyclerView() {
        binding.rvFilterCountry.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ITuneFilterAdapter(this, countryList)
        adapter.setOnClickListener(object : ITuneFilterAdapter.OnClickListener {

            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(position: Int) {
                viewModel.country = countryList[position]
                //Change Color of select filter
                adapter.selectItem = position
                adapter.notifyDataSetChanged()
                //Search with updated filter
                viewModel.search(binding.etSearch.text.toString())
            }
        })
        binding.rvFilterCountry.adapter = adapter

    }

    private fun initMediaTypeFilterRecyclerView() {
        binding.rvFilterMedia.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = ITuneFilterAdapter(this, mediaTypeList)
        adapter.setOnClickListener(object : ITuneFilterAdapter.OnClickListener {

            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(position: Int) {
                viewModel.mediaType = mediaTypeList[position]
                //Change Color of select filter
                adapter.selectItem = position
                adapter.notifyDataSetChanged()
                //Search with updated filter
                viewModel.search(binding.etSearch.text.toString())
            }
        })
        binding.rvFilterMedia.adapter = adapter
    }

    private fun initSearchResultRecyclerView() {
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

    fun click_search(v: View) {
        if (binding.etSearch.text.toString().isBlank()) {
            Toast.makeText(this, getString(R.string.error_empty_edit_text), Toast.LENGTH_LONG)
                .show()
        } else {
            viewModel.search(binding.etSearch.text.toString())
            binding.rvResult.smoothScrollToPosition(0)
        }
    }

    fun click_filter(v: View) {
        binding.llFilter.visibility = if (binding.llFilter.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun click_menu(v: View) {
        binding.gpMenuOptions.visibility = if (binding.gpMenuOptions.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun click_setting(v: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.text_choose_language))
        builder.setItems(langList) { _, which ->
            when (langList[which]) {
                getString(R.string.text_traditional_chinese) -> LocaleUtils.updateLocale(
                    this,
                    Locale.TRADITIONAL_CHINESE
                )
                getString(R.string.text_simplified_chinese) -> LocaleUtils.updateLocale(
                    this,
                    Locale.SIMPLIFIED_CHINESE
                )
                else -> LocaleUtils.updateLocale(this, Locale.ENGLISH)
            }
            recreate()
        }
        builder.show()
    }

    fun click_fav(v: View) {
        val intent = Intent(this, FavouriteSongActivity::class.java)
        startActivity(intent)
    }
}