package com.itmo.r3135.view

import com.itmo.r3135.ViewClient.controller.LocaleString
import com.itmo.r3135.ViewClient.view.Styles.Companion.main
import com.itmo.r3135.ViewClient.view.WorkView.*
import javafx.scene.control.Labeled
import tornadofx.*

class MainView : View("BestApplication") {
    val productsTable: ProductsTable by inject()
    val productsSearch: ProductsSearch by inject()
    val toolbar= Toolbar()
    override val root = borderpane() {
        addClass(main)
        prefHeight = 900.0
        prefWidth = 1600.0

        top {
            add(toolbar)
        }
        left(Interface::class)
        center(gridpane {
//            layoutBoundsProperty().addListener(ChangeListener() { observable, oldValue, newValue ->
//                println(newValue)
//                val dw = newValue.width-oldValue.width
//                val dh = newValue.height-oldValue.height
//                //productsTable.size(width+dw, height+dh)
//            })
            row { add<CoolMap>() }
            row { add(productsSearch) }
            row { add(productsTable) }
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
    init {
        updateLanguage()
    }
    fun updateLanguage() {
        toolbar.updateLanguage()
          }
}