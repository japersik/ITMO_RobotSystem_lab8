package com.itmo.r3135.ViewClient.view.testApp

import com.itmo.r3135.ViewClient.view.Styles
import com.itmo.r3135.ViewClient.controller.ConnectController
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.*
import com.itmo.r3135.ViewClient.view.LoginScreen

class LoginApp : App(LoginScreen::class, Styles::class) {
    val connectController: ConnectController by inject()

    override fun start(stage: Stage) {
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    Application.launch(LoginApp::class.java, *args)
}