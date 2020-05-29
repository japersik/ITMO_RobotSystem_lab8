package com.itmo.r3135.ViewClient.view.testApp

import com.itmo.r3135.ViewClient.view.Styles
import com.itmo.r3135.ViewClient.controller.ConnectController
import javafx.application.Application
import tornadofx.*
import com.itmo.r3135.ViewClient.view.RegistrationView

class RegApp : App(RegistrationView::class, Styles::class){
    val connectController: ConnectController by inject()
}

fun main(args: Array<String>) {
    Application.launch(RegApp::class.java, *args)
}