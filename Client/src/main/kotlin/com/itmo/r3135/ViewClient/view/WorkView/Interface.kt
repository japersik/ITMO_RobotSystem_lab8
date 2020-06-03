package com.itmo.r3135.ViewClient.view.WorkView

import ScriptForm
import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.CoolMapController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import com.itmo.r3135.ViewClient.view.Styles
import com.itmo.r3135.World.Generator
import javafx.scene.control.Labeled
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*


class Interface : View() {
    val connectController: ConnectController by inject()
    val controller: CoolMapController by inject()
    val model: ProductsModel by inject()
    private val localizationManager: LocalizationManager by inject()

    var usertext = text("//username//") {
        font = Font.font(16.0)
        gridpaneConstraints {
            marginTop = 10.0
            marginLeft = 10.0
        }

    }

    override val root = stackpane {
        prefHeight = 800.0
        prefWidth = 200.0
        gridpane {
            row {
                text {
                    gridpaneConstraints {
                        marginTop = 10.0
                        marginLeft = 10.0
                    }
                    id = "user_data"
                    font = Font.font(18.0)
                }
            }
            row {
                children.add(usertext)
            }
            row {
                button {
                    gridpaneConstraints {
                        marginTop = 10.0
                        marginLeft = 10.0
                    }
                    addClass(Styles.loginScreenButton)
                    id = "button_logout"
                    action {
                        connectController.newLoginCode(newIsLogin = false, newNeedCode = false)
                    }
                }
            }
            row {
                button {
                    id = "button_add"
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
                button {
                    id = "button_add_if_min"
                    prefHeight = 50.0
                    prefWidth = 200.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        AddForm(AddForm.Mode.ADD_IF_MIN).openModal()
                    }
                }
            }
            row {
                button {
                    id = "button_update"
                    prefHeight = 50.0
                    prefWidth = 200.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        if (controller.selectedProduct.item != null && controller.selectedProduct.item.id?.toInt() != null &&
                                controller.selectedProduct.item.userName == connectController.sendReceiveManager.login) {
                            AddForm(model = controller.selectedProduct).openModal()
                        } else SelectForm(SelectForm.Mode.UPDATE).openModal()
                    }
                }
            }
            row {
                button {
                    id = "button_remove"
                    prefHeight = 50.0
                    prefWidth = 200.0
                    gridpaneConstraints {
                        marginTop = 2.0
                        marginLeft = 10.0
                    }
                    action {
                        if (controller.selectedProduct.item != null && controller.selectedProduct.item.id?.toInt() != null &&
                                controller.selectedProduct.item.userName == connectController.sendReceiveManager.login) {
                            connectController.send(Command(CommandList.REMOVE_BY_ID, controller.selectedProduct.item.id.toInt()))
                        } else SelectForm(SelectForm.Mode.REMOVE).openModal()
                    }
                }
            }
            row {
                button {
                    id = "button_clear"
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
                button {
                    id = "button_script"
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
                        connectController.send(Command(CommandList.ADD, Generator.nextProduct()))
                    }
                }
            }
        }
    }

    init {
        updateLanguage()
    }

    fun updateLanguage() {
        (root.lookup("#button_logout") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_LOGOUT)
        (root.lookup("#user_data") as Text).text = localizationManager.getNativeTitle(LocaleString.TITLE_USER_DATA)
        (root.lookup("#button_add") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_ADD)
        (root.lookup("#button_remove") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_REMOVE)
        (root.lookup("#button_update") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_UPDATE)
        (root.lookup("#button_clear") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_CLEAR)
        (root.lookup("#button_script") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_SCRIPT)
        (root.lookup("#button_add_if_min") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_ADD_IF_MIN)
        (root.lookup("#button_add") as Labeled).tooltip(localizationManager.getNativeButton(LocaleString.BUTTON_ADD_TOOLTIP))
        (root.lookup("#button_remove") as Labeled).tooltip(localizationManager.getNativeButton(LocaleString.BUTTON_REMOVE_TOOLTIP))
        (root.lookup("#button_update") as Labeled).tooltip(localizationManager.getNativeButton(LocaleString.BUTTON_UPDATE_TOOLTIP))
        (root.lookup("#button_clear") as Labeled).tooltip(localizationManager.getNativeButton(LocaleString.BUTTON_CLEAR_TOOLTIP))
        (root.lookup("#button_script") as Labeled).tooltip(localizationManager.getNativeButton(LocaleString.BUTTON_SCRIPT_TOOLTIP))
        (root.lookup("#button_add_if_min") as Labeled).tooltip(localizationManager.getNativeButton(LocaleString.BUTTON_ADD_IF_MIN_TOOLTIP))

    }
}
