package android.com.manauscity.data

import android.com.manauscity.R
import android.com.manauscity.model.Restaurant
import android.com.manauscity.model.TypeOfFood


object CityDataProvider {
    val defaultRestaurant = getRestaurantData()[0]

    fun getRestaurantData(): List<Restaurant> {
        val amazonic = TypeOfFood.valueOf(TypeOfFood.AMAZONIC.name)
        val barbecue = TypeOfFood.valueOf(TypeOfFood.BARBECUE.name)
        val burguer = TypeOfFood.valueOf(TypeOfFood.BURGUER.name)
        val italian = TypeOfFood.valueOf(TypeOfFood.ITALIAN.name)
        val sushi = TypeOfFood.valueOf(TypeOfFood.SUSHI.name)
        val others = TypeOfFood.valueOf(TypeOfFood.OTHERS.name)

        return listOf(
            Restaurant(
                id = 1,
                name = R.string.amazonico_restaurant_name,
                adress = R.string.amazonico_restaurant_adress,
                description = R.string.amazonico_restaurant_description,
                typeOfFood = amazonic,
                logoImageRes = R.drawable.amazonico_logo,
                foodImageRes = R.drawable.amazonico_prato
            ),
            Restaurant(
                id = 2,
                name = R.string.barollo_restaurant_name,
                adress = R.string.barollo_restaurant_adress,
                description = R.string.barollo_restaurant_description,
                typeOfFood = italian,
                logoImageRes = R.drawable.barollo_logo,
                foodImageRes = R.drawable.barollo_prato
            ),
            Restaurant(
                id = 3,
                name = R.string.burguers_and_burguers_name,
                adress = R.string.burguers_and_burguers_andress,
                description = R.string.burguers_and_burguers_description,
                typeOfFood = burguer,
                logoImageRes = R.drawable.burguers_burguers_logo,
                foodImageRes = R.drawable.burguers_burguers_prato
            ),
            Restaurant(
                id = 4,
                name = R.string.cafe_com_leite_name,
                adress = R.string.cafe_com_leite_adress,
                description = R.string.generic_description,
                typeOfFood = amazonic,
                logoImageRes = R.drawable.cafecomleite_logo,
                foodImageRes = R.drawable.cafecomleite_prato
            ),
            Restaurant(
                id = 5,
                name = R.string.do_futro_cafe_name,
                adress = R.string.do_futuro_cafe_adress,
                description = R.string.generic_description,
                typeOfFood = others,
                logoImageRes = R.drawable.dofuturocafe_logo,
                foodImageRes = R.drawable.dofuturocafe_prato
            ),
            Restaurant(
                id = 6,
                name = R.string.domes_burguers_name,
                adress = R.string.domes_burguers_adress,
                description = R.string.generic_description,
                typeOfFood = burguer,
                logoImageRes = R.drawable.domes_logo,
                foodImageRes = R.drawable.domes_prato
            ),
            Restaurant(
                id = 7,
                name = R.string.fiorentina_name,
                adress = R.string.fiorentina_adress,
                description = R.string.fiorentina_description,
                typeOfFood = italian,
                logoImageRes = R.drawable.fiorentina_logo,
                foodImageRes = R.drawable.fiorentina_prato
            ),
            Restaurant(
                id = 8,
                name = R.string.gaucho_churrascaria_name,
                adress = R.string.gaucho_churrascaria_adress,
                description = R.string.generic_description,
                typeOfFood = barbecue,
                logoImageRes = R.drawable.gauchochurrascaria_logo,
                foodImageRes = R.drawable.gauchochurrascaria_prato
            ),
            Restaurant(
                id = 9,
                name = R.string.jsk_burguers_name,
                adress = R.string.jsk_burguers_adress,
                description = R.string.generic_description,
                typeOfFood = burguer,
                logoImageRes = R.drawable.jsk_logo,
                foodImageRes = R.drawable.jsk_prato
            ),
            Restaurant(
                id = 10,
                name = R.string.matsuri_name,
                adress = R.string.matsuri_adress,
                description = R.string.generic_description,
                typeOfFood = sushi,
                logoImageRes = R.drawable.matsuri_logo,
                foodImageRes = R.drawable.matsuri_prato
            ),
            Restaurant(
                id = 11,
                name = R.string.morada_cafe_name,
                adress = R.string.morada_cafe_adress,
                description = R.string.generic_description,
                typeOfFood = amazonic,
                logoImageRes = R.drawable.moradacafe_logo,
                foodImageRes = R.drawable.moradacafe_prato
            ),
            Restaurant(
                id = 12,
                name = R.string.morada_do_peixe_name,
                adress = R.string.morada_do_peixe_adress,
                description = R.string.generic_description,
                typeOfFood = amazonic,
                logoImageRes = R.drawable.moradadopeixe_logo,
                foodImageRes = R.drawable.moradadopeixe_prato
            ),
            Restaurant(
                id = 13,
                name = R.string.pao_e_alho_name,
                adress = R.string.pao_e_alho_adress,
                description = R.string.generic_description,
                typeOfFood = barbecue,
                logoImageRes = R.drawable.pao_alho_logo,
                foodImageRes = R.drawable.pao_alho_prato
            ),
            Restaurant(
                id = 14,
                name = R.string.rancho_bufalo_name,
                adress = R.string.rancho_bufalo_adress,
                description = R.string.generic_description,
                typeOfFood = barbecue,
                logoImageRes = R.drawable.ranchobufalo_logo,
                foodImageRes = R.drawable.ranchobufalo_prato
            ),
            Restaurant(
                id = 15,
                name = R.string.soho_lounge_name,
                adress = R.string.soho_lounge_adress,
                description = R.string.generic_description,
                typeOfFood = sushi,
                logoImageRes = R.drawable.soho_logo,
                foodImageRes = R.drawable.soho_prato
            ),
            Restaurant(
                id = 16,
                name = R.string.suysei_name,
                adress = R.string.suysei_adress,
                description = R.string.generic_description,
                typeOfFood = sushi,
                logoImageRes = R.drawable.suysei_logo,
                foodImageRes = R.drawable.suysei_prato
            ),
            Restaurant(
                id = 17,
                name = R.string.suzuran_name,
                adress = R.string.suzuran_adress,
                description = R.string.generic_description,
                typeOfFood = sushi,
                logoImageRes = R.drawable.suzuran_logo,
                foodImageRes = R.drawable.suzuran_prato
            )
        )
    }

}