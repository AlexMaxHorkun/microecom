package com.microecom.catalogservice.http.controller.data;

import com.microecom.catalogservice.model.data.ProductInfo;

import javax.validation.constraints.*;

/**
 * Input for a new product.
 */
public class NewProduct implements ProductInfo {
    private final String sku;

    private final String title;

    private final String description;

    private final Float price;

    private final String categoryId;

    public NewProduct(String sku, String title, String description, Float price, String categoryId) {
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public NewProduct() {
        sku = null;
        title = null;
        description = null;
        price = null;
        categoryId = null;
    }

    @Override
    public @NotNull @Size(min = 5, max = 255) String getSku() {
        return sku;
    }

    @Override
    public @NotNull @Size(min = 5, max = 255) String getTitle() {
        return title;
    }

    @Override
    public @NotNull @Size(min = 5, max = 4096) String getDescription() {
        return description;
    }

    @Override
    public @NotNull @DecimalMin("0.1") @DecimalMax("99999.0") Float getPrice() {
        return price;
    }

    @Override
    public @NotNull @NotBlank String getCategoryId() {
        return categoryId;
    }
}
