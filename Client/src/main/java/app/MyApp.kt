package com.itmo.r3135.app

import com.itmo.r3135.view.MainView
import controller.ProductsController
import javafx.application.Application
import tornadofx.*
import view.ConnectionView


fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

class MyApp: App(ConnectionView::class, Styles::class) {
    val controller: ProductsController by inject()

}


