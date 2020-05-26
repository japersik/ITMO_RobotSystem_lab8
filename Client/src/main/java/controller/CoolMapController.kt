package controller

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.ProductWithStatus
import com.itmo.r3135.World.Product
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import tornadofx.*
import view.WorkView.CoolMap
import view.WorkView.ProductPoint
import view.WorkView.Products
import view.WorkView.ProductsModel

class CoolMapController : Controller() {
    val coolMap: CoolMap by inject()
    val connectController: ConnectController by inject()
    val products = FXCollections.observableArrayList<Products>()
    val figures = FXCollections.observableHashMap<Int, ProductPoint>()
    val selectedProduct = ProductsModel()
    var currentMinCoordinatesX = -100.0
    var currentMinCoordinatesY = -100.0
    var currentMaxCoordinatesX = 100.0
    var currentMaxCoordinatesY = 100.0
    val border = 40.0

    init {
        products.clear()
        connectController.sendReceiveManager.send(Command(CommandList.SHOW))
        products.addListener(ListChangeListener<Products> { c ->
            while (c.next()) {
                if (c.wasRemoved()) {
                    //работка удаления
                    println("REMOVED")
                } else if (c.wasAdded()) {
                    //обавление
                    println("ADDED")
                } else if (c.wasUpdated()) {
                    //изменение
                    println("UPDATED")
                }
            }
        })
    }

    fun show(showList: ArrayList<Product>) {
        for (p in showList)
            addProduct(p)
        uperingCoordinates()
        updatePointCoordinates()
    }

    fun updateList(updateList: ArrayList<ProductWithStatus>) {
        for (p in updateList) {
            if (p.status == ProductWithStatus.ObjectStatus.UPDATE)
                addProduct(p.product)
            if (p.status == ProductWithStatus.ObjectStatus.REMOVE)
                removeProduct(p.product)
        }
        uperingCoordinates()
        updatePointCoordinates()
    }

    fun uperingCoordinates() {
        for (pp: MutableMap.MutableEntry<Int, ProductPoint> in figures) {
            if (currentMinCoordinatesX > pp.value.xReal) currentMinCoordinatesX = pp.value.xReal
            if (currentMinCoordinatesY > pp.value.yReal) currentMinCoordinatesY = pp.value.yReal
            if (currentMaxCoordinatesX < pp.value.xReal) currentMaxCoordinatesX = pp.value.xReal
            if (currentMaxCoordinatesY < pp.value.yReal) currentMaxCoordinatesY = pp.value.yReal
        }
        println(currentMinCoordinatesX)
        println(currentMinCoordinatesY)
        println(currentMaxCoordinatesX)
        println(currentMaxCoordinatesY)
    }

    fun updatePointCoordinates() {
        for (pp: MutableMap.MutableEntry<Int, ProductPoint> in figures) {
            val x = border + (coolMap.p.minWidth-2*border)*(pp.value.xReal-currentMinCoordinatesX)/
                    (currentMaxCoordinatesX-currentMinCoordinatesX)
            val y = coolMap.p.minHeight-(border + (coolMap.p.minHeight-2*border)*(pp.value.yReal-currentMinCoordinatesY)/
                    (currentMaxCoordinatesY-currentMinCoordinatesY))
            pp.value.setXY(x, y)
        }
    }

    private fun addToMap(product: Product) {
        val added = ProductPoint(product)
        figures[product.id] = added
        coolMap.p.add(added.point)
    }

    private fun removeFromMap(product: Product) {
        coolMap.p.children.remove((figures[product.id])?.point)
        figures.remove(product.id)

    }

    private fun addProduct(product: Product) {
        products.add(Products(product))
        addToMap(product)
    }

    private fun removeProduct(product: Product) {
        products.remove(Products(product))
        removeFromMap(product)
    }


}