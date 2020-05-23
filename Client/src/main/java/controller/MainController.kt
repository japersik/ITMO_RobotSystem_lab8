package com.itmo.r3135.controller


import com.itmo.r3135.Connector.Executor
import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import com.itmo.r3135.System.ServerMessage
import controller.LoginController
import controller.ProductsController
import controller.SendReceiveManager
import javafx.application.Platform
import tornadofx.*
import view.ConnectionView
import view.LoginScreen
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.channels.UnresolvedAddressException


class MainController : Controller(), Executor {
    lateinit var sendReceiveManager: SendReceiveManager
    val loginScreen: LoginScreen by inject()
    val connectionView: ConnectionView by inject()
    val loginController: LoginController by inject()
    val controller: ProductsController by inject()


    fun connectionCheck(host: String, port: Int) {
        try {
            sendReceiveManager = SendReceiveManager(InetSocketAddress(host, port), this)
            if (sendReceiveManager.ping() != -1L) {
                sendReceiveManager.startListening()
                connectionView.replaceWith(loginScreen, sizeToScene = true, centerOnScreen = true)
            } else println("Bad connect")
        } catch (e: UnresolvedAddressException) {
            println("wrong address!!!")
        }
    }

    override fun execute(data: ByteArray?, inputAddress: SocketAddress?) {
        try {
            ObjectInputStream(
                    ByteArrayInputStream(data)).use { objectInputStream ->
                val serverMessage = objectInputStream.readObject() as ServerMessage
                objectInputStream.close()
                if (serverMessage != null) {
                    if (serverMessage.login && !loginController.isLogin)
                        Platform.runLater {
                            loginController.isLogin = true
                            loginController.toBase()
                            sendReceiveManager.send(Command(CommandList.SHOW));
                        }
                    if (serverMessage.productWithStatuses != null)
                        Platform.runLater {
                            controller.updateList(serverMessage.productWithStatuses)
                            sendReceiveManager.lastUpdateTime = serverMessage.updateTime
                        }
                    if (serverMessage.message != null) println(serverMessage.message)
                    if (serverMessage.products != null)
                        Platform.runLater {
                            controller.show(serverMessage.products)
                            sendReceiveManager.lastUpdateTime = serverMessage.updateTime
                        }

                }
//                else println("Ответ сервера некорректен.")
            }
        } catch (e: IOException) {
//            println("Ошибка десериализации.")
        } catch (e: ClassNotFoundException) {
//            println("Ошибка десериализации.")
        }
    }
}