package android.com.amphiniansapp

import android.app.Application
import android.com.amphiniansapp.data.AppContainer
import android.com.amphiniansapp.data.DefaultAppContainer

class AmphibiansInfoApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}