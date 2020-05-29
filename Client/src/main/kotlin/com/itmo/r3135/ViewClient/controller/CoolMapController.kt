package com.itmo.r3135.ViewClient.controller

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.ProductWithStatus
import com.itmo.r3135.ViewClient.view.WorkView.*
import com.itmo.r3135.World.Product
import javafx.collections.FXCollections
import javafx.collections.SetChangeListener
import javafx.util.Duration
import tornadofx.*
import com.itmo.r3135.ViewClient.view.WorkView.*
import kotlin.streams.toList

class CoolMapController : Controller() {
    private val coolMap: CoolMap by inject()
    private val productsSearch: ProductsSearch by inject()
    private val connectController: ConnectController by inject()
    val products = FXCollections.observableSet<Products>()
    val productssearh = FXCollections.observableArrayList<Products>()
    val figures = FXCollections.observableHashMap<Int, ProductPoint>()
    val selectedProduct = ProductsModel()
    var currentMinCoordinatesX = -100.0
    var currentMinCoordinatesY = -100.0
    var currentMaxCoordinatesX = 100.0
    var currentMaxCoordinatesY = 100.0
    val border = 40.0
    val indent = 10.0

    init {
        products.clear()
        connectController.sendReceiveManager.send(Command(CommandList.SHOW))
        products.addListener(SetChangeListener<Products> { c ->
            if (c.elementAdded != null) {
                addToMap(c.elementAdded.toProduct())
            }
            if (c.elementRemoved != null) {
                removeFromMap(c.elementRemoved.toProduct())
            }
//            while (c.next()) {
//                if (c.wasRemoved()) {
//                    //работка удаления
//                    println("REMOVED")
//                } else if (c.wasAdded()) {
//                    //обавление
//                    println("ADDED")
//                } else if (c.wasUpdated()) {
//                    //изменение
//                    println("UPDATED")
//                }
//            }

            updatetable(productsSearch.search)

        })
    }

    /**
     * Добавляет набор обхектов из ArrayList
     */
    fun show(showList: ArrayList<Product>) {
        for (p in showList)
            addProduct(p)
        startGetUpdates()
        updateAllPoints()
    }

    /**
     *
     */
    private fun startGetUpdates() {
        val updater = Thread(Runnable {
            while (true) {
                connectController.sendReceiveManager.send(Command(CommandList.GET_UPDATES))
                Thread.sleep(100)
            }
        })
        updater.isDaemon = true
        updater.start()
    }

    /**
     * Вносит изменеия в существующий набор объектов
     */
    fun updateList(updateList: ArrayList<ProductWithStatus>) {
        for (p in updateList) {
            if (p.status == ProductWithStatus.ObjectStatus.ADD)
                addProduct(p.product)
            if (p.status == ProductWithStatus.ObjectStatus.REMOVE)
                removeProduct(p.product)
            if (p.status == ProductWithStatus.ObjectStatus.UPDATE)
                updateProduct(p.product)
        }
        updateAllPoints()
    }

    /**
     * Расширяет границы поля по добавленному объекту
     */
    private fun uppingCoordinates(pp: ProductPoint) {
        if (currentMinCoordinatesX > pp.xReal - indent) currentMinCoordinatesX = pp.xReal - indent
        if (currentMinCoordinatesY > pp.yReal - indent) currentMinCoordinatesY = pp.yReal - indent
        if (currentMaxCoordinatesX < pp.xReal + indent) currentMaxCoordinatesX = pp.xReal + indent
        if (currentMaxCoordinatesY < pp.yReal + indent) currentMaxCoordinatesY = pp.yReal + indent
    }

    /**
     * Обновляет позиции всех объектов
     */
    private fun updateAllPoints() {
        for (pp: MutableMap.MutableEntry<Int, ProductPoint> in figures) {
            updatePoint(pp.value)
        }
    }

    /**
     * Обновляет границы координатного поля по всей коллекции
     */
    fun updateCoordinates() {
        var maxX = currentMinCoordinatesX
        var maxY = currentMinCoordinatesY
        var minX = currentMaxCoordinatesX
        var minY = currentMaxCoordinatesY
        for (pp: MutableMap.MutableEntry<Int, ProductPoint> in figures) {
            if (maxX < pp.value.xReal) maxX = pp.value.xReal
            if (maxY < pp.value.yReal) maxY = pp.value.yReal
            if (minX > pp.value.xReal) minX = pp.value.xReal
            if (minY > pp.value.yReal) minY = pp.value.yReal
        }
        currentMaxCoordinatesX = maxX + indent
        currentMaxCoordinatesY = maxY + indent
        currentMinCoordinatesX = minX - indent
        currentMinCoordinatesY = minY - indent
        updateAllPoints()
    }

    /**
     * Обновляет позцию одного обхекта
     */
    private fun updatePoint(pp: ProductPoint) {
        uppingCoordinates(pp)
        val x = border + (coolMap.p.minWidth - 2 * border) * (pp.xReal - currentMinCoordinatesX) /
                (currentMaxCoordinatesX - currentMinCoordinatesX)
        val y = coolMap.p.minHeight - (border + (coolMap.p.minHeight - 2 * border) * (pp.yReal - currentMinCoordinatesY) /
                (currentMaxCoordinatesY - currentMinCoordinatesY))
        pp.updateXY(x, y)
    }

    /**
     * Устанавливает позцию объекта на поле(без визуальнгого перемещения)
     */
    private fun setPoint(pp: ProductPoint) {
        uppingCoordinates(pp)
        val x = border + (coolMap.p.minWidth - 2 * border) * (pp.xReal - currentMinCoordinatesX) /
                (currentMaxCoordinatesX - currentMinCoordinatesX)
        val y = coolMap.p.minHeight - (border + (coolMap.p.minHeight - 2 * border) * (pp.yReal - currentMinCoordinatesY) /
                (currentMaxCoordinatesY - currentMinCoordinatesY))
        pp.setXY(x, y)
    }

    /**
     * Добавяет объект на поле
     */
    private fun addToMap(product: Product) {
        val added = ProductPoint(product)
        figures[product.id] = added
        coolMap.p.add(added.group)
        setPoint(added)
    }

    /**
     * Удаляет объект с поля
     */
    private fun removeFromMap(product: Product) {
        sequentialTransition {
            timeline {
                keyframe(Duration.seconds(1.5)) {
                    figures[product.id]?.removeAnimation()
                    setOnFinished {
                        figures[product.id]?.group?.removeFromParent()
                        figures.remove(product.id)
                        updateCoordinates()
                    }
                }
            }
        }
    }

    /**
     * Добавяет объект в коллекцию
     */
    private fun addProduct(product: Product) {
        products.add(Products(product))
//        addToMap(product)
    }

    /**
     * Добавяет объект из коллекции
     */
    private fun removeProduct(product: Product) {
//        removeFromMap(product)
        products.remove(Products(product))
    }

    /**
     * Обновляет объект из коллекции
     */
    private fun updateProduct(product: Product) {
        products.remove(Products(product))
        products.add(Products(product))
        figures[product.id]?.xReal = product.coordinates.x
        figures[product.id]?.yReal = product.coordinates.y
        figures[product.id]?.let { updatePoint(it) }
    }

    fun updatetable(searchString: String) {
        productssearh.setAll(products.stream().filter { t: Products? -> t?.name?.toLowerCase()?.contains(searchString.toLowerCase())!! }.toList())
    }
}