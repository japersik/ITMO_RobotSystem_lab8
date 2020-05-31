package com.itmo.r3135.ViewClient.view.WorkView

import ScriptForm
import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.CoolMapController
import com.itmo.r3135.World.Generator
import javafx.scene.text.Font
import tornadofx.*


class Interface : View("My View") {
    val connectController: ConnectController by inject()
    val controller: CoolMapController by inject()
    val model: ProductsModel by inject()
    var usertext = text("Username: ${connectController.sendReceiveManager.login}") {
        font = Font.font(16.0)
    }
    override val root = stackpane {
        prefHeight = 800.0
        prefWidth = 200.0
        gridpane {
            row() {
                text("Данные пользователя") {
                    font = Font.font(18.0)
                }
            }
            row() {
              children.add(usertext)
            }
            row {
                button("Logout") {
                    action {
                        connectController.newLoginCode(newIsLogin = false, newNeedCode = false)
                    }
                }
            }
            row {
                button("+") {
                    tooltip("add new object")
                    prefHeight = 50.0
                    prefWidth = 200.0
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
                    prefWidth = 200.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {

                        if (controller.selectedProduct.item.id?.toInt() != null) {
                            connectController.send(Command(CommandList.REMOVE_BY_ID, controller.selectedProduct.item.id.toInt()))
                        } else RemoveForm().openModal()
                    }
                }
            }
            row {
                button("CLEAR") {
                    tooltip("Delete yours objects")
                    prefHeight = 50.0
                    prefWidth = 200.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        connectController.send(Command(CommandList.CLEAR))

                    }
                }
            }
            row {
                button("SCRIPT") {
                    tooltip("Execute SCRIPT")
                    prefHeight = 50.0
                    prefWidth = 200.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        ScriptForm().openModal()
                    }
                }
            }
            row {
                button("ADD RANDOM") {
                    tooltip("add new object")
                    prefHeight = 50.0
                    prefWidth = 200.0
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
                    prefWidth = 200.0
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
