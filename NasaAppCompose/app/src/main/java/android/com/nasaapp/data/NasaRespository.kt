package android.com.nasaapp.data

import android.com.nasaapp.model.NasaInfo
import android.com.nasaapp.network.NasaApiService

interface NasaRepository {
    suspend fun getNasaInfo(): List<NasaInfo>
}

class NetworkNasaRepository(
private val nasaApiService: NasaApiService
) : NasaRepository {
    override suspend fun getNasaInfo(): List<NasaInfo> = nasaApiService.getInfo()
}