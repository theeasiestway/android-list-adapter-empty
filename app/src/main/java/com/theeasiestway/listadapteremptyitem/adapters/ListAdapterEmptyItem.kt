package com.theeasiestway.listadapteremptyitem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Loboda Alexey on 20.02.2021
 */
abstract class ListAdapterEmptyItem<T>(
    diffCallback: DiffUtil.ItemCallback<T>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val typeEmpty = 0
        val typeData = 1
    }

    private val emptyList = listOf<T?>(null)
    private var removedItems = arrayListOf<T>()
    private val differ = AsyncListDiffer(this, diffCallback).apply {
        addListListener { _, newList ->
            var isActualList = true
            removedItems.forEach {
                if (newList.contains(it)) {
                    isActualList = false
                    return@forEach
                }
            }
            if (isActualList) removedItems.clear()
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList.size == 1 && differ.currentList[position] == null) typeEmpty else typeData
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListAdapterEmptyItem<*>.ViewHolder) {
            with(holder as ListAdapterEmptyItem<T>.ViewHolder) {
                if (itemViewType == typeEmpty) bindEmpty()
                else bindData(differ.currentList[position] as T)
            }
        }
    }

    fun submitList(list: List<T>?) {
        if (list == null || list.isEmpty()) differ.submitList(emptyList)
        else differ.submitList(list)
    }

    fun removeItem(position: Int): T? {
        if (position >= itemCount || getItemViewType(position) == typeEmpty) return null
        val item = differ.currentList[position]
        removedItems.add(item)
        val list = differ.currentList - removedItems
        submitList(list)
        return item
    }

    fun removeItem(item: T): T? {
        val position = differ.currentList.indexOf(item)
        if (position == -1) return null
        return removeItem(position)
    }

    /** for ItemTouchHelper.SimpleCallbackgetSwipeDirs.getSwipeDirs() */
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