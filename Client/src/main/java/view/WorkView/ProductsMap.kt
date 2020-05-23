package view.WorkView

import controller.ProductsController
import javafx.geometry.Side
import javafx.scene.chart.NumberAxis
import tornadofx.*

class ProductsMap : View("My View") {
    val controller: ProductsController by inject()

    override val root = stackpane {
        prefHeight = 700.0
        prefWidth = 1500.0

        scatterchart("", NumberAxis(), NumberAxis()) {
            titleSide = Side.RIGHT
            series("Products") {
                controller.products.stream().forEach { t: Products? -> data(t?.xcoordinate, t?.ycoordinate) }
            }
        }
    }

    fun repaint() {
        scatterchart("", NumberAxis(), NumberAxis()) {
            titleSide = Side.RIGHT
            series("Products") {
                controller.products.stream().forEach { t: Products? -> data(t?.xcoordinate, t?.ycoordinate) }
            }
        }
    }
}
