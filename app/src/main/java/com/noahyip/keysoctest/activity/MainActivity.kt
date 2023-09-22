package com.noahyip.keysoctest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.noahyip.keysoctest.R
import com.noahyip.keysoctest.databinding.ActivityMainBinding
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
    }
}