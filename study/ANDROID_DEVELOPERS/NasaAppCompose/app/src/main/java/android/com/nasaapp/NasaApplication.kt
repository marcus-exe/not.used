package android.com.nasaapp

import android.app.Application
import android.com.nasaapp.data.AppContainer
import android.com.nasaapp.data.DefaultAppContainer


class NasaApplication : Application() {
    lateinit var container : AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}