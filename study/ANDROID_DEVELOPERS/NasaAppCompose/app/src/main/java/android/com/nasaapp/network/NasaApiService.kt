package android.com.nasaapp.network

import android.com.nasaapp.model.NasaInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {
    @GET("planetary/apod")
    suspend fun getInfo(
        @Query("api_key")
        apiKey: String = "VwrcLN9fTKPtvhzmosmM1LtrykObrzwdHhfwhcsd",
        @Query("count")
        count: Int = 20
    ): List<NasaInfo>

}