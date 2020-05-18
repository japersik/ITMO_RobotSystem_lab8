package view

import javafx.scene.chart.NumberAxis
import tornadofx.*

class ProductsMap : View("My View") {
    override val root = stackpane {
        prefHeight = 500.0
        prefWidth = 500.0
        bubblechart("bubblechart", NumberAxis(), NumberAxis()) {
            series("series 1") {
                data(1, 1, 1)
                data(5, 5, 0.25)
            }
        }
    }
}
