package com.itmo.r3135.Server.Commands;

import com.itmo.r3135.Server.DataManager;
import com.itmo.r3135.Server.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ProductWithStatus;
import com.itmo.r3135.System.ServerMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Класс формирует сообщение о измененияв в коллекции пользователю
 */
public class GetUpdatesCommand extends AbstractCommand {

    public GetUpdatesCommand(DataManager dataManager, Mediator serverWorker) {
        super(dataManager, serverWorker);
    }

    @Override
    public ServerMessage activate(Command command) {
        if (command.getLastUpdate() != null) {
            LocalDateTime lastUpdate = command.getLastUpdate();
            dataManager.getLock().readLock().lock();
            try {
                if (dataManager.getDateChange().compareTo(lastUpdate) > 0) {
                    ArrayList<ProductWithStatus> changeProducts = dataManager.getChangeProducts();
                    ArrayList<ProductWithStatus> changeProductsList = changeProducts.stream()
                            .filter(changeProduct -> changeProduct.getAddDateTime().compareTo(lastUpdate) > 0)
                            .collect(Collectors.toCollection(ArrayList::new));
                    return new ServerMessage(dataManager.getDateChange(), changeProductsList);
                } else return new ServerMessage("В последнее время изменений не произошло.");
            } finally {
                dataManager.getLock().readLock().unlock();
            }
        } else return new ServerMessage("Некорректное время последнего обновления.");

    }
}
