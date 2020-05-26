package view

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import controller.ConnectController
import controller.NotificationsController
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.regex.Pattern

class RegistrationView : View("Please reg") {
    val connectController: ConnectController by inject()
    private val notificationsController: NotificationsController by inject()
    private val model = object : ViewModel() {
        val login = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
    }

    override val root = form {
        fieldset {
            //добавть проверку
            field("Email") {
                textfield(model.login) {
                    id = "login"
                    required()
                    whenDocked {
                        requestFocus()
                    }
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
                    if (!validate(model.login.value)) {
                        connectController.shakeStage()
                    notificationsController.errorMessage(text = "Incorrect e-mail")
                    } else {
                        val command = Command(CommandList.REG)
                        command.setLoginPassword(model.login.value, model.password.value)
                        connectController.sendReceiveManager.send(command)
                        close()
                    }
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
    val VALIDEMAIL: Pattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    fun validate(emailStr: String): Boolean {
        return VALIDEMAIL.matcher(emailStr).find();
    }
}
