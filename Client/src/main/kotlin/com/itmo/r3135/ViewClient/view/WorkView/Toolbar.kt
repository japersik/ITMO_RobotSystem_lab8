package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import tornadofx.*
import kotlin.system.exitProcess

class Toolbar : View() {
    private val localizationManager: LocalizationManager by inject()
    private val connectController: ConnectController by inject()
    override val root =
            menubar {
                menu {
                    id = "language"
                    item("Русский").action {
                        localizationManager.setLocale("ru")
                        connectController.updateLanguage()
                    }
                    separator()
                    item("Français").action {
                        localizationManager.setLocale("fr")
                        connectController.updateLanguage()
                    }
                    separator()
                    item("Suomalainen").action {
                        localizationManager.setLocale("fi")
                        connectController.updateLanguage()
                    }
                    separator()
                    item("Español (Honduras)").action {
                        localizationManager.setLocale("es")
                        connectController.updateLanguage()
                    }
                }
                menu("Help") {
                    item("About...")
                }
                menu("Exit") {
                    item("Close all") {
                        id = "close_all"
                        action {
                            exitProcess(0)
                        }
                    }
                }
            }

    init {
        updateLanguage()
    }


    fun updateLanguage() {
        root.menus[0].text = localizationManager.getNativeTitle(LocaleString.TITLE_LANGUAGE)
        root.menus[1].text = localizationManager.getNativeTitle(LocaleString.TITLE_HELP)
        root.menus[2].text = localizationManager.getNativeTitle(LocaleString.TITLE_EXIT)
        root.menus[2].items[0].text = localizationManager.getNativeTitle(LocaleString.TITLE_CLOSE)
    }
}


