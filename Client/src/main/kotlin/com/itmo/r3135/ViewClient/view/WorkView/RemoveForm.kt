package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.CoolMapController
import com.itmo.r3135.ViewClient.view.Styles
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import kotlin.streams.toList


class RemoveForm : View("Remove Element") {
    private val connectController: ConnectController by inject()
    private val coolMapController: CoolMapController by inject()
    private val model = object : ViewModel() {
        val id = bind { SimpleStringProperty() }
    }
    override val root = form {
        addClass(Styles.addform)
        fieldset("Remove form", FontAwesomeIconView(FontAwesomeIcon.USER)) {
            field("ID of Product") {
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
                button("Remove") {
                    isDefaultButton = true
                    action {
                        model.commit {
                            connectController.send(Command(CommandList.REMOVE_BY_ID, model.id.value.toInt()))
                            close()
                        }
                    }
                    enableWhen(model.valid)
                }
                button("Cancel") {
                    isCancelButton = true
                    action {
                        close()
                    }
                }
            }

        }
    }

}