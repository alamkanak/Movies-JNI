package com.raquib.movies

import android.app.Application
import com.raquib.movies.ui.MovieViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MovieApplication : Application() {

    val appModule = module {
        single { JniHelper() }
        single { MovieRepository(get()) }
        viewModel { MovieViewModel(get<Application>()) }
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