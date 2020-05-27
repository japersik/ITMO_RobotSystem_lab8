package com.itmo.r3135.System;

import com.itmo.r3135.World.Product;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProductWithStatus implements Serializable {
    private final Product product;
    private final ObjectStatus status;
    private final LocalDateTime addDateTime = LocalDateTime.now();

    public ProductWithStatus(Product product, ObjectStatus status) {
        this.product = product;
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public ObjectStatus getStatus() {
        return status;
    }

    public LocalDateTime getAddDateTime() {
        return addDateTime;
    }

    public static enum ObjectStatus implements Serializable {
        UPDATE,
        REMOVE,
        ADD,
    }

}