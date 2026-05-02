package android.com.amphiniansapp.data

import android.com.amphiniansapp.network.AmphibiansApiService


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
    import retrofit2.Retrofit

interface AppContainer{
    val amphibiansInfoRepository: AmphibiansInfoRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://android-kotlin-fun-mars-server.appspot.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: AmphibiansApiService by lazy {
        retrofit.create(AmphibiansApiService::class.java)
    }

    override val amphibiansInfoRepository: AmphibiansInfoRepository by lazy {
        NetworkAmphibiansInfoRepository(retrofitService)
    }

}

