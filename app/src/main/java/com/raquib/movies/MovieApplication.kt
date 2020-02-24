package com.raquib.movies

import android.app.Application
import com.raquib.movies.adapter.DetailAdapter
import com.raquib.movies.adapter.MovieAdapter
import com.raquib.movies.repo.MovieRepository
import com.raquib.movies.ui.detail.DetailViewModel
import com.raquib.movies.ui.movielist.MovieViewModel
import com.raquib.movies.utils.JniHelper
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MovieApplication : Application() {

    private val appModule = module {
        single { JniHelper() }
        single { MovieRepository(get()) }
        single { MovieAdapter() }
        single { Picasso.get()!! }
        single { DetailAdapter(get()) }
        viewModel { MovieViewModel(get(), get()) }
        viewModel { DetailViewModel(get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        // Setup logging with Timber.
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "MOVIESLOG_$tag", message, t)
                }
            })
        }

        // Setup dependency injection with Koin.
        startKoin {
            androidLogger()
            androidContext(this@MovieApplication)
            modules(appModule)
        }
    }
}