package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.ViewClient.controller.ConnectController
import com.itmo.r3135.ViewClient.controller.CoolMapController
import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.controller.LocalizationManager
import com.itmo.r3135.ViewClient.view.Styles.Companion.addform
import com.itmo.r3135.World.Color
import com.itmo.r3135.World.UnitOfMeasure
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.binding.BooleanExpression
import javafx.scene.control.Labeled
import javafx.util.converter.DoubleStringConverter
import javafx.util.converter.FloatStringConverter
import tornadofx.*
import kotlin.streams.toList


class AddForm(private val mode: Mode = Mode.ADD) : View("Product form") {
    private val connectController: ConnectController by inject()
    private val coolMapController: CoolMapController by inject()
    private val model: ProductsModel by inject()
    private val localizationManager: LocalizationManager by inject()

    constructor(mode: Mode = Mode.UPDATE, model: ProductsModel) : this(mode) {
        this.model.item = model.item
    }

    override val root = form {
        addClass(addform)
        fieldset("", FontAwesomeIconView(FontAwesomeIcon.USER))
        {
            id = "owner"
            field {
                id = "owner_name"
                textfield(model.ownername).required()
            }
            field {
                id = "owner_birth_day"
                datepicker(model.birthday).required()
            }
            field {
                id = "owner_eye_color"
                combobox(model.eyecolor, values = Color.values().toList()) {
                    required()
                }
            }
            field {
                id = "owner_hair_color"
                combobox(model.haircolor, values = Color.values().toList()) {
                    required()
                }
            }
        }

        fieldset("", FontAwesomeIconView(FontAwesomeIcon.APPLE))
        {
            id = "product"
            if (mode == Mode.UPDATE) field {
                id = "id"
                textfield(model.id) {
                    required()
                    filterInput {
                        it.controlNewText.isInt()
                    }
                    validator {
                        if (model.id.value != null) {
                            val product = coolMapController.products.stream().filter { p -> p.id == model.id.value.toInt() }.toList()
                            if (product.isEmpty())
                                error("Объекта с заданным id не найдено")
                            else if (product[0].userName != connectController.sendReceiveManager.login)
                                error("Объект принадлежит не Вам") else null
                        } else error("Введите id удаляемого объекта")
                    }
                }
            }
            field {
                id = "name"
                textfield(model.name).required()
            }
            field {
                id = "price"
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
            field {
                id = "part_number"
                textfield(model.partnumeber) {
                    validator {
                        if (it.toProperty().value.length < 21) error("Минимальный размер - 21 символ") else null
                    }
                }
            }
            field {
                id = "manufacture_cost"
                textfield(model.manufacturecost, FloatStringConverter()) {
                    filterInput { it.controlNewText.isFloat() && it.controlNewText.toFloat() >= 0 }
                    required()
                }
            }
            field {
                id = "unit_of_measure"
                combobox(model.unitOfMeasure, values = UnitOfMeasure.values().toList()) {
                    required()
                }
            }
        }
        hbox {
            if (mode == Mode.ADD) button {
                id = "button_add"
                isDefaultButton = true
                action {
                    model.commit {
                        val product = model.item
                        connectController.send(Command(CommandList.ADD, product.toProduct()))
                        close()
                    }
                }
                enableWhen(model.valid)
            }
            if (mode == Mode.ADD_IF_MIN) button {
                id = "button_add_if_min"
                isDefaultButton = true
                action {
                    model.commit {
                        val product = model.item
                        connectController.send(Command(CommandList.ADD_IF_MIN, product.toProduct()))
                        close()
                    }
                }
                enableWhen(model.valid)
            }
            if (mode == Mode.UPDATE) button {
                id = "button_update"
                isDefaultButton = true
                action {
                    model.commit {
                        val product = model.item
                        connectController.send(Command(CommandList.UPDATE, product.toProduct(), model.id.value.toInt()))
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

    override val savable: BooleanExpression
        get() = super.savable

    init {
        updateLanguage()
    }


    fun updateLanguage() {
        (root.lookup("#owner") as Fieldset).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER)
        (root.lookup("#product") as Fieldset).text = localizationManager.getNativeTitle(LocaleString.TITLE_PRODUCT)
        (root.lookup("#name") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_NAME)
        (root.lookup("#price") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_PRICE)
        (root.lookup("#part_number") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_PART_NUMBER)
        (root.lookup("#unit_of_measure") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_UNIT_OF_MEASURE)
        (root.lookup("#manufacture_cost") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_MANUFACTURE_COST)
        (root.lookup("#owner_name") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_NAME)
        (root.lookup("#owner_birth_day") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_BIRTH_DAY)
        (root.lookup("#owner_eye_color") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_EYE_COLOR)
        (root.lookup("#owner_hair_color") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_OWNER_HAIR_COLOR)
        (root.lookup("#button_cancel") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_CANCEL)
        if (mode == Mode.ADD) (root.lookup("#button_add") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_ADD)
        if (mode == Mode.ADD_IF_MIN) (root.lookup("#button_add_if_min") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_ADD_IF_MIN)
        if (mode == Mode.UPDATE) {
            (root.lookup("#button_update") as Labeled).text = localizationManager.getNativeButton(LocaleString.BUTTON_UPDATE)
            (root.lookup("#id") as Field).text = localizationManager.getNativeTitle(LocaleString.TITLE_ID)
        }
    }
    enum class Mode {
        ADD,
        ADD_IF_MIN,
        UPDATE
    }

}



