package com.theeasiestway.listadapteremptyitem.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Loboda Alexey on 20.02.2021
 */
abstract class ListAdapterEmptyItemSwipable<T, VH: ListAdapterEmptyItemSwipable<T, VH>.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
): ListAdapter<T, VH>(diffCallback) {

    companion object {
        val typeEmpty = 0
        val typeData = 1
    }

    private val emptyList = listOf<T?>(null)
    private var removedItems = arrayListOf<T>()

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        return if (currentList.size == 1 && currentList[position] == null) typeEmpty else typeData
    }

    @CallSuper
    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder) {
            if (itemViewType == typeEmpty) bindEmpty()
            else bindData(currentList[position])
        }
    }

    @CallSuper
    override fun submitList(list: List<T>?) {
        submit(list, false)
    }

    private fun submit(list: List<T>?, isLocalSubmit: Boolean) {
        if (!isLocalSubmit) removedItems.clear()
        if (list == null || list.isEmpty()) super.submitList(emptyList)
        else super.submitList(list)
    }

    fun removeItem(position: Int): T? {
        if (position >= itemCount || getItemViewType(position) == typeEmpty) return null
        val item = currentList[position]
        removedItems.add(item)
        val list = currentList - removedItems
        if (list.isEmpty()) removedItems.clear()
        submit(list, true)
        return item
    }

    fun removeItem(item: T): T? {
        val position = currentList.indexOf(item)
        if (position == -1) return null
        return removeItem(position)
    }

    /** for ItemTouchHelper.SimpleCallbackgetSwipeDirs.getSwipeDirs() */
    open fun isItemSwipable(viewHolder: RecyclerView.ViewHolder): Boolean {
        return viewHolder.itemViewType != typeEmpty
    }

    abstract inner class ViewHolder(
        view: View,
        private val onEmptyClick: (() -> Unit)? = null
    ): RecyclerView.ViewHolder(view) {

        constructor(
            @LayoutRes layout: Int,
            parent: ViewGroup,
            onEmptyClick: (() -> Unit)? = null
        ): this(LayoutInflater.from(parent.context).inflate(layout, parent, false), onEmptyClick)

        open fun bindData(data: T) = Unit

        open fun bindEmpty() {
            itemView.setOnClickListener { onEmptyClick?.invoke() }
        }
    }
}