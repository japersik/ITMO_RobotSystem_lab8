package controller

import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.controller.MainController
import com.itmo.r3135.view.MainView
import tornadofx.*
import view.ConnectionView
import view.LoginScreen

class LoginController : Controller() {
    val loginScreen: LoginScreen by inject()
    val secureScreen: ConnectionView by inject()
    val mainController: MainController by inject()
    val mainScreen: MainView by inject()
    var isLogin: Boolean = false
    fun init() {
        with(config) {
            if (containsKey(USERNAME) && containsKey(PASSWORD))
                tryLogin(USERNAME, PASSWORD, true)
            else
                showLoginScreen("Please log in")
        }
    }

    fun showLoginScreen(message: String, shake: Boolean = false) {
        secureScreen.replaceWith(loginScreen, sizeToScene = true, centerOnScreen = true)
        runLater {
            if (shake) loginScreen.shakeStage()
        }
    }

    fun toBase() {
        if (isLogin)
            loginScreen.replaceWith(mainScreen, sizeToScene = true, centerOnScreen = true)
    }

    fun showSecureScreen() {
        loginScreen.replaceWith(secureScreen, sizeToScene = true, centerOnScreen = true)
    }

    fun tryLogin(username: String, password: String, remember: Boolean) {
        val command = Command(CommandList.LOGIN)
        command.setLoginPassword(username, password)
        mainController.sendReceiveManager.send(command)
//        runAsync {
//            username == "admin" && password == "secret"
//        } ui { successfulLogin ->
//            if (successfulLogin) {
//                loginScreen.close()
//                if (remember) {
//                    with(config) {
//                        set(USERNAME to username)
//                        set(PASSWORD to password)
//                        save()
//                    }
//                }
//            } else {
//                showLoginScreen("Login failed. Please try again.", true)
//            }
//        }
    }

    fun logout() {
        with(config) {
            remove(USERNAME)
            remove(PASSWORD)
            save()
        }

        showLoginScreen("Log in as another user")
    }

    companion object {
        val USERNAME = "username"
        val PASSWORD = "password"
    }
}