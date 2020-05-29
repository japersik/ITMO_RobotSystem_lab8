package com.itmo.r3135.ViewClient

import com.itmo.r3135.ViewClient.view.Styles
import com.itmo.r3135.ViewClient.controller.ProductsController
import javafx.application.Application
import tornadofx.*
import com.itmo.r3135.ViewClient.view.ConnectionView


fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

class MyApp: App(ConnectionView::class, Styles::class) {
    val controller: ProductsController by inject()
}


