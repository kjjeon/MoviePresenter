package com.kjjeon.moviepresenter.presenter.home

import android.os.Bundle
import android.util.Log
import com.kjjeon.moviepresenter.R
import com.kjjeon.moviepresenter.databinding.ActivityMainBinding
import com.kjjeon.moviepresenter.presenter.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val dataBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater, null, true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBinding.viewModel = mainViewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.stop()
    }
}
