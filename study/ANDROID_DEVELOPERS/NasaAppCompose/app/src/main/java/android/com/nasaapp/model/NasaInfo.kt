package android.com.nasaapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NasaInfo(
    val copyright : String? = null,
    val date : String,
    val explanation: String,
    @SerialName(value = "hdurl")
    val hdUrl : String,
    @SerialName(value = "media_type")
    val mediaType: String,
    @SerialName(value = "service_version")
    val serviceVersion : String,
    val title: String,
    val url: String,
)
