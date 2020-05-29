package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.World.Generator
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.CoolMapController
import com.itmo.r3135.ViewClient.controller.ProductsController
import tornadofx.*
import kotlin.streams.toList


class Interface : View("My View") {
    val connectController: ConnectController by inject()
    val controller: CoolMapController by inject()
    val model: ProductsModel by inject()
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

                        if (controller.selectedProduct.item.id?.toInt() != null) {
                            connectController.sendReceiveManager.send(Command(CommandList.REMOVE_BY_ID, controller.selectedProduct.item.id.toInt()))
                        }else RemoveForm().openModal()
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
                button("ADD RANDOM") {
                    tooltip("add new object")
                    prefHeight = 50.0
                    prefWidth = 80.0
                    gridpaneConstraints {
                        marginTop = 30.0
                        marginLeft = 10.0
                    }
                    action {
                        connectController.sendReceiveManager.send(Command(CommandList.ADD, Generator.nextProduct()))
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
