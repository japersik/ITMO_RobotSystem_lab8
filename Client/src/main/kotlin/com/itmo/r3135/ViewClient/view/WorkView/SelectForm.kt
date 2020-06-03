package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.CoolMapController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import com.itmo.r3135.ViewClient.view.Styles
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Labeled
import tornadofx.*
import kotlin.streams.toList


class SelectForm(private val mode: Mode) : View() {
    private val connectController: ConnectController by inject()
    private val coolMapController: CoolMapController by inject()
    private val localizationManager: LocalizationManager by inject()
    private val model = object : ViewModel() {
        val id = bind { SimpleStringProperty() }
    }
    override val root = form {
        addClass(Styles.addform)
        fieldset("Select form", FontAwesomeIconView(FontAwesomeIcon.USER)) {
            id = "form_mane"
            field("ID of Product") {
                id = "id"
                textfield(model.id) {
                    required()
                    filterInput {
                        it.controlNewText.isInt()
                    }
                    validator {
                        if (model.id.value != null && model.id.value.isInt()) {
                            val product = coolMapController.products.stream().filter { p -> p.id == model.id.value.toInt() }.toList()
                            if (product.isEmpty())
                                error("Объекта с заданным id не найдено")
                            else if (product[0].userName != connectController.sendReceiveManager.login)
                                error("Объект принадлежит не Вам") else null
                        } else error("Введите id удаляемого объекта")
                    }
                }
            }
            hbox {
                button {
                    id = "button_select"
                    isDefaultButton = true
                    action {
                        if (mode == Mode.REMOVE) model.commit {
                            connectController.send(Command(CommandList.REMOVE_BY_ID, model.id.value.toInt()))
                            close()
                        }
                        if (mode == Mode.UPDATE) {
                            val models = ProductsModel()
                            models.item = coolMapController.products.stream().filter { p -> p.id == model.id.value.toInt() }.toList()[0]
                            AddForm(model = models).openWindow()
                            close()
                        }
                    }
                    enableWhen(model.valid)
                }
                button {
                    id = "button_cancel"
                    isCancelButton = true
                    action {
                        close()
                    }
                }
            }

        }
    }

    enum class Mode {
        REMOVE,
        UPDATE
    }

    init {
        updateLanguage()
    }


    fun updateLanguage() {
        (root.lookup("#form_mane") as Fieldset).text = localizationManager.getNativeTitle(LocaleString.TITLE_SELECT_ID)
        (root.lookup("#id") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_ID)
        (root.lookup("#button_cancel") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_CANCEL)
        if (mode == Mode.UPDATE) {
            (root.lookup("#button_select") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_UPDATE)
        }
        if (mode == Mode.REMOVE) {
            (root.lookup("#button_select") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_REMOVE)
        }
    }

}