package com.itmo.r3135.ViewClient.controller

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import org.controlsfx.control.Notifications
import tornadofx.*


class NotificationsController : Controller() {
    fun infoMessage(title: String = "INFO", text: String) {
        Notifications.create().darkStyle()
                .title(title)
//                .owner(FX.primaryStage)
                .text(text)
                .showInformation()
    }

    fun errorMessage(title: String = "ERROR", text: String) {
        Notifications.create().darkStyle()
                .title(title)
//                .owner(FX.primaryStage)
                .text(text)
                .showError()
    }

//    fun errorMessageAlert(title: String = "ERROR", text: String) {
//        val errorAlert = Alert(AlertType.ERROR)
//        errorAlert.headerText = title
//        errorAlert.contentText = text
//        errorAlert.showAndWait()
//    }
}