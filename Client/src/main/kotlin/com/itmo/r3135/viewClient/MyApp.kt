package com.itmo.r3135.viewClient

import com.itmo.r3135.viewClient.view.Styles
import com.itmo.r3135.viewClient.controller.ProductsController
import javafx.application.Application
import tornadofx.*
import com.itmo.r3135.viewClient.view.ConnectionView


fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

class MyApp: App(ConnectionView::class, Styles::class) {
    val controller: ProductsController by inject()
}


