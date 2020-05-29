package com.itmo.r3135.ViewClient.view

import com.itmo.r3135.ViewClient.controller.ConnectController
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class ConnectionView : View("Connect controller") {
    val connectController: ConnectController by inject()

    private val model = object : ViewModel() {
        val host = bind { SimpleStringProperty() }
        val port = bind { SimpleStringProperty() }
    }

    override val root = form {
        addClass(Styles.loginScreen)
        fieldset {
            field("Host | Хостяра") {
                textfield(model.host) {
                    required()
                    whenDocked { requestFocus() }
                }
            }
            field("Port") {
                textfield(model.port) {
                    required()
                    filterInput { it.controlNewText.isInt() && it.controlNewText.toInt() < 64000 }
                }
            }
        }
        button("Ping") {
            isDefaultButton = true
            action {
                model.commit {
                    connectController.connectionCheck(model.host.value,
                            model.port.value.toInt())
                }

            }
        }
    }

}



