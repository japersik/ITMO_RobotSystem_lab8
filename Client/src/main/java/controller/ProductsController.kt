package controller

import com.itmo.r3135.World.Color
import com.itmo.r3135.World.Generator
import javafx.collections.FXCollections
import model.Products
import model.ProductsModel
import tornadofx.Controller
import java.time.LocalDate

class ProductsController : Controller() {
    val persons = FXCollections.observableArrayList<Products>()
    val selectedPerson = ProductsModel()

    init {
        persons.add(Products(Generator.nextProduct()))
        persons.add(Products(Generator.nextProduct()))
        persons.add(Products(Generator.nextProduct()))
        persons.add(Products(Generator.nextProduct()))
        persons.add(Products(Generator.nextProduct()))
        persons.add(Products(Generator.nextProduct()))
    }
}