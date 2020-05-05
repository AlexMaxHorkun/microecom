package com.microecom.catalogservice.http.controller.data;

import com.microecom.catalogservice.model.data.ExistingProduct;

import java.util.ArrayList;

/**
 * Product info to display.
 */
public class ProductRead {
    private final String sku;

    private final String title;

    private final String description;

    private final String categoryId;

    private final float price;

    private final String id;

    public static ProductRead of(ExistingProduct product) {
        return new ProductRead(
                product.getSku(),
                product.getTitle(),
                product.getDescription(),
                product.getCategoryId(),
                product.getPrice(),
                product.getId()
        );
    }

    public static Iterable<ProductRead> listOf(Iterable<ExistingProduct> list) {
        var result = new ArrayList<ProductRead>();
        for (ExistingProduct p : list) {
            result.add(ProductRead.of(p));
        }

        return result;
    }

    private ProductRead(String sku, String title, String description, String categoryId, float price, String id) {
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.price = price;
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public float getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}
