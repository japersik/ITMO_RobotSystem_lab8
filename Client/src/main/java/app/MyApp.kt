package com.itmo.r3135.app

import com.itmo.r3135.World.Generator
import com.itmo.r3135.World.Product
import com.itmo.r3135.view.MainView
import controller.LoginController
import controller.ProductsController
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.*
import view.LoginScreen
import view.testApp.LoginApp

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

class MyApp: App(MainView::class, Styles::class) {
    val controller: ProductsController by inject()

}


