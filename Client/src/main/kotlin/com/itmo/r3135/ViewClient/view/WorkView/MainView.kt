package com.itmo.r3135.view


import com.itmo.r3135.ViewClient.view.WorkView.*
import com.itmo.r3135.ViewClient.view.Styles.Companion.main
import tornadofx.*

class MainView : View("BestApplication") {

    override val root = borderpane() {
        addClass(main)
        prefHeight = 900.0
        prefWidth = 1600.0
        top(Toolbar::class)
        left(Interface::class)
        center(gridpane {
//            row { add<ProductsMap>() }
            row { add<CoolMap>() }
            row { add<ProductsSearch>() }
            row { add<ProductsTable>() }
        })
//        right(AddForm::class)
        //left(BottomView::class)
        //addClass(Styles.main)
    }

}