package view

import com.itmo.r3135.app.Styles
import com.itmo.r3135.controller.MainController
import controller.LoginController
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.util.Duration
import tornadofx.*

class ConnectionView : View("Register Customer") {
    val mainController: MainController by inject()

    private val model = object : ViewModel() {
        val host = bind { SimpleStringProperty() }
        val port = bind { SimpleStringProperty() }
    }
    override val root = form {
        addClass(Styles.loginScreen)
        fieldset {
            field("Host") {
                textfield(model.host) {
                    required()
                    whenDocked { requestFocus() }
                }
            }
            field("Port") {
                textfield(model.port){
                    required()
                filterInput { it.controlNewText.isInt()&&it.controlNewText.toInt()>1000&&it.controlNewText.toInt()< }
                }
            }
        }

        button("Ping") {
            isDefaultButton = true

            action {
                model.commit {
                }
            }
        }
    }

}



