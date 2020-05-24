package view.testApp

import com.itmo.r3135.app.Styles
import controller.ConnectController
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.*
import view.LoginScreen

class LoginApp : App(LoginScreen::class, Styles::class) {
    val connectController: ConnectController by inject()

    override fun start(stage: Stage) {
        super.start(stage)
        connectController.init()
    }
}

fun main(args: Array<String>) {
    Application.launch(LoginApp::class.java, *args)
}