package com.itmo.r3135.viewClient.view.testApp

import com.itmo.r3135.viewClient.view.Styles
import com.itmo.r3135.viewClient.controller.ConnectController
import javafx.application.Application
import tornadofx.*
import com.itmo.r3135.viewClient.view.RegistrationView

class RegApp : App(RegistrationView::class, Styles::class){
    val connectController: ConnectController by inject()
}

fun main(args: Array<String>) {
    Application.launch(RegApp::class.java, *args)
}