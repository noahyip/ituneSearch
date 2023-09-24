package com.noahyip.keysoctest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.noahyip.keysoctest.adapter.ITuneFavouriteResultAdapter
import com.noahyip.keysoctest.databinding.ActivityFavouriteSongBinding
import com.noahyip.keysoctest.utils.SharePreferenceUtils
import com.noahyip.keysoctest.viewModel.FavouriteSongActivityViewModel

class FavouriteSongActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteSongBinding
    private lateinit var viewModel: FavouriteSongActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteSongBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FavouriteSongActivityViewModel::class.java]
        binding.viewModel = viewModel

        initView()
    }

    fun initView() {
        binding.rvFavResult.layoutManager = LinearLayoutManager(this)
        val adapter = ITuneFavouriteResultAdapter(this, SharePreferenceUtils.getAllFavourite(this))
        binding.rvFavResult.adapter = adapter

    }

    fun click_back(v: View) {
        finish()
    }
}