package com.microecom.catalogservice.http.controller.data;

import com.microecom.catalogservice.model.data.ProductUpdate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * Input for a product update.
 */
public class PatchProductUpdate implements ProductUpdate {
    private String id;

    private final String title;

    private final String description;

    private final Float price;

    private final String categoryId;

    public PatchProductUpdate(String title, String description, Float price, String categoryId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public PatchProductUpdate() {
        title = null;
        description = null;
        price = null;
        categoryId = null;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getForId() {
        return id;
    }

    @Override
    public Optional<@Size(min = 5, max = 255) String> getTitle() {
        return Optional.ofNullable(title);
    }

    @Override
    public Optional<@Size(min = 5, max = 4096) String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public Optional<@DecimalMin("0.1") @DecimalMax("99999.0") Float> getPrice() {
        return Optional.ofNullable(price);
    }

    @Override
    public Optional<@Size(min = 1) String> getCategoryId() {
        return Optional.ofNullable(categoryId);
    }
}
