package com.itmo.r3135.ViewClient.controller

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.ProductWithStatus
import com.itmo.r3135.ViewClient.view.WorkView.*
import com.itmo.r3135.World.Product
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.SetChangeListener
import javafx.scene.Group
import javafx.scene.shape.Line
import javafx.scene.text.Text
import javafx.util.Duration
import tornadofx.*
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
    val lineGroup = Group()
    val textGroup = Group()

    private var stepXLine: Double = 0.0
    private var stepYLine: Double = 0.0

    lateinit var updater: Thread

    init {
        products.addListener(SetChangeListener<Products> { c ->
            if (c.elementAdded != null) {
                addToMap(c.elementAdded.toProduct())
            }
            if (c.elementRemoved != null) {
                removeFromMap(c.elementRemoved.toProduct())
            }
            updatetable(productsSearch.search)

        })
        coolMap.p.children.add(lineGroup)
        coolMap.p.children.add(textGroup)
        repaintNewWindowsSize()
    }

    fun initial() {
        println("lal")
        connectController.send(Command(CommandList.SHOW))
        startGetUpdates()
    }

    fun repaintNewWindowsSize() {
        stepXLine = (coolMap.p.width - 2 * border) / 10
        stepYLine = (coolMap.p.height - 2 * border) / 10
        lineGroup.children.clear()
        var i = border
        while (i <= coolMap.p.width - border) {
            lineGroup.children.add(Line(i, border, i, coolMap.p.height - border))
            i += stepXLine
        }
        lineGroup.children.add(Line(coolMap.p.width - border, border,
                coolMap.p.width - border, coolMap.p.height - border))
        i = border
        while (i <= coolMap.p.height - border) {
            lineGroup.children.add(Line(border, i, coolMap.p.width - border, i))
            i += stepYLine
        }
        lineGroup.children.add(Line(border, coolMap.p.height - border,
                coolMap.p.width - border, coolMap.p.height - border))
        updateAllPoints()
        repaintNewCoordinateText()
    }

    private fun repaintNewCoordinateText() {
        textGroup.children.clear()
        val deltaX = currentMaxCoordinatesX - currentMinCoordinatesX
        var kX = (coolMap.p.width - 2 * border) / deltaX
        var i = currentMinCoordinatesX
        while (i <= currentMaxCoordinatesX + 5.0) {
            val text = Text()
            text.text = String.format("%.2f", i)
            text.x = (i - currentMinCoordinatesX) * kX + border
            text.y = coolMap.p.height - border / 3
            textGroup.children.add(text)
            i += deltaX / 10
        }
        val deltaY = currentMaxCoordinatesY - currentMinCoordinatesY
        var kY = (coolMap.p.height - 2 * border) / deltaY
        i = currentMinCoordinatesY
        while (i <= currentMaxCoordinatesY + 5.0) {
            val text = Text()
            text.text = String.format("%.2f", i)
            text.x = 2 / 3 * border
            text.y = (currentMaxCoordinatesY - i) * kY + border
            textGroup.children.add(text)
            i += deltaY / 10
        }
    }

    /**
     * Добавляет набор обхектов из ArrayList
     */
    fun show(showList: ArrayList<Product>) {
        for (p in showList)
            addProduct(p)
        updateAllPoints()
        repaintNewCoordinateText()
    }

    /**
     *
     */
    private fun startGetUpdates() {
        updater = Thread(Runnable {
            try {
                while (!Thread.currentThread().isInterrupted && connectController.isLogin) {
                    Platform.runLater {
                        connectController.send(Command(CommandList.GET_UPDATES))
                    }
                    Thread.sleep(300)
                    if (Thread.currentThread().isInterrupted || !connectController.isLogin) break
                }
            } catch (e: InterruptedException) {
                return@Runnable
            }
        })

        updater.isDaemon = true
        updater.start()
    }

    fun stopGetUpdates() {
        updater.interrupt()
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
        repaintNewCoordinateText()
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
        val x = border + (coolMap.p.width - 2 * border) * (pp.xReal - currentMinCoordinatesX) /
                (currentMaxCoordinatesX - currentMinCoordinatesX)
        val y = coolMap.p.height - (border + (coolMap.p.height - 2 * border) * (pp.yReal - currentMinCoordinatesY) /
                (currentMaxCoordinatesY - currentMinCoordinatesY))
        pp.updateXY(x, y)
    }

    /**
     * Устанавливает позцию объекта на поле(без визуальнгого перемещения)
     */
    private fun setPoint(pp: ProductPoint) {
        uppingCoordinates(pp)
        val x = border + (coolMap.p.width - 2 * border) * (pp.xReal - currentMinCoordinatesX) /
                (currentMaxCoordinatesX - currentMinCoordinatesX)
        val y = coolMap.p.height - (border + (coolMap.p.height - 2 * border) * (pp.yReal - currentMinCoordinatesY) /
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
        var productid = product.id
        var productf = figures[productid]
        figures.remove(productid)
        sequentialTransition {
            timeline {
                keyframe(Duration.seconds(0.625)) {
                    productf?.removeAnimation()
                    setOnFinished {
                        if (connectController.isLogin) {
                            productf?.group?.removeFromParent()
                            updateCoordinates()
                        }
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
        figures[product.id]?.let { setPoint(it) }
    }

    fun updatetable(searchString: String) {
        productssearh.setAll(products.stream().filter { t: Products? -> t?.name?.toLowerCase()?.contains(searchString.toLowerCase())!! }.toList())
    }
}