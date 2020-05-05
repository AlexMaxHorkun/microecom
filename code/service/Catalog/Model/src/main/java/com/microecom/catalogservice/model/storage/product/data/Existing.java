package com.microecom.catalogservice.model.storage.product.data;

import com.microecom.catalogservice.model.data.ExistingProduct;

import java.util.Optional;

public class Existing implements ExistingProduct {
    private final String id;

    private final Boolean available;

    private final String sku;

    private final String title;

    private final String description;

    private final String categoryId;

    private final Float price;

    public Existing(String id, Boolean available, String sku, String title, String description, String categoryId, Float price) {
        this.id = id;
        this.available = available;
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Optional<Boolean> isAvailable() {
        return Optional.ofNullable(available);
    }

    @Override
    public String getSku() {
        return sku;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Float getPrice() {
        return price;
    }

    @Override
    public String getCategoryId() {
        return categoryId;
    }
}
