package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.ViewClient.controller.CoolMapController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class ProductsSearch : View("My View") {
    private val coolMapController: CoolMapController by inject()
    var search = ""


    private val model = object : ViewModel() {
        val search = bind { SimpleStringProperty() }
    }

    override val root = hbox {
        fieldset(" ", FontAwesomeIconView(FontAwesomeIcon.SEARCH)) {
        }
        textfield(model.search) {
            textProperty().addListener { _, _, newValue ->
                search = newValue
                coolMapController.updatetable(search)
            }
        }
    }
}
