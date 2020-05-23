package com.itmo.r3135.view


import com.itmo.r3135.app.Styles
import javafx.application.Application
import tornadofx.*
import view.*
import view.testApp.AddApp

class MainView : View("BestApplication") {

    override val root = borderpane() {
        prefHeight = 900.0
        prefWidth = 1600.0
        top(Toolbar::class)
        left(Interface::class)
        center(gridpane {
            row { add<ProductsMap>() }
            row { add<ProductsTable>() }
        })
        right(AddForm::class)
        //left(BottomView::class)
        //addClass(Styles.main)
    }

}