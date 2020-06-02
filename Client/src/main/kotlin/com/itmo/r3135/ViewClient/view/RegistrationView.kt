package com.itmo.r3135.ViewClient.view

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import com.itmo.r3135.ViewClient.controller.NotificationsController
import com.itmo.r3135.ViewClient.view.Styles.Companion.loginScreen
import com.itmo.r3135.ViewClient.view.WorkView.Toolbar
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Labeled
import tornadofx.*
import java.util.regex.Pattern

class RegistrationView : View("Please reg") {
    val connectController: ConnectController by inject()
    val notificationsController: NotificationsController by inject()
    val localizationManager: LocalizationManager by inject()
    val toolbar = Toolbar()
    private val model = object : ViewModel() {
        val login = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
    }

    override val root =
            borderpane() {
                top { add(toolbar) }
                center {
                    form {
                        addClass(loginScreen)
                        fieldset {
                            field("Email") {
                                id = "email"
                                textfield(model.login) {
                                    required()
                                    whenDocked {
                                        requestFocus()
                                    }
                                }
                            }
                            field("Password") {
                                id = "pass"
                                passwordfield(model.password) {
                                    required()
                                }
                            }
                        }
                        hbox {
                            button("Reg") {
                                addClass(Styles.loginScreenButton)
                                id = "reg"
                                isDefaultButton = true
                                action {
                                    model.commit {
                                        if (!validate(model.login.value)) {
                                            connectController.shakeStage()
                                            notificationsController.errorMessage(text = "Incorrect e-mail")
                                        } else {
                                            val command = Command(CommandList.REG)
                                            command.setLoginPassword(model.login.value, model.password.value)
                                            connectController.sendReceiveManager.send(command)
                                            close()
                                        }
                                    }
                                }
                            }
                            button("cancel") {
                                addClass(Styles.loginScreenButton)
                                id = "cancel"
                                isCancelButton = true
                                action {
                                    close()
                                }
                            }
                        }
                    }
                }
            }
    private val VALIDEMAIL: Pattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private fun validate(emailStr: String): Boolean {
        return VALIDEMAIL.matcher(emailStr).find();
    }

    init {
        updateLanguage()
    }

    fun updateLanguage() {
        (root.lookup("#email") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_EMAIL)
        (root.lookup("#pass") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_PASSWORD)
        (root.lookup("#reg") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_REG)
        (root.lookup("#cancel") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_CANCEL)
    }
}
