package android.com.amphiniansapp.network

import android.com.amphiniansapp.model.AmphibianInfo
import retrofit2.http.GET


interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getInfo(): List<AmphibianInfo>
}