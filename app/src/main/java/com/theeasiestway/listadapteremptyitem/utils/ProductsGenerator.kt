package com.theeasiestway.listadapteremptyitem.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import com.theeasiestway.listadapteremptyitem.models.Product
import kotlin.random.Random

/**
 * Created by Loboda Alexey on 20.02.2021
 */
object ProductsGenerator {

    private val productNames = arrayListOf(
        "Apple", "Apricot", "Banana", "Blueberry", "Cherry", "Lemon", "Grape","Mango",
        "Lychee", "Melon", "Orange", "Papaya", "Pomelo", "Strawberry", "Nectarine"
    )

    private val productsIds = arrayListOf<Int>()
    private val randomId = 1..100
    private val randomColor = 0..255

    val productsColors = hashMapOf<String, Int>()
    val products = arrayListOf<Product>()

    init {
        generateProducts()
        products.addAll(products)
    }

    private fun generateProducts() {
        productsIds.clear()
        productsColors.clear()
        products.clear()

        for (i in 0 until productNames.size) {
            var idAdded = false
            while (!idAdded) {
                val id = randomId.shuffled().first()
                if (!productsIds.contains(id)) {
                    productsIds.add(id)
                    idAdded = true
                }
            }

            var colorAdded = false
            while (!colorAdded) {
                val color = Color.rgb(
                    randomColor.shuffled().first(),
                    randomColor.shuffled().first(),
                    randomColor.shuffled().first())

                val luminance = ColorUtils.calculateLuminance(color)
                val contrast = ColorUtils.calculateContrast(color, Color.WHITE)
                if (contrast < 0.9 || luminance !in 0.45..0.7) continue

                if (productsColors[productNames[i]] != color) {
                    productsColors[productNames[i]] = color
                    colorAdded = true
                }
            }
            products.add(Product(productsIds[i], productNames[i]))
        }
    }
}