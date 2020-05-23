package com.itmo.r3135.Client;

import com.itmo.r3135.Connector.Executor;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketAddress;

public class MessageController implements Executor {
    @Override
    public void execute(byte[] data, SocketAddress inputAddress) {
        try (
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new ByteArrayInputStream(data))
        ) {
            ServerMessage serverMessage = (ServerMessage) objectInputStream.readObject();
            objectInputStream.close();
            if (serverMessage != null) {
                if (serverMessage.getMessage() != null)
                    System.out.println(serverMessage.getMessage());
                if (serverMessage.getProducts() != null)
                    for (Product p : serverMessage.getProducts()) System.out.println(p);
            } else System.out.println("Ответ сервера некорректен.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка десериализации.");
        }
    }
}
