package com.theeasiestway.listadapteremptyitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.theeasiestway.listadapteremptyitem.adapters.ProductsAdapter
import com.theeasiestway.listadapteremptyitem.databinding.ActivityMainBinding
import com.theeasiestway.listadapteremptyitem.models.Product
import com.theeasiestway.listadapteremptyitem.utils.ItemSwipeHandler
import com.theeasiestway.listadapteremptyitem.utils.ProductsGenerator

/**
 * Created by Alexey Loboda on 20.02.2021
 */
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val productsAdapter = ProductsAdapter { addItem() }
    private val products = arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = productsAdapter
            }
            with(addItem) {
                setOnClickListener { addItem() }
                hide()
            }
            ItemTouchHelper(ItemSwipeHandler(productsAdapter) { removeItem(it) }).attachToRecyclerView(recyclerView)
        }
        productsAdapter.submitList(null)
    }

    private fun addItem() {
        for (i in 0 until ProductsGenerator.products.size) {
            val product = ProductsGenerator.products[i]
            if (!products.contains(product)) {
                products.add(product)
                break
            }
        }
        if (products.isNotEmpty()) binding.addItem.show()
        productsAdapter.submitList(products.toList())
    }

    private fun removeItem(product: Product) {
        products.remove(product)
        Log.d("qwdwqdwq", "removed: ${product.name}; list is empty: ${products.isEmpty()}")
        if (products.isEmpty()) binding.addItem.hide()
    }
}