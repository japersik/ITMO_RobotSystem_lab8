package view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.World.Generator
import controller.ConnectController
import controller.CoolMapController
import controller.ProductsController
import tornadofx.*
import kotlin.streams.toList


class Interface : View("My View") {
    val connectController: ConnectController by inject()
    val controller: CoolMapController by inject()
    val model: ProductsModel by inject()
    val productsMap: ProductsMap by inject()
    val addForm: AddForm by inject()
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
                        AddForm().openModal()
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

                        if (controller.selectedProduct.item != null) {
                            connectController.sendReceiveManager.send(Command(CommandList.REMOVE_BY_ID, controller.selectedProduct.item.id.toInt()))
                        }
//                        val removelist = controller.products.stream().filter { t: Products? -> t == controller.selectedProduct.item }.toList()
//                        controller.products.removeAll(removelist)
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
            row {
                button("ADD RANDOM") {
                    tooltip("add new object")
                    prefHeight = 50.0
                    prefWidth = 80.0
                    gridpaneConstraints {
                        marginTop = 10.0
                        marginLeft = 10.0
                    }
                    action {
                        connectController.sendReceiveManager.send(Command(CommandList.ADD, Generator.nextProduct()))
                    }
                }
            }
        }
    }
}
