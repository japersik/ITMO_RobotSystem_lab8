package view.testApp

import com.itmo.r3135.app.Styles
import javafx.application.Application
import tornadofx.App
import view.WorkView.AddForm

class AddApp : App(AddForm::class, Styles::class)

fun main(args: Array<String>) {
    Application.launch(AddApp::class.java, *args)
}