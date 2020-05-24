package com.itmo.r3135.System;

import com.itmo.r3135.World.Product;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Класс ответа сервера
 */
public class ServerMessage implements Serializable {
    private LocalDateTime updateTime;
    private String message;
    private ArrayList<Product> products;
    private Boolean login = true;
    private Boolean needCode = false;
    private ArrayList<ProductWithStatus> productWithStatuses;

    public ServerMessage(String message) {
        this.message = message;
    }

    public ServerMessage(String message, ArrayList<Product> products) {
        this.message = message;
        this.products = products;
    }

    public ServerMessage(ArrayList<Product> products, LocalDateTime updateTime) {
        this.products = products;
        this.updateTime = updateTime;
    }

    public ServerMessage(String message, Boolean login) {
        this.message = message;
        this.login = login;
    }

    public ServerMessage(String message, Boolean login, Boolean needCode) {
        this.message = message;
        this.login = login;
        this.needCode = needCode;
    }

    public ServerMessage(LocalDateTime updateTime, ArrayList<ProductWithStatus> productWithStatuses) {
        this.productWithStatuses = productWithStatuses;
        this.updateTime = updateTime;
    }

    public Boolean getNeedCode() {
        return needCode;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<ProductWithStatus> getProductWithStatuses() {
        return productWithStatuses;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }
}

