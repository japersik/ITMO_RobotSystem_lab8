package controller

import com.itmo.r3135.Connector.Executor
import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.ServerMessage
import com.itmo.r3135.view.MainView
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.util.Duration
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
    private val connectionView: ConnectionView by inject()
    private val loginScreen: LoginScreen by inject()
    private val mainView: MainView by inject()
    private val productsController: CoolMapController by inject()
//    private val productsController: ProductsController by inject()
    var isConnect = false
    var isLogin = false
    var needCode = false

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
            if (serverMessage.updateTime != null) {
                sendReceiveManager.lastUpdateTime = serverMessage.updateTime
            }
            if (serverMessage.productWithStatuses != null) {
                productsController.updateList(serverMessage.productWithStatuses)
            }
            if (serverMessage.message != null) println(serverMessage.message)
            if (serverMessage.products != null) {
                productsController.show(serverMessage.products)
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
//                    productsController.init()
                } else shakeStage()
            //если пароль изменился в процессе работы(вдруг)
            else if (!newIsLogin) println("Пароль был изменён. Ошибка авторизации")
        this.needCode = newNeedCode
        this.isLogin = newIsLogin
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
}
