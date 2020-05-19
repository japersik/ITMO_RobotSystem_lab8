package view

import javafx.geometry.Side
import javafx.scene.chart.NumberAxis
import tornadofx.*

class ProductsMap : View("My View") {
    override val root = stackpane {
        prefHeight = 700.0
        prefWidth = 1550.0

        bubblechart("", NumberAxis(), NumberAxis()) {
            titleSide = Side.LEFT
            series("series 1") {

                data(1, 1,5) // imageview("house2.png")
            }
        }
    }
}
