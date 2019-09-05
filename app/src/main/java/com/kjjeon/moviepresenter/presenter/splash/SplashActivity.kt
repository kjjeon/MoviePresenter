package com.kjjeon.moviepresenter.presenter.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.kjjeon.moviepresenter.R
import com.kjjeon.moviepresenter.extension.launchActivity
import com.kjjeon.moviepresenter.presenter.base.BaseActivity
import com.kjjeon.moviepresenter.presenter.home.MainActivity
import io.reactivex.Completable
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timber.plant(Timber.DebugTree())

        Glide.with(this)
            .asBitmap()
            .load(R.drawable.ic_movie_black_24dp)
            .fitCenter()
            .into(logo)

        Completable.complete()
            .delay(1,TimeUnit.SECONDS)
            .subscribe {
                applicationContext.launchActivity<MainActivity> {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                finish()
            }.drop()
    }
}