package com.itmo.r3135.viewClient.controller

import com.itmo.r3135.Connector.Executor
import com.itmo.r3135.Connector.PingChecker
import com.itmo.r3135.Connector.Reader
import com.itmo.r3135.Connector.Sender
import com.itmo.r3135.System.Command
import com.itmo.r3135.System.CommandList
import java.math.BigInteger
import java.net.SocketAddress
import java.nio.channels.DatagramChannel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.time.LocalDateTime

class SendReceiveManager(var socketAddress: SocketAddress, executor: Executor) {
    var login = ""
    var password = ""
    private var sender: Sender
    private var reader: Reader
    var lastUpdateTime = LocalDateTime.MIN

    init {
        val datagramChannel = DatagramChannel.open()
        sender = Sender(datagramChannel)
        reader = Reader(datagramChannel)
        reader.setExecutor(executor)
    }

    fun send(command: Command) {
        if (command.command == CommandList.REG || command.command == CommandList.LOGIN) {
            login = command.login
            password = sha384(command.password)
            command.password = password
        } else {
            command.setLoginPassword(login, password)
        }
        command.lastUpdate = lastUpdateTime
        sender.send(command, socketAddress)
    }

    fun ping(): Long {
        return PingChecker.ping(Command(CommandList.PING), socketAddress)
    }

    fun startListening() {
        reader.startListening()
    }

    private fun sha384(password: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-384")
            val messageDigest = md.digest(password.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } catch (e: NoSuchAlgorithmException) {
            password
        }
    }
}

