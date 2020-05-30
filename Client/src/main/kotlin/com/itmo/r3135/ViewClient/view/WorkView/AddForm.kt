package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.view.Styles.Companion.addform
import com.itmo.r3135.World.Color
import com.itmo.r3135.World.UnitOfMeasure
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.USER
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.binding.BooleanExpression
import javafx.util.converter.DoubleStringConverter
import javafx.util.converter.FloatStringConverter
import tornadofx.*


class AddForm(val mode: Int = 1) : View("Register Customer") {
    val connectController: ConnectController by inject()
    val model: ProductsModel by inject()

    override val root = form {
        addClass(addform)
        if (mode == 1)
            fieldset("Хозяина", FontAwesomeIconView(USER))
            {
                field("Owner name") {
                    textfield(model.ownername).required()
                }
                field("Birthday") {
                    datepicker(model.birthday).required()
                }
                field("Owner's eye color") {
                    combobox<Color>(model.eyecolor, values = Color.values().toList()) {
                        required()
                    }
                }
                field("Owner's hair color") {
                    combobox<Color>(model.haircolor, values = Color.values().toList()) {
                        required()
                    }
                }
            }

        fieldset("Products", FontAwesomeIconView(FontAwesomeIcon.APPLE))
        {
//            field("ID") {
//                textfield(model.id).required()
//            }
            field("Name") {
                textfield(model.name).required()
            }
            field("Price") {
                textfield(model.price, DoubleStringConverter()) {
                    filterInput { it.controlNewText.isDouble() && it.controlNewText.toDouble() >= 0 }
                    validator {
                        if (!it.toProperty().value.isDouble() || model.price.value <= 0)
                            error("Цена должна быть больше нуля") else null
                    }
                }
            }
            field("X / Y") {
                textfield(model.xcoordinate, DoubleStringConverter()) {
                    id = "xId"
                    filterInput { it.controlNewText.isDouble() || it.controlNewText == "-" }
                    validator {
                        if (!it.toProperty().value.isDouble()) error("Введите число") else null
                    }
                }
                textfield(model.ycoordinate, DoubleStringConverter()) {
                    filterInput { it.controlNewText.isDouble() || it.controlNewText == "-" }
                    validator {
                        if (!it.toProperty().value.isDouble()) error("Введите число") else null
                    }
                }
            }
            field("Partnumeber") {
                textfield(model.partnumeber) {
                    validator {
                        if (it.toProperty().value.length < 21) error("Минимальный размер - 21 символ") else null
                    }
                }
            }
            field("Manufacture cost") {
                textfield(model.manufacturecost, FloatStringConverter()) {
                    filterInput { it.controlNewText.isFloat() && it.controlNewText.toFloat() >= 0 }
                    required()
                }
            }
            field("Unit of measure") {
                combobox<UnitOfMeasure>(model.unitOfMeasure, values = UnitOfMeasure.values().toList()) {
                    required()
                }
            }
        }
        hbox {
            button("Add")
            {
                isDefaultButton = true
                action {
                    model.commit {
                        val product = model.item
                        connectController.sendReceiveManager.send(Command(CommandList.ADD, product.toProduct()))
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

    override val savable: BooleanExpression
        get() = super.savable

}



