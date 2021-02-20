package com.theeasiestway.listadapteremptyitem.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Loboda Alexey on 20.02.2021
 */
abstract class ListAdapterEmptyItem<T>(
    diffCallback: DiffUtil.ItemCallback<T>
): ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        val typeEmpty = 0
        val typeData = 1
    }

    private val emptyList = listOf<T?>(null)

    override fun getItemViewType(position: Int): Int {
        return if (currentList.size == 1 && currentList[position] == null) typeEmpty else typeData
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListAdapterEmptyItem<*>.ViewHolder) {
            with(holder as ListAdapterEmptyItem<T>.ViewHolder) {
                if (itemViewType == typeEmpty) bindEmpty()
                else bindData(currentList[position] as T)
            }
        }
    }

    override fun submitList(list: List<T>?) {
        if (list == null || list.isEmpty()) super.submitList(emptyList)
        else super.submitList(list)
    }

    fun removeItem(position: Int): T? {
        if (position >= itemCount || getItemViewType(position) == typeEmpty) return null
        val item = currentList[position]
        val list = currentList.toMutableList().apply { remove(item) }
        Log.d("qwdwqdwq", "in adapter removed: ${item}; list: $list")
        submitList(list)
        return item
    }

    open fun isItemSwipable(viewHolder: RecyclerView.ViewHolder): Boolean {
        return viewHolder.itemViewType != typeEmpty
    }

    abstract inner class ViewHolder(
        view: View,
        private val onClick: (() -> Unit)? = null
    ): RecyclerView.ViewHolder(view) {

        constructor(
            @LayoutRes layout: Int,
            parent: ViewGroup,
            onClick: (() -> Unit)? = null
        ): this(LayoutInflater.from(parent.context).inflate(layout, parent, false), onClick)

        open fun bindData(data: T) = Unit

        open fun bindEmpty() {
            itemView.setOnClickListener { onClick?.invoke() }
        }
    }
}