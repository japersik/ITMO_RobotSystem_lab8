package com.itmo.r3135.ViewClient.controller

import org.controlsfx.control.Notifications
import tornadofx.*


class NotificationsController : Controller() {
    fun infoMessage(title: String = "INFO", text: String) {
        Notifications.create().darkStyle()
                .title(title)
                .text(text)
                .showInformation()
    }

    fun errorMessage(title: String = "ERROR", text: String) {
        Notifications.create().darkStyle()
                .title(title)
                .text(text)
                .showError()
    }

}