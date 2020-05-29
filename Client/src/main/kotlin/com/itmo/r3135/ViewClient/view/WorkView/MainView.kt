package com.itmo.r3135.view

import com.itmo.r3135.ViewClient.view.WorkView.*
import com.itmo.r3135.ViewClient.view.Styles.Companion.main
import javafx.beans.value.ObservableValue
import javafx.geometry.Bounds
import tornadofx.*

class MainView : View("BestApplication") {
    val productsTable:ProductsTable by inject()

    override val root = borderpane() {
        addClass(main)
        prefHeight = 900.0
        prefWidth = 1600.0
        top( Toolbar::class)
        left(Interface::class)
        center(gridpane {
            layoutBoundsProperty().addListener(ChangeListener() { observable, oldValue, newValue ->
                println(newValue)
                val dw = newValue.width-oldValue.width
                val dh = newValue.height-oldValue.height
                //productsTable.size(width+dw, height+dh)
            })
            row { add<CoolMap>() }
            row { add<ProductsSearch>() }
            row { add<ProductsTable>()  }
        })
//        center(vbox {
//            add<CoolMap>()
//            add<ProductsSearch>()
//            add<ProductsTable>()
//        })


//        right(AddForm::class)
        //left(BottomView::class)
        //addClass(Styles.main)
    }

}