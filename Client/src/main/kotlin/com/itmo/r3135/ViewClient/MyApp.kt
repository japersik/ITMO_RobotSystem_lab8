package com.itmo.r3135.ViewClient

import com.itmo.r3135.ViewClient.view.ConnectionView
import com.itmo.r3135.ViewClient.view.Styles
import javafx.application.Application
import tornadofx.*


fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}

class MyApp : App(ConnectionView::class, Styles::class)


