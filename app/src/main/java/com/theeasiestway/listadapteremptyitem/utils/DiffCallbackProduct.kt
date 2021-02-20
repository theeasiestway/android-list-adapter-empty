package com.theeasiestway.listadapteremptyitem.utils

import androidx.recyclerview.widget.DiffUtil
import com.theeasiestway.listadapteremptyitem.models.Product

/**
 * Created by Loboda Alexey on 20.02.2021
 */
object DiffCallbackProduct: DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.name == newItem.name
    }
}