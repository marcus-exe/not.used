package android.com.nasaapp.data


import android.com.nasaapp.network.NasaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val nasaRepository : NasaRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://api.nasa.gov"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }

    override val nasaRepository: NasaRepository by lazy {
        NetworkNasaRepository(retrofitService)
    }


}