package trash

import controller.ConnectController
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class SecureScreen : View("Secure Screen") {
    val connectController: ConnectController by inject()

    override val root = borderpane {
        setPrefSize(800.0, 600.0)

        top {
            label(title) {
                font = Font.font(22.0)
            }
        }

        center {
            vbox(spacing = 15) {
                alignment = Pos.CENTER

                label("If you can see this, you are successfully logged in!")

                hbox {
                    alignment = Pos.CENTER

                    button("Logout") {
                        setOnAction {
                            connectController.logout()
                        }
                    }

                    button("Exit") {
                        setOnAction {
                            Platform.exit()
                        }
                    }
                }
            }
        }
    }
}