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
    val productsColors = hashMapOf<String, Int>()
    val products = arrayListOf<Product>()

    init {
        generateProducts()
    }

    private fun generateProducts() {
        productsIds.clear()
        productsColors.clear()
        products.clear()

        val randomId = Random(System.currentTimeMillis())
        val randomColor = Random(0)
        for (i in 0 until productNames.size) {
            var idAdded = false
            while (!idAdded) {
                val id = randomId.nextInt()
                if (!productsIds.contains(id)) {
                    productsIds.add(id)
                    idAdded = true
                }
            }

            var colorAdded = false
            while (!colorAdded) {
                val color = Color.argb(randomColor.nextInt(150,256),
                    randomColor.nextInt(256),
                    randomColor.nextInt(256),
                    randomColor.nextInt(256))

                val luminance = ColorUtils.calculateLuminance(color)
                if (luminance !in 0.5..0.7) continue

                if (productsColors[productNames[i]] != color) {
                    productsColors[productNames[i]] = color
                    colorAdded = true
                }
            }
            products.add(Product(productsIds[i], productNames[i]))
        }
    }
}