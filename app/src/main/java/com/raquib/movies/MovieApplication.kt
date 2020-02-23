package com.raquib.movies

import android.app.Application
import com.raquib.movies.adapter.MovieAdapter
import com.raquib.movies.repo.MovieRepository
import com.raquib.movies.ui.moviedetail.DetailViewModel
import com.raquib.movies.ui.movielist.MovieViewModel
import com.raquib.movies.utils.JniHelper
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
        viewModel { MovieViewModel(get(), get()) }
        viewModel {
            DetailViewModel(
                get(),
                get()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "MOVIESLOG_" + tag!!, message, t)
                }
            })
        }

        startKoin {
            androidLogger()
            androidContext(this@MovieApplication)
            modules(appModule)
        }
    }
}