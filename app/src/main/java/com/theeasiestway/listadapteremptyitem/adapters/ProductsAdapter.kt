package com.theeasiestway.listadapteremptyitem.adapters

import android.view.ViewGroup
import com.theeasiestway.listadapteremptyitem.R
import com.theeasiestway.listadapteremptyitem.databinding.ItemEmptyPlaceholderBinding
import com.theeasiestway.listadapteremptyitem.databinding.ItemProductBinding
import com.theeasiestway.listadapteremptyitem.models.Product
import com.theeasiestway.listadapteremptyitem.utils.DiffCallbackProduct
import com.theeasiestway.listadapteremptyitem.utils.ProductsGenerator

/**
 * Created by Loboda Alexey on 20.02.2021
 */
class ProductsAdapter(
    private val onAddItemClicked: () -> Unit
): ListAdapterEmptyItemSwipeable<Product, ProductsAdapter.ProductViewHolder>(DiffCallbackProduct) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return if (viewType == typeData) ProductViewHolder(R.layout.item_product, parent)
        else ProductViewHolder(R.layout.item_empty_placeholder, parent)
    }

    inner class ProductViewHolder(layout: Int, parent: ViewGroup): ViewHolder(layout, parent) {

        override fun bindData(data: Product) {
            with(ItemProductBinding.bind(itemView)) {
                title.text = data.name
                root.setBackgroundColor(ProductsGenerator.productsColors[data.name] as Int)
            }
        }

        override fun bindEmpty() {
            with(ItemEmptyPlaceholderBinding.bind(itemView)) {
                title.text = "List is empty"
                with(addItem) {
                    text = "Add Item"
                    setOnClickListener { onAddItemClicked.invoke() }
                }
            }
        }
    }
}