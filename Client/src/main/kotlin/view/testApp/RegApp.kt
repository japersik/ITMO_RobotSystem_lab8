package view.testApp

import com.itmo.r3135.app.Styles
import controller.ConnectController
import javafx.application.Application
import javafx.stage.Stage
import tornadofx.*
import view.RegistrationView

class RegApp : App(RegistrationView::class, Styles::class){
    val connectController: ConnectController by inject()
}

fun main(args: Array<String>) {
    Application.launch(RegApp::class.java, *args)
}