package br.com.alura.orgs.dao

import br.com.alura.orgs.models.Product
import java.math.BigDecimal

class ProductsDao {

    fun add(product: Product) {
        products.add(product)
    }

    fun searchAll(): List<Product> {
        return products.toList()
    }

    companion object {
        private val products = mutableListOf<Product>(
            Product(
                name = "Fruit Salad",
                description = "Orange, Apples, Grapes",
                value = BigDecimal("19.83"),
                image = "https://images.pexels.com/photos/2683373/pexels-photo-2683373.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
            )
        )
    }

}