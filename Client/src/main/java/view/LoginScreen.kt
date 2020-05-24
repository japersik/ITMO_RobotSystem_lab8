package view

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.app.Styles
import com.itmo.r3135.app.Styles.Companion.loginScreen
import controller.ConnectController
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.util.Duration
import tornadofx.*

class LoginScreen : View("Please log in") {
    val connectController: ConnectController by inject()

    private val model = object : ViewModel() {
        val username = bind { SimpleStringProperty() }
        val password = bind { SimpleStringProperty() }
        val remember = bind { SimpleBooleanProperty() }
//        val code = bind { SimpleStringProperty() }
    }

    override val root = form {
        addClass(loginScreen)
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
//            field("Code") {
//                passwordfield(model.code)
//            }
            field("Remember me") {
                checkbox(property = model.remember) {
                    tooltip("It's not safe.")
                }
            }
        }


        button("Login") {
            isDefaultButton = true

            action {
                model.commit {
                    connectController.tryLogin(
                            model.username.value,
                            model.password.value,
                            model.remember.value
                    )
                }
            }
        }
    }

    override fun onDock() {
        model.validate(decorateErrors = false)
    }

    fun shakeStage() {
        var x = 0
        var y = 0
        val cycleCount = 10
        val move = 10
        val keyframeDuration = Duration.seconds(0.04)

        val stage = FX.primaryStage

        val timelineX = Timeline(KeyFrame(keyframeDuration, EventHandler {
            if (x == 0) {
                stage.x = stage.x + move
                x = 4
            } else {
                stage.x = stage.x - move
                x = 0
            }
        }))

        timelineX.cycleCount = cycleCount
        timelineX.isAutoReverse = false

        val timelineY = Timeline(KeyFrame(keyframeDuration, EventHandler {
            if (y == 0) {
                stage.y = stage.y + move
                y = 1
            } else {
                stage.y = stage.y - move
                y = 0
            }
        }))

        timelineY.cycleCount = cycleCount
        timelineY.isAutoReverse = false

        timelineX.play()
        timelineY.play()
    }

    fun clear() {
        model.username.value = ""
        model.password.value = ""
        model.remember.value = false
    }
}

class CodeView : View("Verification Code Checker") {
    val connectController: ConnectController by inject()

    private val model = object : ViewModel() {
        val code = bind { SimpleStringProperty() }
    }

    override val root = form {
        addClass(Styles.loginScreen)
        fieldset {
            field("Code") {
                textfield(model.code) {
                    required()
                    whenDocked { requestFocus() }
                }
            }
        }


        button("Send code") {
            isDefaultButton = true
            action {
                model.commit {
                    connectController.sendReceiveManager.send(
                            Command(CommandList.CODE, model.code.value))
                    close()
                }

            }
        }
    }

}



