package view

import com.itmo.r3135.World.Generator
import controller.ProductsController
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import model.Products
import model.ProductsModel
import tornadofx.*
import view.testApp.AddApp
import kotlin.streams.toList


class Interface : View("My View") {
    val controller: ProductsController by inject()
    val model: ProductsModel by inject()
    val productsTable: ProductsTable by inject()
    val productsMap: ProductsMap by inject()

    override val root = stackpane {
        prefHeight = 800.0
        prefWidth = 80.0
        gridpane {
            row {
                button("+") {
                    tooltip("add new object")
                    prefHeight = 50.0
                    prefWidth = 80.0
                    gridpaneConstraints {
                        marginTop = 10.0
                        marginLeft = 10.0
                    }
                    action {
                        AddForm().openWindow()
                    }
                }
            }
            row {
                button("-") {
                    tooltip("remove object")
                    prefHeight = 50.0
                    prefWidth = 80.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        controller.products.removeAll(controller.products.stream().filter { t: Products? -> t == controller.selectedPerson.item }.toList())
                        productsMap.repaint()
                    }
                }
            }
            row {
                button("button") {
                    tooltip("remove object")
                    prefHeight = 50.0
                    prefWidth = 80.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        //action
                    }
                }
            }
        }
    }
}
