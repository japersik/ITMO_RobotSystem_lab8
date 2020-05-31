package com.itmo.r3135.ViewClient.view.WorkView

import com.itmo.r3135.ViewClient.controller.CoolMapController
import javafx.scene.layout.Pane
import tornadofx.*


class CoolMap : View("My View") {
    val controller: CoolMapController by inject()
    val productsTable: ProductsTable by inject()
    val p: Pane = pane {
        minWidth = 700.0
        minHeight = 400.0
        prefWidth = 1500.0
        prefHeight = 900.0
        layoutBoundsProperty().addListener(ChangeListener() { observable, oldValue, newValue ->
            Thread.sleep(20)
            controller.repaintNewWindowsSize()
//            val dw = newValue.width-oldValue.width
//            val dh = newValue.height-oldValue.height
            productsTable.size(newValue.width, newValue.height)
        })
    }
    override val root = p

}



