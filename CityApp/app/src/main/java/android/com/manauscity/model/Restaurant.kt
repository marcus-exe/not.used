package android.com.manauscity.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Restaurant(
    val id: Int,
    @StringRes val name: Int,
    @StringRes val adress: Int,
    @StringRes val description: Int,
    val typeOfFood: TypeOfFood,
    @DrawableRes val logoImageRes: Int,
    @DrawableRes val foodImageRes: Int
)
