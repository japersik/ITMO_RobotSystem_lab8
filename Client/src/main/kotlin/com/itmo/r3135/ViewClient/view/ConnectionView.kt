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
            field("Host") {
                textfield(model.host) {
                    required()
                    //whenDocked { requestFocus() }
                }
            }
            field("Port") {
                textfield(model.port) {
                    filterInput { it.controlNewText.isInt() }
                    validator {
                        if (!it.toProperty().value.isInt() || it.toProperty().value.toInt() > 65535 || it.toProperty().value.toInt() < 0)
                            error("Порт - это число от 0 до 65535") else null
                    }
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



