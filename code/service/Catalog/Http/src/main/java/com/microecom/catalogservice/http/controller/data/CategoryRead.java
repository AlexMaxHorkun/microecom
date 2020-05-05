package com.microecom.catalogservice.http.controller.data;

import com.microecom.catalogservice.model.data.ExistingCategory;

/**
 * Category data to display.
 */
public class CategoryRead {
    private final String id;

    private final String name;

    public static CategoryRead of(ExistingCategory category) {
        return new CategoryRead(category.getId(), category.getName());
    }

    private CategoryRead(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
