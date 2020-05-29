package com.itmo.r3135.viewClient.view.workView

import com.itmo.r3135.viewClient.controller.CoolMapController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class ProductsSearch : View("My View") {
    val CoolMapController : CoolMapController by inject()
    var search = ""


    private val model = object : ViewModel() {
        val search = bind { SimpleStringProperty() }
    }

    override val root = hbox {
            fieldset("", FontAwesomeIconView(FontAwesomeIcon.SEARCH)) {
            }
            textfield(model.search){
                textProperty().addListener { observable, oldValue, newValue ->
                    search = newValue
                    CoolMapController.updatetable(search) }
            }
    }
}
