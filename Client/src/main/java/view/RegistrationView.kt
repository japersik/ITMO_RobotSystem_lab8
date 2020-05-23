package view

import com.itmo.r3135.Client.ClientWorker
import com.itmo.r3135.app.Styles
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class RegistrationView : View("Please reg") {
    private val model = object : ViewModel() {
        val username = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
        val code = bind { SimpleStringProperty() }
    }

    override val root = form {

        fieldset {
            field("Username") {
                textfield(model.username) {
                    required()
                    whenDocked { requestFocus() }
                }
            }
            field("Password") {
                passwordfield(model.password).required()
            }
        }

        button("Reg") {
            isDefaultButton = true

            action {
                model.commit {
                    //reg
                }
            }
        }
    }
}
