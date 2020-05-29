package com.itmo.r3135.ViewClient.view

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.view.Styles.Companion.loginScreen
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.view.Styles.Companion.loginScreenButton
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import kotlin.math.absoluteValue

class LoginScreen : View("Please log in") {
    val connectController: ConnectController by inject()

    private val model = object : ViewModel() {
        val username = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
        val remember = bind { SimpleBooleanProperty() }

    }

    override val root = form {
        addClass(loginScreen)
        fieldset {
            field("Username") {
                textfield(model.username) {
                    required()
                    whenDocked { requestFocus() }
                }
            }
            field("Password") {
                passwordfield(model.password).required()
            }
            field("Remember me") {
                checkbox(property = model.remember) {
                    tooltip("It's not safe.")
                }
            }
        }

        hbox {
            button("Login") {
                addClass(loginScreenButton)
                isDefaultButton = true
                action {
                    model.commit {
                        connectController.tryLogin(
                                model.username.value,
                                model.password.value,
                                model.remember.value
                        )
                    }
                }
            }
            button("Registration") {
                addClass(loginScreenButton)
                isDefaultButton = false
                action {
                    RegistrationView().openModal()
                }
            }
        }
    }

    override fun onDock() {
        model.validate(decorateErrors = false)
    }

    fun clear() {
        model.username.value = ""
        model.password.value = ""
        model.remember.value = false
    }
}

class CodeView : View("Verification Code Checker") {
    val connectController: ConnectController by inject()

    private val model = object : ViewModel() {
        val code = bind { SimpleStringProperty() }
    }

    override val root = form {
        addClass(Styles.loginScreen)
        fieldset {
            field("Code") {
                textfield(model.code) {
                    required()
                    whenDocked { requestFocus() }
                }
            }
        }


        button("Send code") {
            isDefaultButton = true
            action {
                model.commit {
                    connectController.sendReceiveManager.send(
                            Command(CommandList.CODE, model.code.value))
                    close()
                }

            }
        }
    }

}


