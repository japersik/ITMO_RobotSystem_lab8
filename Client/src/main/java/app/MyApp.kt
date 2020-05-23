package com.itmo.r3135.app

import com.itmo.r3135.view.MainView
import controller.LoginController
import javafx.stage.Stage
import tornadofx.App


class MyApp: App(MainView::class, Styles::class) {
}

