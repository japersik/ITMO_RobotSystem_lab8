package com.itmo.r3135.view

import com.itmo.r3135.ViewClient.view.Styles.Companion.main
import com.itmo.r3135.ViewClient.view.WorkView.*
import tornadofx.*

class MainView : View("BestApplication") {
    val toolbar = Toolbar()
    override val root = borderpane() {
        addClass(main)
        prefHeight = 900.0
        prefWidth = 1600.0
        top {
            add(toolbar)
        }
        left<Interface>()
        center(gridpane {
            row { add<CoolMap>() }
            row { add<ProductsSearch>() }
            row { add<ProductsTable>() }
        })
    }

    init {
        updateLanguage()
    }

    fun updateLanguage() {
        toolbar.updateLanguage()
    }
}