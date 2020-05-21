package view.testApp

import com.itmo.r3135.app.Styles
import controller.LoginController
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.*
import view.LoginScreen

class LoginApp : App(LoginScreen::class, Styles::class) {
    val loginController: LoginController by inject()

    override fun start(stage: Stage) {
        super.start(stage)
        loginController.init()
    }
}

fun main(args: Array<String>) {
    Application.launch(LoginApp::class.java, *args)
}