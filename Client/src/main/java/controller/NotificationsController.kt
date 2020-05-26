package controller

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
}