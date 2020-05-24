package controller

import com.itmo.r3135.Connector.Executor
import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.ServerMessage
import com.itmo.r3135.view.MainView
import javafx.application.Platform
import tornadofx.*
import view.CodeView
import view.ConnectionView
import view.LoginScreen
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.channels.UnresolvedAddressException

class ConnectController : Controller(), Executor {
    lateinit var sendReceiveManager: SendReceiveManager
    val connectionView: ConnectionView by inject()
    val loginScreen: LoginScreen by inject()
    val mainView: MainView by inject()
    val productsController: ProductsController by inject()
    var isConnect = false
    var isLogin = false
    var needCode = false

    fun init() {
        with(config) {
            if (containsKey(USERNAME) && containsKey(PASSWORD))
                tryLogin(USERNAME, PASSWORD, true)
            else
                showLoginScreen("Please log in")
        }
    }

    fun connectionCheck(host: String, port: Int) {
        try {
            sendReceiveManager = SendReceiveManager(InetSocketAddress(host, port), this)
            if (sendReceiveManager.ping() != -1L) {
                isConnect = true
                connectionView.replaceWith(loginScreen, sizeToScene = true, centerOnScreen = true)
                sendReceiveManager.startListening()
            } else println("Bad connect")
        } catch (e: UnresolvedAddressException) {
            println("wrong address!!!")
        }
    }

    fun showLoginScreen(message: String, shake: Boolean = false) {
//        secureScreen.replaceWith(loginScreen, sizeToScene = true, centerOnScreen = true)
//        runLater {
//            if (shake) loginScreen.shakeStage()
//        }
    }

    fun tryLogin(username: String, password: String, remember: Boolean) {
        val command = Command(CommandList.LOGIN)
        command.setLoginPassword(username, password)
        sendReceiveManager.send(command)
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

    //
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

    override fun execute(data: ByteArray?, inputAddress: SocketAddress?) {
        try {
            ObjectInputStream(
                    ByteArrayInputStream(data)).use { objectInputStream ->
                val serverMessage = objectInputStream.readObject() as ServerMessage
                objectInputStream.close()
                if (serverMessage != null) processing(serverMessage)
//                else println("Ответ сервера некорректен.")
            }
        } catch (e: IOException) {
//            println("Ошибка десериализации.")
        } catch (e: ClassNotFoundException) {
//            println("Ошибка десериализации.")
        }
    }

    fun processing(serverMessage: ServerMessage) {
        Platform.runLater {
            newLoginCode(serverMessage.login, serverMessage.needCode)
            if (serverMessage.productWithStatuses != null) {
                productsController.updateList(serverMessage.productWithStatuses)
                sendReceiveManager.lastUpdateTime = serverMessage.updateTime
            }
            if (serverMessage.message != null) println(serverMessage.message)
            if (serverMessage.products != null) {
                productsController.show(serverMessage.products)
                sendReceiveManager.lastUpdateTime = serverMessage.updateTime
            }
        }
    }

    /**
     * Обновелние статуса пользователя и переход между окнами
     */
    private fun newLoginCode(newIsLogin: Boolean, newNeedCode: Boolean) {
        if (newNeedCode) {
            CodeView().openModal()
            return
        } else
            if (!this.isLogin)
                if (newIsLogin) {
                    loginScreen.replaceWith(mainView, sizeToScene = true, centerOnScreen = true)
                    productsController.init()
                } else loginScreen.shakeStage()
            //если пароль изменился в процессе работы(вдруг)
            else if (!newIsLogin) println("Пароль был изменён. Ошибка авторизации")
        this.needCode = newNeedCode
        this.isLogin = newIsLogin
    }
}
