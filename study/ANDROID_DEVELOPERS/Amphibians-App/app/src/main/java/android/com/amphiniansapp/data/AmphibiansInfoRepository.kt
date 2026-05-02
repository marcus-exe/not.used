package android.com.amphiniansapp.data

import android.com.amphiniansapp.model.AmphibianInfo
import android.com.amphiniansapp.network.AmphibiansApiService

interface AmphibiansInfoRepository {
    suspend fun getAmphibiansInfo(): List<AmphibianInfo>
}

class NetworkAmphibiansInfoRepository(
    private val amphibiansApiService: AmphibiansApiService
) : AmphibiansInfoRepository {
    override suspend fun getAmphibiansInfo(): List<AmphibianInfo> = amphibiansApiService.getInfo()
}