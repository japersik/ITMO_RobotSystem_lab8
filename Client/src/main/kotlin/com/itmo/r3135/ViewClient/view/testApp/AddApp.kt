package com.itmo.r3135.ViewClient.view.testApp

import com.itmo.r3135.ViewClient.view.Styles
import javafx.application.Application
import tornadofx.App
import com.itmo.r3135.ViewClient.view.WorkView.AddForm

class AddApp : App(AddForm::class, Styles::class)

fun main(args: Array<String>) {
    Application.launch(AddApp::class.java, *args)
}