package com.itmo.r3135.ViewClient.view

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Labeled
import tornadofx.*

class CodeView : View("Verification Code Checker") {
    private val connectController: ConnectController by inject()
    private val localizationManager: LocalizationManager by inject()
    private val model = object : ViewModel() {
        val code = bind { SimpleStringProperty() }
    }

    override val root = form {
        addClass(Styles.loginScreen)
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
                    connectController.send(Command(CommandList.CODE, model.code.value.toString()))
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