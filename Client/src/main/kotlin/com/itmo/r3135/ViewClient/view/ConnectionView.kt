package com.itmo.r3135.ViewClient.view

import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.view.Styles.Companion.red
import com.itmo.r3135.ViewClient.view.Styles.Companion.redcolor
import javafx.beans.property.SimpleStringProperty
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter
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



