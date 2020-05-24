package view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import controller.ConnectController
import controller.ProductsController
import tornadofx.*
import kotlin.streams.toList


class Interface : View("My View") {
    val connectController: ConnectController by inject()
    val controller: ProductsController by inject()
    val model: ProductsModel by inject()
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
                        controller.products.removeAll(controller.products.stream().filter { t: Products? -> t == controller.selectedProduct.item }.toList())
                        productsMap.repaint()
                    }
                }
            }
            row {
                button("CLEAR") {
                    tooltip("Delete yours objects")
                    prefHeight = 50.0
                    prefWidth = 80.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        connectController.sendReceiveManager.send(Command(CommandList.CLEAR))

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
            row {
                button("UPDATE SHOW") {
                    tooltip("FOR TEST!!!")
                    prefHeight = 50.0
                    prefWidth = 80.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        connectController.sendReceiveManager.send(Command(CommandList.GET_UPDATES))
                    }
                }
            }
        }
    }
}
