package br.com.alura.orgs.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.orgs.R
import br.com.alura.orgs.dao.ProductsDao
import br.com.alura.orgs.databinding.ActivityProductsListBinding
import br.com.alura.orgs.ui.recyclerview.adapter.ProductListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductListActivity : AppCompatActivity() {

    val dao = ProductsDao()
    private val adapter = ProductListAdapter(context = this, products = dao.searchAll())
    val binding by lazy {
        ActivityProductsListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRecyclerView()
        setContentView(binding.root)//binding view + activity
        configureFab()

    }

    override fun onResume() {
        super.onResume()
        adapter.refresh(dao.searchAll())
    }

    private fun configureFab() {
        //val fab = findViewById<FloatingActionButton>(R.id.activity_product_list_fab)
        val fab = binding.activityProductListFab
        fab.setOnClickListener { goToProductForm() }
    }

    private fun goToProductForm() {
        val intent = Intent(this, FormProductActivity::class.java)
        startActivity(intent)
    }

    private fun configureRecyclerView() {
        //val recyclerView = findViewById<RecyclerView>(R.id.activity_product_list_recyclerView)
        val recyclerView = binding.activityProductListRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}