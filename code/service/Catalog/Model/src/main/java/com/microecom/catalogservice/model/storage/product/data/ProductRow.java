package com.microecom.catalogservice.model.storage.product.data;

import com.microecom.catalogservice.model.storage.category.data.CategoryRow;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductRow {
    @Id
    private UUID id;

    private String sku;

    private String title;

    private String description;

    private Float price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryRow category;

    private Boolean available;

    public ProductRow(String sku, String title, String description, Float price, CategoryRow category) {
        this.sku = sku;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.id = UUID.randomUUID();
    }

    protected ProductRow() {}

    public UUID getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setCategory(CategoryRow category) {
        this.category = category;
    }

    public UUID getCategoryId() {
        return category.getId();
    }

    public Optional<Boolean> getAvailable() {
        return Optional.ofNullable(available);
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
