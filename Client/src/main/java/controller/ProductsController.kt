package controller

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.ProductWithStatus
import com.itmo.r3135.World.Product
import javafx.collections.FXCollections
import tornadofx.*
import view.WorkView.Products
import view.WorkView.ProductsMap
import view.WorkView.ProductsModel

class ProductsController() : Controller() {
    val products = FXCollections.observableArrayList<Products>()
    val selectedProduct = ProductsModel()
    val productsMap: ProductsMap by inject()
    val connectController: ConnectController by inject()

    init{
        products.clear()
        connectController.sendReceiveManager.send(Command(CommandList.SHOW))
    }

    fun show(showList: ArrayList<Product>) {
        for (p in showList)
            addProduct(p)
        productsMap.repaint()
    }

    public fun updateList(updateList: ArrayList<ProductWithStatus>) {
        for (p in updateList) {
            if (p.status == ProductWithStatus.ObjectStatus.UPDATE)
                addProduct(p.product)
            if (p.status == ProductWithStatus.ObjectStatus.REMOVE)
                removeProduct(p.product)
        }
        productsMap.repaint()
    }

    fun addProduct(product: Product) {
        products.add(Products(product))
    }

    fun removeProduct(product: Product) {
        products.remove(Products(product))
    }
}