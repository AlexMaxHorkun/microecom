package com.microecom.catalogservice.model.storage.category.data;

import com.microecom.catalogservice.model.data.ExistingCategory;

public class Existing implements ExistingCategory {
    private final String id;

    private final String name;

    public Existing(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
