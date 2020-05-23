package controller

import com.itmo.r3135.World.Generator
import com.itmo.r3135.World.Product
import javafx.collections.FXCollections
import model.Products
import model.ProductsModel
import tornadofx.*

class ProductsController() : Controller() {
    val products = FXCollections.observableArrayList<Products>()
    val selectedPerson = ProductsModel()

    init {   repeat(10) {
        addProduct(Generator.nextProduct())
    }
    }

    public fun addProduct(product: Product) {
        products.add(Products(product))
    }

    public fun removeProduct(product: Product) {
        products.remove(Products(product))
    }
}