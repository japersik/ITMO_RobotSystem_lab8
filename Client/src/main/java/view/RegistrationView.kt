package view

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import controller.ConnectController
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class RegistrationView : View("Please reg") {
    val connectController: ConnectController by inject()

    private val model = object : ViewModel() {
        val login = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
    }

    override val root = form {
        fieldset {
            //добавть проверку
            field("Email") {
                textfield(model.login) {
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
                    val command = Command(CommandList.REG)
                    command.setLoginPassword(model.login.value, model.password.value)
                    connectController.sendReceiveManager.send(command)
                    close()
                }
            }
        }
        button("cancel") {
            isCancelButton = true
            action {
                     close()
            }
        }
    }
}
