package view

import controller.ProductsController
import javafx.geometry.Side
import javafx.scene.chart.NumberAxis
import model.Products
import tornadofx.*

class ProductsMap : View("My View") {
    val controller: ProductsController by inject()

    override val root = stackpane {
        prefHeight = 700.0
        prefWidth = 1500.0

        bubblechart("", NumberAxis(), NumberAxis()) {
            titleSide = Side.LEFT
            series("series 1") {
                controller.persons.stream().forEach { t: Products? -> data(t?.xcoordinate,t?.ycoordinate, 10) }
            }
        }
    }
}
