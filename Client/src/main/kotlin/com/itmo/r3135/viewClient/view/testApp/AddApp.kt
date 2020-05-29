package com.itmo.r3135.viewClient.view.testApp

import com.itmo.r3135.viewClient.view.Styles
import javafx.application.Application
import tornadofx.App
import com.itmo.r3135.viewClient.view.workView.AddForm

class AddApp : App(AddForm::class, Styles::class)

fun main(args: Array<String>) {
    Application.launch(AddApp::class.java, *args)
}