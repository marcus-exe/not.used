package br.com.alura.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ProductItemBinding
import br.com.alura.orgs.extensions.tryUploadImage
import br.com.alura.orgs.models.Product
import coil.load
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ProductListAdapter(
    private val context: Context,
    products: List<Product>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val dataset = products.toMutableList()

    class ViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val name = binding.productItemName
            name.text = product.name

            val description = binding.productItemDescription
            description.text = product.description

            val value = binding.productItemValue
            val currencyValue: String = formatCurrency(product.value)
            value.text = currencyValue


            val visibility = if (product.image != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.imageView.visibility = visibility

            binding.imageView.tryUploadImage(product.image)
        }

        private fun formatCurrency(value: BigDecimal): String {
            val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
            return formatter.format(value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val inflater = LayoutInflater.from(context)
        //val view = inflater.inflate(R.layout.product_item, parent, false)
        val binding = ProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = dataset[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = dataset.size

    fun refresh(products: List<Product>) {
        this.dataset.clear()
        this.dataset.addAll(products)
        notifyDataSetChanged()
    }

}
