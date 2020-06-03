package com.itmo.r3135.ViewClient.view

import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import com.itmo.r3135.ViewClient.view.WorkView.Toolbar
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Labeled
import tornadofx.*

class ConnectionView : View("Connect controller") {
    private val connectController: ConnectController by inject()
    private val localizationManager: LocalizationManager by inject()
    private val toolbar= Toolbar()
    private val model = object : ViewModel() {
        val host = bind { SimpleStringProperty() }
        val port = bind { SimpleStringProperty() }
    }

    override val root = borderpane{
        top { add(toolbar) }
        center {
            form {
                addClass(Styles.loginScreen)
                fieldset {
                    field {
                        id = "host"
                        textfield(model.host) {
                            required()
                        }
                    }
                    field {
                        id = "port"
                        textfield(model.port) {
                            filterInput { it.controlNewText.isInt() }
                            validator {
                                if (!it.toProperty().value.isInt() || it.toProperty().value.toInt() > 65535 || it.toProperty().value.toInt() < 0)
                                    error("Порт - это число от 0 до 65535") else null
                            }
                        }
                    }
                }
                button {
                    id = "ping"
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
    }

    init {
        updateLanguage()
    }

    fun updateLanguage() {
        toolbar.updateLanguage()
        (root.lookup("#port") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_PORT)
        (root.lookup("#host") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_HOST)
        (root.lookup("#ping") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_PING)
    }


}



