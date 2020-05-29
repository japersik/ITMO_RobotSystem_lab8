package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.ViewClient.controller.ProductsController
import javafx.collections.ObservableList
import javafx.geometry.Side
import javafx.scene.chart.Chart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.ScatterChart
import javafx.scene.chart.XYChart
import tornadofx.*
import java.awt.List
import java.util.*

class ProductsMap : View("My View") {
    val controller: ProductsController by inject()
    val series: XYChart.Series<Number, Number>
    val scatterChart: ScatterChart<Number, Number>

    init {
        scatterChart = ScatterChart(NumberAxis(), NumberAxis())
        scatterChart.titleSide = Side.RIGHT
        series = scatterChart.series("Products") {}
    }

    override val root = stackpane {
        prefHeight = 700.0
        prefWidth = 1500.0
        scatterchart("", NumberAxis(), NumberAxis()) {
            titleSide = Side.RIGHT
            series("Products") {
                controller.products.stream().forEach { t: Products? -> data(t?.xcoordinate?.toDouble(), t?.ycoordinate?.toDouble()) }
            }
        }
    }


    fun repaint() {
        scatterchart("", NumberAxis(), NumberAxis()) {
            titleSide = Side.RIGHT
            series("Products") {
                controller.products.stream().forEach { t: Products? -> data(t?.xcoordinate?.toDouble(), t?.ycoordinate?.toDouble()) }
            }
        }
    }
}
