package com.itmo.r3135.ViewClient.view

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import com.itmo.r3135.ViewClient.view.Styles.Companion.loginScreen
import com.itmo.r3135.ViewClient.view.Styles.Companion.loginScreenButton
import com.itmo.r3135.ViewClient.view.WorkView.Toolbar
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Labeled
import tornadofx.*

class LoginScreen : View("Please log in") {
    val connectController: ConnectController by inject()
    val localizationManager: LocalizationManager by inject()
    val registrationView: RegistrationView by inject()
    val toolbar = Toolbar()
    private val model = object : ViewModel() {
        val username = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
        val remember = bind { SimpleBooleanProperty() }

    }

    override val root =
            borderpane {
                top { add(toolbar) }
                center {
                    form {
                        addClass(loginScreen)
                        fieldset {
                            field("Username") {
                                id = "name"
                                textfield(model.username) {
                                    required()
                                    whenDocked { requestFocus() }
                                }
                            }
                            field("Password") {
                                id = "pass"
                                passwordfield(model.password).required()
                            }
                        }
                        hbox {
                            button {
                                id = "login"
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
                            button {
                                id = "reg"
                                addClass(loginScreenButton)
                                isDefaultButton = false
                                action {
                                    registrationView.openModal()
                                }
                            }
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

    init {
        updateLanguage()
    }

    fun updateLanguage() {
        toolbar.updateLanguage()
        (root.lookup("#name") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_LOGIN)
        (root.lookup("#pass") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_PASSWORD)
        (root.lookup("#login") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_LOGIN)
        (root.lookup("#reg") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_REG)

    }
}

class CodeView : View("Verification Code Checker") {
    private val connectController: ConnectController by inject()
    private val localizationManager: LocalizationManager by inject()
    private val model = object : ViewModel() {
        val code = bind { SimpleStringProperty() }
    }

    override val root = form {
        addClass(loginScreen)
        fieldset {
            field("Code") {
                id = "code"
                textfield(model.code) {
                    required()
                    whenDocked { requestFocus() }
                }
            }
        }
        button("Send code") {
            isDefaultButton = true
            id = "send"
            action {
                model.commit {
                    connectController.send(
                            Command(CommandList.CODE, model.code.value))
                    close()
                }

            }
        }
    }

    init {
        updateLanguage()
    }

    fun updateLanguage() {
        (root.lookup("#code") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_CODE)
        (root.lookup("#send") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_CODE)
    }
}



